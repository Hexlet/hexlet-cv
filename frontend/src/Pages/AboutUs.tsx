import {
  Container,
  Title,
  Text,
  SimpleGrid,
  Card,
  Group,
  Stack,
  Badge,
} from '@mantine/core'
import { motion } from 'framer-motion'
import UserBadge from './components/UserBadge'

const AboutUs = () => {
  return (
    <Container size="lg" py="xs">
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

      <motion.div
        whileHover={{ 
          scale: 1.02,
          transition: { duration: 0.2 }
        }}
      >
        <Card
          radius="lg"
          p="xl"
          bg='dark.6'
        >
          <Group justify="center">
            <Stack gap="md" align="center">
              <Text size='xl' c='dimmed'>
                ИИ-помощник
              </Text>  
              <Title order={3} ta="center" c='white'>
                Тота ИИ — агент-ускоритель карьеры
              </Title>
              <Text ta="center" size="lg" c='dimmed'>
                Помогает с резюме, откликами, перепиской с рекрутерами 
                и подготовкой к собеседованиям.
              </Text>
            </Stack>
          </Group>
        </Card>
      </motion.div>
    </Container>
  );
}

export default AboutUs;