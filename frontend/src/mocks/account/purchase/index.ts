import { http, delay } from 'msw'
import { inertiaJson } from '@mocks/inertia'
import { menu, activityCards } from '../index'

export const purchaseHandlers = [
  http.get('/account/purchase', async () => {
    await delay()
    return inertiaJson({
      component: 'Account/Purchase/Index',
      props: {
        menu,
        activityCards,
        purchases: {
          content: [
            {
              id: 1,
              date: '16.01.2025',
              name: 'Подписка 2026',
              price: 1200,
              status: 'active', // canceled, expired
              recieptUrl: '#', //url для скачивания чека или null если курс бесплатный
            },
            {
              id: 2,
              date: '17.01.2025',
              name: 'Вебинар 2026',
              price: 112,
              status: 'active', // canceled, expired
              recieptUrl: '#',
            },
            {
              id: 3,
              date: '17.01.2025',
              name: 'Курс JS',
              price: 0,
              status: 'active', // canceled, expired
            },
          ],
          totalItems: 3,
        },
      },
      url: '/account/purchase',
    })
  }),
]
