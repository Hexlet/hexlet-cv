import { Link } from '@inertiajs/react'
import { Container, Button, Group, Title } from '@mantine/core'
import PerformanceReview from './PerformanceReview'

export default function Home() {
  return (
    <>
      <Container size="sm" mt="xl" ta="center">
        <Title order={2} mb="xl">
          Добро пожаловать!
        </Title>

        <Group justify="center">
          <Button component={Link} href="/en/users/sign_in">
            Вход
          </Button>
          <Button component={Link} href="/en/users/sign_up" variant="outline">
            Регистрация
          </Button>
        </Group>
      </Container>
      <PerformanceReview/>
    </>  
  )
}
