import { SimpleGrid, Container, Group, Pagination } from '@mantine/core'
import { IconBook, IconShoppingCart } from '@tabler/icons-react'
import { useMemo } from 'react'
import { router } from '@inertiajs/react'
import { useTranslation } from 'react-i18next'
import { EmptyPlaceholder, PageHeader } from '@shared/ui'
import { OpenProgramButton } from '@features/open-lerning-program'
import type { IProgressResponse } from '@widgets/progress-list/types'
import type { TProgress } from '@entities/learning-progress'

interface Props extends IProgressResponse {
  renderItem: (program: TProgress) => React.ReactNode
}

export const ProgressList: React.FC<Props> = ({
  progress,
  pagination,
  renderItem,
}) => {
  const { t } = useTranslation()

  const sortedPrograms = useMemo(() => {
    return [...progress].sort((a, b) => {
      return (
        new Date(b.lastActivityAt).getTime() -
        new Date(a.lastActivityAt).getTime()
      )
    })
  }, [progress])

  const activeProgramId = sortedPrograms[0]?.id

  const handlePageChange = (page: number) => {
    router.get(
      '/account/my-progress',
      { page: page - 1 },
      { preserveScroll: true },
    )
  }

  if (!progress.length)
    return (
      <EmptyPlaceholder
        title={t('emptyPlaceholders.noPurchasesTitle')}
        icon={IconShoppingCart}
        buttonLink="https://hexlet.io/courses"
        buttonLabel={t('buttonsLabels.goToCatalog')}
      />
    )

  return (
    <Container fluid>
      <PageHeader
        icon={<IconBook />}
        title={t('accountPage.progress.title')}
        actionButton={
          !!activeProgramId && (
            <OpenProgramButton variant="outline" programId={activeProgramId}>
              {t('buttonsLabels.continue')}
            </OpenProgramButton>
          )
        }
      />

      <SimpleGrid cols={{ base: 1, lg: 3 }} spacing="md">
        {sortedPrograms.map((program) => renderItem(program))}
      </SimpleGrid>

      {pagination.totalPages > 1 && (
        <Group justify="center" mt="xl" pb="xl">
          <Pagination
            total={pagination.totalPages}
            value={pagination.currentPage + 1}
            onChange={handlePageChange}
          />
        </Group>
      )}
    </Container>
  )
}
