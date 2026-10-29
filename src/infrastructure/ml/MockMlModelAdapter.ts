/**
 * Адаптер ML-модели: mock.
 * Реализует MlModelPort (паттерн Adapter).
 * В реальном проекте здесь был бы вызов TensorFlow/PyTorch.
 */
import { MlModelPort } from '../../domain/ports/MlModelPort';
import { Recommendation } from '../../domain/entities/Recommendation';

export class MockMlModelAdapter implements MlModelPort {
  async predict(
    userId: string,
    history: string[],
    segment: string,
    limit: number,
  ): Promise<Recommendation[]> {
    // Имитация тяжёлого инференса
    await new Promise((r) => setTimeout(r, 300));

    const recommendations: Recommendation[] = [];
    for (let i = 0; i < limit; i++) {
      recommendations.push({
        itemId: `rec_item_${Math.floor(Math.random() * 900) + 100}`,
        score: Math.random(),
      });
    }
    return recommendations;
  }
}
