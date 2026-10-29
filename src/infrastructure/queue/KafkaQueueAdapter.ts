/**
 * Адаптер очереди: Kafka.
 * Реализует QueuePort (паттерн Adapter + Pub-Sub через Kafka).
 * Добавлен Retry (простая реализация).
 */
import { Kafka, Producer } from 'kafkajs';
import { QueuePort } from '../../domain/ports/QueuePort';
import { UserEvent } from '../../domain/entities/UserEvent';

export class KafkaQueueAdapter implements QueuePort {
  private readonly producer: Producer;

  constructor(brokers: string[] = ['localhost:9092']) {
    const kafka = new Kafka({ clientId: 'recommendation-service', brokers });
    this.producer = kafka.producer();
  }

  async connect(): Promise<void> {
    await this.producer.connect();
  }

  async disconnect(): Promise<void> {
    await this.producer.disconnect();
  }

  async publish(topic: string, event: UserEvent): Promise<void> {
    // Паттерн Retry: 3 попытки с задержкой
    const maxRetries = 3;
    for (let attempt = 1; attempt <= maxRetries; attempt++) {
      try {
        await this.producer.send({
          topic,
          messages: [{ value: JSON.stringify(event) }],
        });
        console.log(`[QUEUE] Sent event to Kafka (attempt ${attempt}):`, event);
        return;
      } catch (err) {
        const msg = err instanceof Error ? err.message : String(err);
        console.warn(`[QUEUE] Kafka send failed (attempt ${attempt}/${maxRetries}):`, msg);
        if (attempt === maxRetries) throw err;
        await new Promise((r) => setTimeout(r, 500 * attempt));
      }
    }
  }
}
