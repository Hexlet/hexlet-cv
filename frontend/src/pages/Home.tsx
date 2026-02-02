import {
  Container,
  getGradient,
  useMantineTheme,
  Stack,
  Group,
  Text,
  SimpleGrid,
  Title,
  Pill,
  Box,
  alpha
} from '@mantine/core'
import { Footer } from '@widgets/Footer'
import { Header } from '@widgets/Header'
import { AboutUs } from '@widgets/about-us'
import { WhoWeAre } from '@widgets/who-we-are'
import { CommercialProjects } from '@widgets/commercial-projects'
import { MarketAnalytics } from '@widgets/market-analytics'
import { TrainingPrograms } from '@widgets/training-programs'
import { Communities } from '@widgets/communities'
import { KnowledgeBaseAndInterviews } from '@widgets/knowledge-base-and-interviews'

type PageSection = {
  id: number
  pageKey: string
  sectionKey: string
  title: string
  content: string
  active: boolean
  createdAt: string
  updatedAt: string
}

type IndexProps = {
  pageSections: Readonly<PageSection[]>
}

// Компонент-пустышка для демонстрации пропса pageSections (Потом УДАЛИТЬ!)
const sample: JSX.Element = (
  <SimpleGrid
    cols={2}
    spacing="xs"
    bg={alpha('#787878', 0.2)}
    p="xl"
    bdrs="lg"
    bd={'1px solid ' + alpha('#FFFFFF', 0.1)}
    my={20}
  >
    <Stack gap="md" align="strech">
      <Title order={1} fw={700}>
        Привет, мы Хекслет
      </Title>
      <Text mb="sm">
        экосистема для старта и развития карьеры в IT:
      </Text>
      <Group gap="xs">
        <Pill bg="blue" radius="xl" size="md">
          Составлять резюме
        </Pill>
        <Pill bg="blue" radius="xl" size="md">
          Откликаться
        </Pill>
        <Pill bg="blue" radius="xl" size="md">
          Искать ванкансии и стажировки
        </Pill>
        <Pill bg="blue" radius="xl" size="md">
          Переписываться с рекрутерами
        </Pill>
        <Pill bg="blue" radius="xl" size="md">
          Писать сопроводительные
        </Pill>
        <Pill bg="blue" radius="xl" size="md">
          Готовится к интервью
        </Pill>
        <Pill bg="blue" radius="xl" size="md">
          Получать комерческий опыт
        </Pill>
      </Group>
    </Stack>
    <Group justify="flex-end">
      <Box
        w={250}
        h={250}
        style={{
          borderRadius: '50%',
          background: 'linear-gradient(135deg, #707070 0%, #303030 100%)',
          opacity: 0.8,
        }}
      />
    </Group>
  </SimpleGrid>
)
// Конец компонента-пустышки

const Index: React.FC<IndexProps> = ({ pageSections }) => {
  const theme = useMantineTheme()
  console.log(`Page sections:`, pageSections)

  // Начало компонента-пустышки для демонстрации пропса pageSections (Потом удалить)
  const repeatCount = 5 // Колличество повторений примера inertia компонента
  const repeatedSamples: React.ReactNode[] = []
  for (let i = 0; i < repeatCount; i++) {
    repeatedSamples.push(<div key={i}>{sample}</div>)
  }
  // Конец компонента-пустышки

  return (
    <Stack
      // mih="100vh"
      bg={getGradient({
        deg: 135,
        from: 'black',
        to: '#00031a',
      }, theme)}
      justify="space-between"
    >
      <Header />
      {/* здесь передаем пропсы страниц для отрисовки, а пока БД пустая я сделал компонент пустышку для демонстрации */}
      <Container size="xl">
        <AboutUs />
        <WhoWeAre />
        <CommercialProjects />
        <MarketAnalytics />
        <TrainingPrograms />
        <KnowledgeBaseAndInterviews />
        {repeatedSamples}
        <Communities />
        {/* <Link href="/account">Personal Cabinet</Link> */}
      </Container>
      <Footer />
    </Stack>
  )
}

export default Index
