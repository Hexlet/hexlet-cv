import { http, delay } from 'msw'
import { inertiaJson } from '@mocks/inertia'
import { type MenuItem } from '@pages/personal-cabinet/components/Navbar'

const data: MenuItem[] = [
  {
    label: 'Мое обучение',
    link: '/personal-cabinet/my-learning',
  },
  {
    label: 'Покупки и подписки',
    link: '/personal-cabinet/pay-and-subscriptions',
  },
  {
    label: 'Вебинары',
  },
  {
    label: 'База знаний',
  },
  {
    label: 'Интервью',
  },
  {
    label: 'Грейдирование',
  },
  {
    label: 'Программы обучения',
  },
]

export const handlers = [
  http.get(/\/personal-cabinet(\?.*)?$/, async ({ request }) => {
    console.log('MSW handler hit:', request.method, request.url)

    await delay()

    return inertiaJson({
      component: 'personal-cabinet/PersonalCabinet',
      props: {
        errors: {},
        data,
      },
      url: '/personal-cabinet',
      version: 'msw-dev',
    })
  }),
  http.get(/\/personal-cabinet\/my-learning(\?.*)?$/, async ({ request }) => {
    console.log('MSW handler hit:', request.method, request.url)

    await delay()

    return inertiaJson({
      component: 'personal-cabinet/PersonalCabinet',
      props: {
        errors: {},
        data,
      },
      url: '/personal-cabinet/my-learning',
      version: 'msw-dev',
    })
  }),
  http.get(/\/personal-cabinet\/pay-and-subscriptions(\?.*)?$/, async ({ request }) => {
    console.log('MSW handler hit:', request.method, request.url)

    await delay()

    return inertiaJson({
      component: 'personal-cabinet/PersonalCabinet',
      props: {
        errors: {},
        data,
      },
      url: '/personal-cabinet/pay-and-subscriptions',
      version: 'msw-dev',
    })
  }),
]
