import { Text } from '@mantine/core'
import type { TextProps } from '@mantine/core'

type DsTextTone = 'primary' | 'secondary'

type TProps = TextProps & { children: React.ReactNode; tone?: DsTextTone }

const TONE_COLORS = {
  secondary: 'var(--ds-text-secondary)',
} as const

export const DsText: React.FC<TProps> = (props) => {
  const { tone = 'primary', ...rest } = props
  const color = tone === 'secondary' ? TONE_COLORS.secondary : undefined

  return <Text {...rest} c={color} />
}