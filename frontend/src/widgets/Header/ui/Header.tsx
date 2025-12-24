import { Group, Button, Divider, ThemeIcon, Text, Anchor } from '@mantine/core'
import { Link } from '@inertiajs/react'
import { useDisclosure } from '@mantine/hooks'
import { ForgotPasswordModal } from '@features/auth'
import { useTranslation } from 'react-i18next'

const links = [
  {
    link: '#',
    label: 'Коммерческий опыт',
  },
  {
    link: '#',
    label: 'Аналитика',
  },
  {
    link: '#',
    label: 'Обучение',
  },
  {
    link: '#',
    label: 'Грейды',
  },
  {
    link: '#',
    label: 'База знаний',
  },
  {
    link: '#',
    label: 'Вебинары',
  },
  {
    link: '#',
    label: 'Кейсы',
  },
  {
    link: '#',
    label: 'Тарифы',
  },
  {
    link: '#',
    label: 'Новости',
  },
  {
    link: '#',
    label: 'Сообщество',
  },
]

export function Header(): JSX.Element {
  const { t } = useTranslation()
  const [opened, { open, close }] = useDisclosure(false)

  const items = links.map((link): JSX.Element => (
    <Anchor
      key={link.label}
      href={link.link}
      variant="transparent"
      underline="never"
      size="md"
      c="white"
      fw={500}
    >
      {link.label}
    </Anchor>
  ))

  return (
    <header>
      <Group py={10} justify="center" gap={70} bg="black">
        <Anchor
          key="Home"
          href="/" // В данный момент ссылка на главную страницу
          variant="transparent"
          underline="never"
          size="md"
          c="white"
          fw={500}
        >
          <Group gap="xs">
            <ThemeIcon variant="white" size="lg" radius="md">
              <Text c="black" size="md" fw={500}>
                X
              </Text>
            </ThemeIcon>
            <Text c="white" size="md" fw={700} lh={1.1}>
              Hexlet
              <br />
              Карьера
            </Text>
          </Group>
        </Anchor>

        <nav>
          <Group gap="xl">
            {items}
          </Group>
        </nav>

        {/* В данный момент переводит на авторизацию в dashboard */}
        <Button variant="default" size="md" component={Link} href="/en/users/sign_in">
          <Text size="md" lh={1.1}>
            Попробывать
            <br />
            бесплатно
          </Text>
        </Button>
      </Group>

      {/* Кнопка "Забыли пароль?" под основной кнопкой */}
      <div style={{
        position: 'absolute',
        left: '50%',
        transform: 'translateX(-50%)',
        width: '100%',
        textAlign: 'center',

      }}>
        <Button
          variant="subtle"
          size="xs"
          onClick={open}
          style={{ padding: 0, height: 'auto' }}
        >
          <Text size="xl" c="white">
            {t('auth.forgotPassword.forgotPassword')}
          </Text>
        </Button>
      </div>

      <Divider size={2} color="rgba(255, 255, 255, 0.1)" />

      {/* Модалка восстановления пароля */}
      <ForgotPasswordModal opened={opened} onClose={close} />
    </header>
  )
}