import { http, delay } from 'msw'
import { inertiaJson } from '@mocks/inertia'
import type { PerformanceCardDto } from '@widgets/performance-review'
import type { TrainingCardDto } from '@widgets/training-programs'

const performanceReview: PerformanceCardDto[] = [
  {
    description: 'Практические задачи, ревью кода и чек‑лист по soft-skills.',
    title: 'Тестирование навыков',
  },
  {
    description: 'Оценка по KPI и вкладу в проекты, плюс развёрнутая обратная связь от менторов.',
    title: 'Перформанс‑ревью',
  },
  {
    description: 'Сопоставление с вилками и требованиями - прозрачный отчёт и шаги роста.',
    title: 'Грейд и рынок',
  },
]

const trainingPrograms: TrainingCardDto[] = [
  {
    description: 'Стратегия поиска, позиционирование, резюме, собеседования.',
    title: 'Как искать работу',
  },
  {
    description: 'Портфолио, бриф, коммуникации, ценообразование, договорённости.',
    title: 'Как работать на фрилансе',
  },
  {
    description: 'Рынки, площадки, подготовка профилей и откликов на английском.',
    title: 'Как искать валютную работу',
  },
]

export const handlers = [
  http.get(/\/(\?.*)?$/, async ({ request }) => {
    console.log('MSW handler hit:', request.method, request.url)

    await delay()

    const page = {
      component: 'Home',
      props: {
        trainingPrograms,
        performanceReview,
        errors: {},
      },
      url: '/',
      version: 'msw-dev',
    }

    return inertiaJson(page)
  }),
]
