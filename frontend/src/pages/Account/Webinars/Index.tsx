import type { InertiaPage } from '@shared/types/inertia'
import { AppLayout } from '../components/AppLayout'
import { Webinars as WebinarsWidget, type WebinarDTO } from '@widgets/webinars'

type TProps = {
  webinars: WebinarDTO[]
}

const Webinars: InertiaPage<TProps> = ({ webinars }) => {
  return <WebinarsWidget webinars={webinars} />
}

Webinars.layout = page => (
  <AppLayout>
    {page}
  </AppLayout>
)

export default Webinars
