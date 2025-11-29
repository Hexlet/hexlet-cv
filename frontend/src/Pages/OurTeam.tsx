import {
  Container,
  Title,
  Text,
  SimpleGrid,
  Group,
  Stack,
  Badge,
} from '@mantine/core'
import UserBadge from './components/UserBadge'

const OurTeam = () => {
  return (
    <Container size="lg" py="lg">
      <Group justify="center" mb="xs">
        <Stack gap="xs" align="center">
          <Badge color='teal.9' mb={0} size='lg'>
            <Text size='xs' c='lime' fw='bold'>
                О нас
            </Text>
          </Badge>
          <Title order={1} fw={900} ta="center" c='white'>
            Наша команда
          </Title>
        </Stack>
      </Group>

      <SimpleGrid
        cols={{ base: 1, sm: 2, md: 3, lg: 5 }}
        spacing="lg"
        mb='xl'
      >     
        <UserBadge name="Максим" role="Основатель сервиса" />
        <UserBadge name="Альберт" role="Администратор" />
        <UserBadge name="Таня" role="HR-менеджер" />
        <UserBadge name="Слава" role="Карьерный консультант" />
        <UserBadge name="Лера" role="Карьерный консультант" />
      </SimpleGrid>

    </Container>
  );
}

export default OurTeam;