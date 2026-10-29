/**
 * Точка входа приложения.
 * Собирает DI-контейнер, подключает инфраструктуру, регистрирует роуты.
 */
import express from 'express';
import { buildContainer } from './config/container';

async function bootstrap(): Promise<void> {
  const container = buildContainer();

  // Подключаем инфраструктуру
  await container.cache.ping();
  console.log('✅ Connected to Redis');

  await container.queue.connect();
  console.log('✅ Connected to Kafka');

  // Демо: засеваем историю пользователю (в реальности — из БД)
  await container.historyRepo.seedHistory('user_123', ['item_a', 'item_b', 'item_c']);
  console.log('✅ History repository initialized (mock)');

  const app = express();
  app.use(express.json());

  app.get('/', (_req, res) => {
    res.json({ status: 'ok', service: 'Personalization & Recommendation API (Variant 7)' });
  });

  app.use('/api/v1', container.recommendationController.router);
  app.use('/api/v1', container.eventController.router);

  app.listen(8000, () => {
    console.log('🚀 Server is running on http://localhost:8000');
  });
}

bootstrap().catch((err) => {
  console.error('❌ Failed to start server:', err);
  process.exit(1);
});
