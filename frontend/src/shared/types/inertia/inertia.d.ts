import type { MenuItem } from '../inertiaSharedData/inertiaSharedProps'

declare module '@inertiajs/core' {
  interface PageProps {
    menu: MenuItem[]
  }
}