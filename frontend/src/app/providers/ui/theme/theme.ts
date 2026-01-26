import { createTheme, Divider } from '@mantine/core'

export const theme = createTheme({
  components: {
    Divider: Divider.extend({
      styles: {
        root: {
          '--divider-color': 'var(--ds-divider-color)',
        } as React.CSSProperties,
      },
    }),
  },
})
