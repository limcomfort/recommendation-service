import express, { Request, Response } from 'express';
import Redis from 'ioredis';
import { Kafka, Producer } from 'kafkajs';
import { Pool } from 'pg';

// ==========================================
// 1. ИНИЦИАЛИЗАЦИЯ ИНФРАСТРУКТУРЫ
// ==========================================

// Кэш (Redis) — ключевой элемент для низкой задержки
const redis = new Redis({ host: 'localhost', port: 6379 });

// Очереди (Kafka) — для высокого RPS (асинхронные события)
const kafka = new Kafka({ clientId: 'recommendation-service', brokers: ['localhost:9092'] });
const producer: Producer = kafka.producer();

// БД (PostgreSQL) — хранит историю пользователя
const db = new Pool({
  host: 'localhost',
  port: 5432,
  user: 'postgres',
  password: 'postgres',
  database: 'recommendations',
});

const app = express();
app.use(express.json());

// ==========================================
// 2. ТИПЫ И ИНТЕРФЕЙСЫ
// ==========================================

interface RecommendationRequest {
  userId: string;
  segment: string;
  limit: number;
}

interface UserEvent {
  userId: string;
  action: 'view' | 'like' | 'add_to_cart';
  itemId: string;
}

// ==========================================
// 3. ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ (Имитация ML/БД)
// ==========================================

// Имитация тяжелого запроса в БД
async function fetchHistoryFromDb(userId: string): Promise<string[]> {
  await new Promise((resolve) => setTimeout(resolve, 100)); // Имитация сети
  return [`item_1`, `item_2`, `item_3`];
}

// Имитация тяжелых вычислений ML-модели
async function runMlModel(history: string[], segment: string, limit: number): Promise<string[]> {
  await new Promise((resolve) => setTimeout(resolve, 300)); // Имитация инференса
  return Array.from({ length: limit }, () => `rec_item_${Math.floor(Math.random() * 900) + 100}`);
}

// ==========================================
// 4. ЭНДПОИНТЫ (Соответствуют диаграмме)
// ==========================================

app.get('/', (req: Request, res: Response) => {
  res.json({ status: 'ok', service: 'Personalization & Recommendation API (TS)' });
});

// Клиент -> Сервер -> Кэш -> (если нет) -> БД -> ML -> Кэш
app.post('/api/v1/recommendations', async (req: Request, res: Response) => {
  try {
    const { userId, segment = 'default', limit = 10 }: RecommendationRequest = req.body;
    const cacheKey = `recs:${userId}:${segment}:${limit}`;

    // ШАГ 1: Проверяем Кэш (Redis) — обеспечивает низкую задержку
    const cachedData = await redis.get(cacheKey);

    if (cachedData) {
      // CACHE HIT: Мгновенный ответ
      return res.json({
        source: 'cache',
        latency: 'low',
        data: JSON.parse(cachedData),
      });
    }

    // ШАГ 2: CACHE MISS — идем в БД за историей
    const history = await fetchHistoryFromDb(userId);

    // ШАГ 3: Считаем рекомендации через ML-модель
    const recommendations = await runMlModel(history, segment, limit);

    // ШАГ 4: Сохраняем результат в Кэш (TTL = 5 минут)
    await redis.setex(cacheKey, 300, JSON.stringify(recommendations));

    return res.json({
      source: 'ml_model',
      latency: 'high',
      data: recommendations,
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal server error' });
  }
});

// Клиент -> Сервер -> Очереди (Kafka)
app.post('/api/v1/events', async (req: Request, res: Response) => {
  try {
    const event: UserEvent = req.body;

    // Отправляем событие в Очередь асинхронно (мгновенно для клиента)
    // В Express нет BackgroundTasks как в FastAPI, поэтому мы не ждем (await) отправку.
    producer.send({
      topic: 'user_events_topic',
      messages: [{ value: JSON.stringify(event) }],
    }).catch((err) => console.error('[QUEUE ERROR]', err));

    console.log(`[QUEUE] Sent event to Kafka:`, event);

    return res.json({ status: 'accepted', message: 'Event queued for async processing' });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Failed to process event' });
  }
});

// ==========================================
// 5. ЗАПУСК СЕРВЕРА
// ==========================================

async function startServer() {
  try {
    // Подключаемся к Kafka
    await producer.connect();
    console.log('✅ Connected to Kafka');

    // Проверяем Redis
    await redis.ping();
    console.log('✅ Connected to Redis');

    // Имитация подключения к БД
    console.log('✅ Database pool initialized');

    app.listen(8000, () => {
      console.log('🚀 Server is running on http://localhost:8000');
    });
  } catch (error) {
    console.error('❌ Failed to start server:', error);
    process.exit(1);
  }
}

startServer();
