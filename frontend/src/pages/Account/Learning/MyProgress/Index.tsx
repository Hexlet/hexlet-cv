import type { InertiaPage } from '@shared/types/inertia'
import { AppLayout } from '@pages/Account/components/AppLayout'
import { ProgressList } from '@widgets/progress-list'

const MyProgress: InertiaPage = () => {
  return <ProgressList />
}

MyProgress.layout = (page) => <AppLayout>{page}</AppLayout>

export default MyProgress
