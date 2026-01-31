import { http, delay } from 'msw'
import { inertiaJson } from '@mocks/inertia'

export const handlers = [
  http.get(/\/(\?.*)?$/, async ({ request }) => {
    console.log('MSW handler hit:', request.method, request.url)

    await delay()

    return inertiaJson({
      component: 'Home',
      props: {
        errors: {},
      },
      url: '/',
      version: 'msw-dev',
    })
  }),
]
