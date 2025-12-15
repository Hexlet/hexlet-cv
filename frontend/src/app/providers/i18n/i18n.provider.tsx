import i18next from 'i18next'
import { I18nextProvider } from 'react-i18next'
import resources from './locales/index'

const I18nProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const i18nextInstance = i18next.createInstance()
  i18nextInstance.init({
    debug: false,
    lng: 'ru',
    resources,
    interpolation: {
      escapeValue: false,
    },
  })

  return (
    <I18nextProvider i18n={i18nextInstance}>
      { children }
    </I18nextProvider>
  )
}

export default I18nProvider
