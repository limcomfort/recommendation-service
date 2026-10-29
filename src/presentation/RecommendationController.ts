/**
 * Контроллер (N-tier: presentation layer).
 * Паттерн DTO: входные данные отделены от доменных сущностей.
 * Отвечает только за HTTP — бизнес-логика в use case.
 */
import { Router, Request, Response } from 'express';
import { GetRecommendationsUseCase } from '../application/GetRecommendationsUseCase';

export class RecommendationController {
  readonly router: Router;

  constructor(private readonly useCase: GetRecommendationsUseCase) {
    this.router = Router();
    this.router.post('/recommendations', this.handle.bind(this));
  }

  private async handle(req: Request, res: Response): Promise<void> {
    try {
      const { userId, segment = 'default', limit = 10 } = req.body ?? {};
      if (!userId) {
        res.status(400).json({ error: 'userId is required' });
        return;
      }
      const output = await this.useCase.execute({ userId, segment, limit });
      res.json(output);
    } catch (err) {
      const msg = err instanceof Error ? err.message : String(err);
      res.status(500).json({ error: msg });
    }
  }
}
