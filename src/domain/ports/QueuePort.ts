/**
 * Порт очереди. Реализация — KafkaQueueAdapter.
 * Паттерн: Observer / Pub-Sub (через Kafka).
 */
import { UserEvent } from '../entities/UserEvent';

export interface QueuePort {
  publish(topic: string, event: UserEvent): Promise<void>;
  connect(): Promise<void>;
  disconnect(): Promise<void>;
}
