/**
 * Use Case: получить рекомендации для пользователя.
 * Сценарий (Cache-Aside, паттерн Strategy):
 *   1. Проверить кэш → если есть, вернуть (низкая задержка).
 *   2. Если нет — взять историю из БД.
 *   3. Прогнать через ML-модель.
 *   4. Сохранить в кэш.
 *   5. Вернуть.
 *
 * Use Case не знает, КАК реализованы кэш/БД/ML — только интерфейсы (порты).
 */
import { CachePort } from '../domain/ports/CachePort';
import { HistoryRepositoryPort } from '../domain/ports/HistoryRepositoryPort';
import { MlModelPort } from '../domain/ports/MlModelPort';
import { Recommendation } from '../domain/entities/Recommendation';

export interface GetRecommendationsInput {
  userId: string;
  segment: string;
  limit: number;
}

export interface GetRecommendationsOutput {
  source: 'cache' | 'ml_model';
  latency: 'low' | 'high';
  data: Recommendation[];
}

export class GetRecommendationsUseCase {
  constructor(
    private readonly cache: CachePort,
    private readonly historyRepo: HistoryRepositoryPort,
    private readonly mlModel: MlModelPort,
  ) {}

  async execute(input: GetRecommendationsInput): Promise<GetRecommendationsOutput> {
    const { userId, segment, limit } = input;
    const cacheKey = `recs:${userId}:${segment}:${limit}`;

    // Шаг 1: Cache-Aside — проверяем кэш
    const cached = await this.cache.get<Recommendation[]>(cacheKey);
    if (cached) {
      return { source: 'cache', latency: 'low', data: cached };
    }

    // Шаг 2: Cache Miss — берём историю из БД
    const history = await this.historyRepo.getViewedItemIds(userId, 50);

    // Шаг 3: Считаем рекомендации через ML-модель
    const recommendations = await this.mlModel.predict(userId, history, segment, limit);

    // Шаг 4: Сохраняем в кэш (TTL 5 минут)
    await this.cache.set(cacheKey, recommendations, 300);

    return { source: 'ml_model', latency: 'high', data: recommendations };
  }
}
