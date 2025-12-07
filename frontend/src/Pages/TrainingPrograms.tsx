import { Container, Card, Text, Group, Title, Grid, Button } from '@mantine/core';

const TrainingPrograms  = () => {

  return (
    <Container size="lg" py="xs">
      <Card radius="lg" p="md" mb="lg">
        <Title order={1} c='white' fw='bold' mb="md">
          Наши программы обучения
        </Title>
        <Grid>
          <Grid.Col span={{ base: 12, md: 4 }}>
            <Card radius="lg" bg='dark.5' h="100%">
              <Group gap='sm'>
                <Text fz='h3' c='white'>
                  Как искать работу
                </Text>
              </Group>
              <Text size='sm' c='white' mb="lg" mt="xs">
                Стратегия поиска, позиционирование, резюме, собеседования.
              </Text>
              <Button radius="lg" color="white" w="fit-content" variant="default">
                <Text c="dark" size="sm">
                  Узнать подробнее
                </Text>
              </Button>
            </Card>
          </Grid.Col>
          <Grid.Col span={{ base: 12, md: 4 }}>
            <Card radius="lg" bg='dark.5' h="100%">
              <Group gap='sm'>
                <Text fz='h3' c='white'>
                  Как работать на фрилансе
                </Text>
              </Group>
              <Text size='sm' c='white' mb="lg" mt="xs">
                Портфолио, бриф, коммуникации, ценообразование, договорённости.
              </Text>
              <Button radius="lg" color="white" w="fit-content" variant="default">
                <Text c="dark" size="sm">
                  Узнать подробнее
                </Text>
              </Button>
            </Card>
          </Grid.Col>
          <Grid.Col span={{ base: 12, md: 4 }}>
            <Card radius="lg" bg='dark.5' h="100%">
              <Group gap='sm'>
                <Text fz='h3' c='white'>
                  Как искать валютную работу
                </Text>
              </Group>
              <Text size='sm' c='white' mb="lg" mt="xs">
                Рынки, площадки, подготовка профилей и откликов на английском.
              </Text>
              <Button radius="lg" color="white" w="fit-content" variant="default">
                <Text c="dark" size="sm">
                  Узнать подробнее
                </Text>
              </Button>           
            </Card>
          </Grid.Col>
        </Grid>
      </Card>
    </Container>
  );
}

export default TrainingPrograms 