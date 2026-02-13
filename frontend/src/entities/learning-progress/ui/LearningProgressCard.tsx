import {
  ThemeIcon,
  Progress,
  Group,
  Stack,
  Text,
  Card,
  Badge,
  Flex,
  Avatar,
} from '@mantine/core'
import { useTranslation } from 'react-i18next'
import { useRelativeDate, getDaysDiff } from '@shared/lib'
import type { IProgress } from '../model'
import { getBadgeConfig } from '../model'

interface LearningProgressCardProps {
  program: IProgress
  actionButton: React.ReactNode
}

export const LearningProgressCard: React.FC<LearningProgressCardProps> = ({
  program,
  actionButton,
}) => {
  const { t } = useTranslation()
  const getLastActivityText = useRelativeDate()

  const lastActivityText = getLastActivityText(
    program.lastActivityAt,
    getDaysDiff,
  )

  const badgeConfig = getBadgeConfig(
    program.isCompleted,
    program.completedLessons,
    t,
  )

  return (
    <Card
      shadow="sm"
      padding="md"
      radius="md"
      withBorder
      flex={1}
      display="flex"
      style={{ flexDirection: 'column' }}
    >
      <Group wrap="nowrap" align="flex-start">
        <Avatar
          variant="outline"
          color="initials"
          name={program.programTitle}
          alt={program.programTitle}
        />

        <Stack gap={4} style={{ flex: 1, minWidth: 0 }}>
          <Flex justify="space-between" wrap="wrap" align="center">
            <Text
              fw="bold"
              size="lg"
              lineClamp={2}
              title={program.programTitle}
            >
              {program.programTitle}
            </Text>
            {badgeConfig && (
              <Badge variant="filled" size="xs" color={badgeConfig.color}>
                {badgeConfig.label}
              </Badge>
            )}
          </Flex>

          <Group gap={4}>
            <Text size="xs" fw={500} c={program.isCompleted ? 'green' : 'blue'}>
              {program.isCompleted
                ? t('accountPage.progress.status.done')
                : t('accountPage.progress.status.inProgress')}
            </Text>
            <Text size="xs" c="dimmed" span>
              ·
            </Text>
            <Text size="xs" c="dimmed" truncate>
              {lastActivityText}
            </Text>
          </Group>
        </Stack>
      </Group>

      {/* card footer */}
      <Stack mt="auto" pt="md">
        <Progress
          value={program.progressPercentage}
          mt="md"
          size="sm"
          radius="xl"
        />
        <Group justify="space-between" align="center">
          <Text size="sm" fw={500} c="dimmed">
            {program.completedLessons} / {program.totalLessons}
          </Text>
          {actionButton}
        </Group>
      </Stack>
    </Card>
  )
}
