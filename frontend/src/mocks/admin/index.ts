import { http, delay } from 'msw'
import { inertiaJson } from '../inertia'

const mockPrograms = [
    { id: 1, name: 'Frontend-разработчик', duration: 6, lessons: 48, isPublished: true },
    { id: 2, name: 'QA-инженер', duration: 4, lessons: 32, isPublished: false }
]

export const adminHandlers = [
  http.get('*/admin/programs', async ({ request }) => {
    console.log('MSW: handler hit', request.url)

    await delay()

    return inertiaJson({
      component: 'Admin/Programs/Index',
      props: {
        programs: mockPrograms,
      },
      url: new URL(request.url).pathname,
      version: 'msw-dev',
    })
  }),
]
