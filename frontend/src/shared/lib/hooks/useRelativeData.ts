import { useTranslation } from 'react-i18next'

type GetLastActivityFn = (
  date: string,
  daysCounter: (date: string) => number,
) => string

export const useRelativeDate = (): GetLastActivityFn => {
  const { t } = useTranslation()

  const getLastActivityText: GetLastActivityFn = (date, daysCounter) => {
    const days = daysCounter(date)

    if (days === 0) return t('accountPage.progress.dates.today')
    if (days === 1) return t('accountPage.progress.dates.yesterday')

    return t('accountPage.progress.dates.days_ago', { count: days })
  }

  return getLastActivityText
}
