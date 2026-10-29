/**
 * Порт ML-модели. Реализация — MockMlModelAdapter.
 * Позволяет заменить mock на реальную модель (TensorFlow/PyTorch) без изменения use case.
 */
import { Recommendation } from '../entities/Recommendation';

export interface MlModelPort {
  predict(userId: string, history: string[], segment: string, limit: number): Promise<Recommendation[]>;
}
