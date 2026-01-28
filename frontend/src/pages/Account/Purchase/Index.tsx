import type { InertiaPage } from '@shared/types/inertia'
import { AppLayout } from '../components/AppLayout'
import { PurchaseList } from '@widgets/purchase-list'

const Purchase: InertiaPage = () => {
  return <PurchaseList />
}

Purchase.layout = (page) => <AppLayout>{page}</AppLayout>

export default Purchase
