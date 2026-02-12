import type { ActivityCardsData, AuthProps, MenuItem } from '../inertiaSharedData/inertiaSharedProps'

declare module '@inertiajs/core' {
  interface PageProps extends AuthProps {
    activityCards?: ActivityCardsData
    locale?: string
    menu?: MenuItem[]
  }
}
