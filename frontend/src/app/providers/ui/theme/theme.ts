import { createTheme, Divider, Card } from '@mantine/core'

export const theme = createTheme({
  components: {
    Divider: Divider.extend({
      styles: {
        root: {
          '--divider-color': 'var(--ds-divider-color)',
        } as React.CSSProperties,
      },
    }),
    Card: Card.extend({
      styles: () => ({
        root: {
          '--paper-bg': 'var(--ds-surface-card)',
        },
      }),
    }),
  },
})
