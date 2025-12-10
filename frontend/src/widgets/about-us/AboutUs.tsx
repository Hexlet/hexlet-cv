import {
  Container,
  Text,
  Card,
  Grid,
  Button,
} from '@mantine/core';

export const AboutUs = () => {
  return (
    <Container size="lg" my="lg" py="xs">
      <Grid>
        <Grid.Col span={{ base: 12,
          md: 7 }}>
          <Container mr="lg" pl={0}>
            <Text fz="3.2rem" c="white" fw={700} lh={1}>
              Найти работу в IT легче, чем ты думаешь
            </Text>
            <Text c="white" size="lg" my="md">
              С Hexlet Карьерой ты получаешь офферы быстрее - за недели, а не месяцы. 
            </Text>
            <Button radius="md" w="fit-content">
              <Text c="white" size="md">
                Попробовать бесплатно
              </Text>
            </Button>
            <Button radius="md" color="none" w="fit-content">
              <Text c="white" size="md">
                Начать с проектов →
              </Text>
            </Button>
          </Container>
        </Grid.Col>
        <Grid.Col span={{ base: 12,
          md: 5 }}>
          <Card radius="lg" bg='dark.4' h={300}>
            {/* https://mantine.dev/dates/calendar/  ??? */}
          </Card>
        </Grid.Col>
      </Grid>
    </Container>
  );
};
