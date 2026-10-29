/**
 * Адаптер репозитория истории: PostgreSQL.
 * Реализует HistoryRepositoryPort (паттерн Repository).
 *
 * ВНИМАНИЕ: в учебном демо реальные SQL-запросы не выполняются —
 * используется in-memory mock, чтобы не требовать поднятого Postgres.
 * Интерфейс полностью соответствует реальному адаптеру.
 */
import { HistoryRepositoryPort } from '../../domain/ports/HistoryRepositoryPort';

export class PostgresHistoryRepository implements HistoryRepositoryPort {
  // В реальном проекте здесь был бы Pool от 'pg'
  // private readonly pool: Pool;
  private readonly mockHistory = new Map<string, string[]>();
  private readonly mockEvents: Array<{ userId: string; action: string; itemId: string }> = [];

  async getViewedItemIds(userId: string, limit: number): Promise<string[]> {
    // Имитация задержки сети/БД
    await new Promise((r) => setTimeout(r, 100));
    return (this.mockHistory.get(userId) ?? []).slice(0, limit);
  }

  async saveEvent(event: { userId: string; action: string; itemId: string }): Promise<void> {
    // В реальности — INSERT INTO events ...
    this.mockEvents.push(event);
  }

  /** Только для демо: добавляет историю пользователю */
  async seedHistory(userId: string, itemIds: string[]): Promise<void> {
    this.mockHistory.set(userId, itemIds);
  }
}
