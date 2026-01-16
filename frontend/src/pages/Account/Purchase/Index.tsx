import type { InertiaPage } from '@shared/types/inertia'
import { AppLayout } from '../components/AppLayout'

const Purchase: InertiaPage = () => {
  return 'Purchase'
}

Purchase.layout = page => (
  <AppLayout>
    {page}
  </AppLayout>
)

export default Purchase
