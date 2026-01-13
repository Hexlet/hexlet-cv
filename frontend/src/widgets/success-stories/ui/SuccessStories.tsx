import {
  Card,
  Title,
  Text,
  Container,
  Button,
  Stack,
  Image
} from '@mantine/core'
import { Carousel } from '@mantine/carousel'
import { useTranslation } from 'react-i18next'

export const SuccessStories: React.FC = () => {
  const { t } = useTranslation()

  const stories = [
    {
      title: 'Кейс Семёна: оффер за 3 недели',
      description: 'Сняли рутину, сфокусировались на собесах — 7 интервью и 2 оффера.',
    },
    {
      title: '3 оффера за 10 мин в день',
      description: 'Автоотклики + точные правки резюме.',
    },
    {
      title: 'Здесь может быть твой кейс',
      description: 'Поделись историей — оформим и покажем на сайте.',
    },
    {
      title: 'Ещё кейс',
      description: 'Путь от джуна до оффера.',
    },
    {
      title: 'Дополнительный кейс',
      description: 'Ещё одна история успеха.',
    },
  ]

  return (
    <Container size="lg" py="xl">
      <Title order={1} mb="md">
        {t('homePage.successStories.title')}
      </Title>

      <Carousel
        height="100%"
        type="container"
        slideSize={{
          'base': '100%',
          '480px': '50%',
          '730px': '33.333%',
          '900px': '25%',
        }}
        slideGap={{
          'base': 'xs',
          '480px': 'sm',
          '730px': 'md',
          '900px': 'lg',
        }}
        emblaOptions={{
          loop: true,
          align: 'start',
        }}
      >
        {stories.map((story, index) => (
          <Carousel.Slide key={index}>
            <Card p="sm" radius="lg" h="100%">
              <Stack gap={0} h="100%">
                <Image
                  src="../../../../public/vite.svg"
                  height={180}
                  radius="lg"
                  alt={story.title}
                  fit="fill"
                  loading="lazy"
                />
                <Text size="sm" fw="bold">
                  {story.title}
                </Text>
                <Text size="xs" flex={1}>
                  {story.description}
                </Text>
                <Button
                  mt="md"
                  w="fit-content"
                  radius="lg"
                >
                  {t('homePage.successStories.button')}
                </Button>
              </Stack>
            </Card>
          </Carousel.Slide>
        ))}
      </Carousel>
    </Container>
  )
}
