/**
 * Доменная сущность: Пользователь.
 * Не знает ни о БД, ни о Redis, ни о Kafka.
 */
export interface User {
  id: string;
  segment: string;
}
