/**
 * Use Case: зафиксировать событие пользователя.
 * Сценарий:
 *   1. Обернуть в доменную сущность UserEvent.
 *   2. Опубликовать в очередь (Kafka).
 *
 * Асинхронность: use case не ждёт обработки события воркером.
 * Паттерн: Observer / Pub-Sub.
 */
import { QueuePort } from '../domain/ports/QueuePort';
import { UserEvent } from '../domain/entities/UserEvent';

export interface TrackUserEventInput {
  userId: string;
  action: 'view' | 'like' | 'add_to_cart';
  itemId: string;
}

export class TrackUserEventUseCase {
  constructor(
    private readonly queue: QueuePort,
    private readonly topic: string,
  ) {}

  async execute(input: TrackUserEventInput): Promise<void> {
    const event: UserEvent = {
      userId: input.userId,
      action: input.action,
      itemId: input.itemId,
      timestamp: new Date(),
    };
    await this.queue.publish(this.topic, event);
  }
}
