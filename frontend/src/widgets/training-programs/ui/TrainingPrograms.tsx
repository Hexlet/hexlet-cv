import { Container, Card, Text, Title, Grid, Button } from '@mantine/core'
import { useTranslation } from 'react-i18next'

interface TrainingCard {
  key: string
  title: string
  description: string
  buttonText: string
}

export const TrainingPrograms: React.FC = () => {
  const { t } = useTranslation()

  const trainingCards: TrainingCard[] = [
    {
      key: 'jobSearch',
      title: t('homePage.trainingPrograms.cards.jobSearch.title'),
      description: t('homePage.trainingPrograms.cards.jobSearch.description'),
      buttonText: t('homePage.trainingPrograms.cards.jobSearch.button'),
    },
    {
      key: 'freelance',
      title: t('homePage.trainingPrograms.cards.freelance.title'),
      description: t('homePage.trainingPrograms.cards.freelance.description'),
      buttonText: t('homePage.trainingPrograms.cards.freelance.button'),
    },
    {
      key: 'foreignJobs',
      title: t('homePage.trainingPrograms.cards.foreignJobs.title'),
      description: t('homePage.trainingPrograms.cards.foreignJobs.description'),
      buttonText: t('homePage.trainingPrograms.cards.foreignJobs.button'),
    },
  ]

  return (
    <Container size="lg" py="xs">
      <Title order={1} fw="bold" mb="md">
        {t('homePage.trainingPrograms.title')}
      </Title>
      <Grid>
        {trainingCards.map(card => (
          <Grid.Col
            key={card.key}
            span={{
              base: 12,
              md: 4,
            }}
          >
            <Card radius="lg" h="100%">
              <Text fz="h3">
                {card.title}
              </Text>
              <Text size="sm" mb="lg" mt="xs">
                {card.description}
              </Text>
              <Button radius="lg" w="fit-content">
                <Text size="sm">
                  {card.buttonText}
                </Text>
              </Button>
            </Card>
          </Grid.Col>
        ))}
      </Grid>
    </Container>
  )
}
