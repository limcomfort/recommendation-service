/**
 * Адаптер кэша: Redis.
 * Реализует CachePort (паттерн Adapter + Ports & Adapters).
 */
import Redis from 'ioredis';
import { CachePort } from '../../domain/ports/CachePort';

export class RedisCacheAdapter implements CachePort {
  private readonly client: Redis;

  constructor(host = 'localhost', port = 6379) {
    // Паттерн Singleton: один клиент на приложение
    this.client = new Redis({ host, port });
  }

  async get<T>(key: string): Promise<T | null> {
    const raw = await this.client.get(key);
    return raw ? (JSON.parse(raw) as T) : null;
  }

  async set<T>(key: string, value: T, ttlSeconds: number): Promise<void> {
    await this.client.setex(key, ttlSeconds, JSON.stringify(value));
  }

  async delete(key: string): Promise<void> {
    await this.client.del(key);
  }

  async ping(): Promise<void> {
    await this.client.ping();
  }
}
