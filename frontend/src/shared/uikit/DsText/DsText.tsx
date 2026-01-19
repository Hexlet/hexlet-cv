import { Text } from '@mantine/core'
import type { TextProps } from '@mantine/core'

type DsTextTone = 'primary' | 'secondary' | 'muted'

type TProps = TextProps & { children: React.ReactNode; tone?: DsTextTone }

const TONE_COLORS = {
  primary: undefined,
  secondary: 'var(--ds-text-secondary)',
  muted: 'var(--mantine-color-gray-6)',
} as const

export const DsText: React.FC<TProps> = (props) => {
  const { tone = 'primary', ...rest } = props
  const color = TONE_COLORS[tone]

  return <Text {...rest} c={color} />
}