import { http, delay } from 'msw'
import { inertiaJson } from '@mocks/inertia'
import type { MenuItem } from '@shared/types/inertiaSharedData'

const menu: MenuItem[] = [
  { label: 'Мое обучение' },
  { label: 'Покупки и подписки',
    link: '/account/purchase' },
  { label: 'Вебинары',
    link: '/account/webinars' },
  { label: 'База знаний' },
  { label: 'Интервью' },
  { label: 'Грейдирование' },
  { label: 'Программы обучения' },
]

const makeHandler = ({ component, url}: { component: string, url: string }) =>
  http.get(url, async ({ request }) => {
    console.log('MSW handler hit:', request.method, request.url)
    await delay()
    return inertiaJson({
      component,
      props: {
        errors: {},
        menu,
      },
      url,
      version: 'msw-dev',
    })
  })

const routes = [
  {
    component: 'Account/Purchase/Index',
    url: '/account',
  },
  {
    component: 'Account/Purchase/Index',
    url: '/account/purchase',
  },
  {
    component: 'Account/Webinars/Index',
    url: '/account/webinars',
  },
] as const

export const handlers = routes.map(makeHandler)
