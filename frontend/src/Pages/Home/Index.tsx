import { Container, getGradient, useMantineTheme, Stack } from '@mantine/core'
import Footer from '../../Components/Footer.tsx'
import Header from '../../Components/Header.tsx'

export default function Index({ pageSections }) {
  const theme = useMantineTheme()
  console.log(`Page sections:`, pageSections)

  return (
    <Stack h={'100vh'} bg={getGradient({ deg: 135, from: 'black', to: '#00031a' }, theme)} justify="space-between">
      <Header />
      <Container>
      </Container>
      <Footer />
    </Stack>
  )
}