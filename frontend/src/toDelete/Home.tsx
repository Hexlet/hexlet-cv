<<<<<<< HEAD:frontend/src/Pages/Home.tsx
import { Link } from '@inertiajs/react'
import { Container, Button, Group, Title, Text } from '@mantine/core'
=======
import { Link } from '@inertiajs/react';
import { Container, Button, Group, Title } from '@mantine/core';
import { useTranslation } from 'react-i18next';
>>>>>>> upstream/main:frontend/src/toDelete/Home.tsx

export default function Home() {
  const { t } = useTranslation();

  return (
    <Container size="sm" mt="xl" ta="center">
      <Title order={2} mb="xl">
        {t('homePage.greetings')}
      </Title>

      <Group justify="center">
        <Button component={Link} href="/en/users/sign_in">
          Вход
        </Button>
        <Button component={Link} href="/en/users/sign_up" variant="outline">
          Регистрация
        </Button>
      </Group>
         <Text mt="xl" size="sm" c="dimmed">
      </Text>
    </Container>
  );
}
