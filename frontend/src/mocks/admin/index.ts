import { http, delay } from 'msw'
import { inertiaJson } from '../inertia'
import type { InterviewsEntry } from '@widgets/admin-interviews/ui/AdminInterviews'
import type { StudyProgramsEntry } from '@widgets/admin-study-programs';

const mockInterviews: InterviewsEntry[] = [
    { id: 1, title: 'Интервью с продактом', speaker: 'Алексей С.', videoUrl: '', isPublished: true },
    { id: 2, title: 'Интервью: редактор', speaker: 'Наталья О.', videoUrl: '', isPublished: false },
];

const mockPrograms: StudyProgramsEntry[] = [
    { id: 1, name: 'Frontend-разработчик', duration: 6, lessons: 48, isPublished: true },
    { id: 2, name: 'QA-инженер', duration: 4, lessons: 32, isPublished: false }
]

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
