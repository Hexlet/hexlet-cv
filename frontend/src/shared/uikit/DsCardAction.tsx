import { Button, Group } from '@mantine/core'

type TProps = { 
  label: string
  onClick?: () => void
}

export const DsCardAction: React.FC<TProps> = (props) => {
  const { label, onClick } = props

  return (
    <Group justify="flex-end" mt="md">
      <Button
        fullWidth
        size="md"
        variant="white"
        radius="lg"
        onClick={onClick}
        styles={{
          root: {
            backgroundColor: 'white',
            color: 'var(--mantine-color-dark-7)',
            fontWeight: 400,
            '&:hover': {
              backgroundColor: 'var(--mantine-color-gray-1)',
              color: 'var(--mantine-color-dark-7)',
            }
          }
        }}
      >
        {label}
      </Button>
    </Group>
  )
}