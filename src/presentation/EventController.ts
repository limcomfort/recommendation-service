/**
 * Контроллер событий (presentation layer).
 * Отвечает только за HTTP — бизнес-логика в use case.
 */
import { Router, Request, Response } from 'express';
import { TrackUserEventUseCase } from '../application/TrackUserEventUseCase';

export class EventController {
  readonly router: Router;

  constructor(private readonly useCase: TrackUserEventUseCase) {
    this.router = Router();
    this.router.post('/events', this.handle.bind(this));
  }

  private async handle(req: Request, res: Response): Promise<void> {
    try {
      const { userId, action, itemId } = req.body ?? {};
      if (!userId || !action || !itemId) {
        res.status(400).json({ error: 'userId, action, itemId are required' });
        return;
      }
      await this.useCase.execute({ userId, action, itemId });
      res.json({ status: 'accepted', message: 'Event queued for async processing' });
    } catch (err) {
      const msg = err instanceof Error ? err.message : String(err);
      res.status(500).json({ error: msg });
    }
  }
}
