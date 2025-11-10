import { Group, Button, Divider, ThemeIcon, Text } from '@mantine/core'

const links = [
  { link: '#', label: 'Коммерческий опыт' },
  { link: '#', label: 'Аналитика' },
  { link: '#', label: 'Обучение' },
  { link: '#', label: 'Грейды' },
  { link: '#', label: 'База знаний' },
  { link: '#', label: 'Вебинары' },
  { link: '#', label: 'Кейсы' },
  { link: '#', label: 'Тарифы' },
  { link: '#', label: 'Новости' },
  { link: '#', label: 'Сообщество' },
]

export default function Header() {

  const items = links.map((link): JSX.Element => (
    <Button
      key={link.label}
      onClick={(event) => event.preventDefault()}
      variant="transparent"
      size='md'
      color="white"
    >
      {link.label}
    </Button>
  ))

  return (
    <header>
      <Group py={10} justify="center" gap={70} bg='black'>
        <Group gap="xs">
          <ThemeIcon variant="white" size="lg" radius="md">
            <Text color='black' size='md' fw={500}>
              X
            </Text>
          </ThemeIcon>
          <Text color='white' size='md' fw={700} lh={1.1}>
            Hexlet <br /> Карьера
          </Text>
        </Group>
        <Group gap="xs">
          {items}
        </Group>
        <Button variant="default" size='md'>
          <Text size='md' lh={1.1}>
            Попробывать <br /> бесплатно
          </Text>
        </Button>
      </Group>
      <Divider size={2} color="rgba(255, 255, 255, 0.1)" />
    </header>
  )
}