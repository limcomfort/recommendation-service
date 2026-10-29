/**
 * DI-контейнер (паттерн Dependency Injection + Singleton).
 * Собирает все зависимости в одном месте.
 * Легко подменить адаптер в тестах (например, Redis на InMemoryCache).
 */
import { RedisCacheAdapter } from '../infrastructure/cache/RedisCacheAdapter';
import { KafkaQueueAdapter } from '../infrastructure/queue/KafkaQueueAdapter';
import { PostgresHistoryRepository } from '../infrastructure/repository/PostgresHistoryRepository';
import { MockMlModelAdapter } from '../infrastructure/ml/MockMlModelAdapter';
import { GetRecommendationsUseCase } from '../application/GetRecommendationsUseCase';
import { TrackUserEventUseCase } from '../application/TrackUserEventUseCase';
import { RecommendationController } from '../presentation/RecommendationController';
import { EventController } from '../presentation/EventController';

const KAFKA_TOPIC = 'user_events_topic';

export interface Container {
  cache: RedisCacheAdapter;
  queue: KafkaQueueAdapter;
  historyRepo: PostgresHistoryRepository;
  mlModel: MockMlModelAdapter;
  getRecommendationsUseCase: GetRecommendationsUseCase;
  trackUserEventUseCase: TrackUserEventUseCase;
  recommendationController: RecommendationController;
  eventController: EventController;
}

export function buildContainer(): Container {
  const cache = new RedisCacheAdapter();
  const queue = new KafkaQueueAdapter();
  const historyRepo = new PostgresHistoryRepository();
  const mlModel = new MockMlModelAdapter();

  const getRecommendationsUseCase = new GetRecommendationsUseCase(cache, historyRepo, mlModel);
  const trackUserEventUseCase = new TrackUserEventUseCase(queue, KAFKA_TOPIC);

  const recommendationController = new RecommendationController(getRecommendationsUseCase);
  const eventController = new EventController(trackUserEventUseCase);

  return {
    cache,
    queue,
    historyRepo,
    mlModel,
    getRecommendationsUseCase,
    trackUserEventUseCase,
    recommendationController,
    eventController,
  };
}
