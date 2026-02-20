import type { InertiaPage } from '@shared/types/inertia'
import type { IProgressResponse } from '@widgets/progress-list/types'
import { AppLayout } from '@pages/Account/components/AppLayout'
import { ProgressList } from '@widgets/progress-list'
import { LearningProgressCard } from '@widgets/learning-progress-card/'
import { OpenProgramButton } from '@features/open-lerning-program'

const MyProgress: InertiaPage<IProgressResponse> = ({
  progress,
  pagination,
}) => {
  return (
    <ProgressList
      progress={progress}
      pagination={pagination}
      renderItem={(program) => (
        <LearningProgressCard
          key={program.id}
          program={program}
          actionButton={
            <OpenProgramButton programId={program.id}>
              Открыть
            </OpenProgramButton>
          }
        />
      )}
    />
  )
}

MyProgress.layout = (page) => <AppLayout>{page}</AppLayout>

export default MyProgress
