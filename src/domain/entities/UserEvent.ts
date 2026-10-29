/**
 * Доменная сущность: Событие пользователя (просмотр, лайк, корзина).
 */
export type UserAction = 'view' | 'like' | 'add_to_cart';

export interface UserEvent {
  userId: string;
  action: UserAction;
  itemId: string;
  timestamp: Date;
}
