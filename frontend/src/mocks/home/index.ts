import { http, delay } from 'msw'
import type { IArticle } from '@widgets/articles'
import { inertiaJson } from '@mocks/inertia'

const articles: IArticle[] = [
  { readingTime: 1,
    tags: ['Про собеседование', 'Создаем резюме'],
    title: 'Сопроводительное письмо и резюме для IT: примеры и советы' },
  { readingTime: 3,
    tags: ['Про автоклики'],
    title: 'Как настроить автоотклики на hh: быстрый поиск работы с ИИ' },
  { readingTime: 5,
    tags: ['Проходим собеседование'],
    title: 'Как пройти собеседование: частые ошибки и вопросы' },
]

export const handlers = [
  http.get(/\/(\?.*)?$/, async ({ request }) => {
    console.log('MSW handler hit:', request.method, request.url)

    await delay()

    const page = {
      component: 'Home',
      props: {
        articles,
        errors: {},
      },
      url: '/',
      version: 'msw-dev',
    }

    return inertiaJson(page)
  }),
]
