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
import Footer from '@widgets/Footer.tsx'
import Header from '@widgets/Header.tsx'

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
  pageSections: PageSection[]
}

// Компонент-пустышка для демонстрации пропса pageSections (Потом УДАЛИТЬ!)
const sample = (
  <SimpleGrid cols={2} spacing="xs" bg={alpha('#787878', 0.2)} p="xl" bdrs="lg" bd={'1px solid ' + alpha('#FFFFFF', 0.1)} my={20}>
    <Stack gap="md" align="strech">
      <Title order={1} c="white" fw={700}>
        Привет, мы Хекслет
      </Title>
      <Text c="white" mb="sm">
        экосистема для старта и развития карьеры в IT:
      </Text>
      <Group gap="xs">
        <Pill bg="blue" c="white" radius="xl" size="md">
          Составлять резюме
        </Pill>
        <Pill bg="blue" c="white" radius="xl" size="md">
          Откликаться
        </Pill>
        <Pill bg="blue" c="white" radius="xl" size="md">
          Искать ванкансии и стажировки
        </Pill>
        <Pill bg="blue" c="white" radius="xl" size="md">
          Переписываться с рекрутерами
        </Pill>
        <Pill bg="blue" c="white" radius="xl" size="md">
          Писать сопроводительные
        </Pill>
        <Pill bg="blue" c="white" radius="xl" size="md">
          Готовится к интервью
        </Pill>
        <Pill bg="blue" c="white" radius="xl" size="md">
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

export default function Index({ pageSections }: IndexProps) {
  const theme = useMantineTheme()
  console.log(`Page sections:`, pageSections)

  // Начало компонента-пустышки для демонстрации пропса pageSections (Потом удалить)
  const repeatCount = 5 // Колличество повторений примера inertia компонента
  const repeatedSamples = []
  for (let i = 0; i < repeatCount; i++) {
    repeatedSamples.push(
      <div key={i}>
        {sample}
      </div>
    )
  }
  // Конец компонента-пустышки

  return (
    <Stack
      mih="100vh"
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
        {repeatedSamples}
      </Container>
      <Footer />
    </Stack>
  )
}
