import { http, delay } from 'msw'
import { inertiaJson } from '../inertia'

const mockInterviews = [
    { id: 1, title: 'Интервью с продактом', speaker: 'Алексей С.', videoUrl: '', isPublished: true },
    { id: 2, title: 'Интервью: редактор', speaker: 'Наталья О.', videoUrl: '', isPublished: false },
];

export const adminHandlers = [
  http.get('*/admin/interview', async ({ request }) => {
    console.log('MSW: handler hit', request.url)

    await delay()

    return inertiaJson({
      component: 'Admin/Interview/Index',
      props: {
        interviews: mockInterviews,
      },
      url: new URL(request.url).pathname,
      version: 'msw-dev',
    })
  }),
]
