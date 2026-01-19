import { Card, Stack, type CardProps } from '@mantine/core'
import { DsCardContent } from '../DsCardContent'
import { DsCardAction } from '../DsCardAction'

type DsCardProps = CardProps & { 
  children: React.ReactNode
  actionLabel?: string
  onActionClick?: () => void
}

type DsCardFC = React.FC<DsCardProps> & {
  Action: typeof DsCardAction
  Content: typeof DsCardContent
}

export const DsCard: DsCardFC = (props) => {
  const { children, actionLabel, onActionClick, ...rest } = props

  return (
    <Card
      {...rest}
      radius="lg"
      padding="xl"
      withBorder
      style={{
        position: 'relative',
        transition: 'transform 0.2s ease',
      }}
    >
      <Stack gap="md" h="100%" justify="space-between">
        {children}
        {actionLabel && (
          <DsCardAction 
            label={actionLabel} 
            onClick={onActionClick}
          />
        )}
      </Stack>
    </Card>
  )
}

DsCard.Action = DsCardAction
DsCard.Content = DsCardContent
