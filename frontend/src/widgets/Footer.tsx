import { Group, Anchor, Divider, ThemeIcon, Text } from '@mantine/core'

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

export default function Footer(): JSX.Element {
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
    <footer>
      <Divider size={2} color="rgba(255, 255, 255, 0.1)" />
      <Group py={10} justify="space-around" gap="xs" bg="black">
        <Group gap="xs">
          <ThemeIcon variant="white" size="lg" radius="md">
            <Text color="black" size="md" fw={500}>
              X
            </Text>
          </ThemeIcon>
          <Text color="white" size="md">
            © 2025 Hexlet Карьера
          </Text>
        </Group>
        <Group gap="xl">
          {items}
        </Group>
      </Group>
    </footer>
  )
}
