import { MantineProvider } from '@mantine/core'
import '@mantine/core/styles.css'

export const UIProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  return (
    <MantineProvider defaultColorScheme="dark">
      { children }
    </MantineProvider>
  )
}
