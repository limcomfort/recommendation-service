/**
 * Порт репозитория истории. Реализация — PostgresHistoryRepository.
 * Паттерн: Repository.
 */
export interface HistoryRepositoryPort {
  getViewedItemIds(userId: string, limit: number): Promise<string[]>;
  saveEvent(event: { userId: string; action: string; itemId: string }): Promise<void>;
}
