import type { MenuItem } from '../inertiaSharedData/inertiaSharedProps'
import { ActivityCardsData } from '../inertiaSharedData/inertiaSharedProps'

declare module '@inertiajs/core' {
  interface PageProps {
    menu: MenuItem[]
    activityCards: ActivityCardsData
  }
}
