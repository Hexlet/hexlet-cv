import { Container, Card, Text, Group, Title, Grid, Button } from '@mantine/core'
import { useTranslation } from 'react-i18next'

export const Webinars = () => {
  const { t } = useTranslation()

  return (
    <Container size="lg" py="xs">
      <Title order={1} fw="bold" mb="md">
        {t('homePage.webinars.title')}
      </Title>
      <Grid>
        <Grid.Col span={{ base: 12,
          md: 4 }}
        >
          <Card radius="lg" bg="dark.5" h="100%">
            <Group gap="sm">
              <Text fz="h3" fw="bold">
                {t('homePage.webinars.cards.withHR.title')}
              </Text>
            </Group>
            <Text size="sm" mb="lg" mt="xs">
              {t('homePage.webinars.cards.withHR.description')}
            </Text>
            <Button radius="lg" color="white" w="fit-content">
              <Text size="sm">{t('homePage.webinars.cards.viewSchedule')}</Text>
            </Button>
          </Card>
        </Grid.Col>
        <Grid.Col span={{ base: 12,
          md: 4 }}
        >
          <Card radius="lg" bg="dark.5" h="100%">
            <Group gap="sm">
              <Text fz="h3" fw="bold">
                {t('homePage.webinars.cards.responses.title')}
              </Text>
            </Group>
            <Text size="sm" mb="lg" mt="xs">
              {t('homePage.webinars.cards.responses.description')}
            </Text>
            <Button radius="lg" color="white" w="fit-content">
              <Text size="sm">{t('homePage.webinars.cards.viewSchedule')}</Text>
            </Button>
          </Card>
        </Grid.Col>
        <Grid.Col span={{ base: 12,
          md: 4 }}
        >
          <Card radius="lg" bg="dark.5" h="100%">
            <Group gap="sm">
              <Text fz="h3" fw="bold">
                {t('homePage.webinars.cards.tests.title')}
              </Text>
            </Group>
            <Text size="sm" mb="lg" mt="xs">
              {t('homePage.webinars.cards.tests.description')}
            </Text>
            <Button radius="lg" color="white" w="fit-content">
              <Text size="sm">{t('homePage.webinars.cards.viewSchedule')}</Text>
            </Button>
          </Card>
        </Grid.Col>
      </Grid>
    </Container>
  )
}
