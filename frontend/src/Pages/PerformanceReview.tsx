import { Container, Card, Text, Group, Title, Grid, Button } from '@mantine/core';

const PerformanceReview = () => {

  return (
    <Container size="lg" py="xs">
      <Card radius="lg" p="md" mb="lg" bg="dark">
        <Title order={1} c='white' fw='bold' mb="md">
          Performance review и определение грейда
        </Title>
        <Grid>
          <Grid.Col span={{ base: 12, md: 4 }}>
            <Card radius="lg" bg='dark.5' h="100%">
              <Group gap='sm'>
                <Text fz='h3' c='white'>
                  Тестирование навыков
                </Text>
              </Group>
              <Text size='sm' c='white' mb="lg" mt="xs">
                Практические задачи, ревью кода и чек‑лист по soft‑skills.
              </Text>
              <Button radius="lg" color="white" w="fit-content" variant="default">
                <Text c="dark" size="sm">
                  Записаться
                </Text>
              </Button>
            </Card>
          </Grid.Col>
          <Grid.Col span={{ base: 12, md: 4 }}>
            <Card radius="lg" bg='dark.5' h="100%">
              <Group gap='sm'>
                <Text fz='h3' c='white'>
                  Перформанс‑ревью
                </Text>
              </Group>
              <Text size='sm' c='white' mb="lg" mt="xs">
                Оценка по KPI и вкладу в проекты, плюс развёрнутая обратная связь от менторов.
              </Text>
              <Button radius="lg" color="white" w="fit-content" variant="default">
                <Text c="dark" size="sm">
                  Записаться
                </Text>
              </Button>
            </Card>
          </Grid.Col>
          <Grid.Col span={{ base: 12, md: 4 }}>
            <Card radius="lg" bg='dark.5' h="100%">
              <Group gap='sm'>
                <Text fz='h3' c='white'>
                  Грейд и рынок
                </Text>
              </Group>
              <Text size='sm' c='white' mb="lg" mt="xs">
                Сопоставление с вилками и требованиями - прозрачный отчёт и шаги роста.
              </Text>
              <Button radius="lg" color="white" w="fit-content" variant="default">
                <Text c="dark" size="sm">
                  Записаться
                </Text>
              </Button>           
            </Card>
          </Grid.Col>
        </Grid>
      </Card>
    </Container>
  );
}

export default PerformanceReview 