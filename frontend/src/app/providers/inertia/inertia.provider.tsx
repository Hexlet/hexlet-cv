import { createInertiaApp, Link } from '@inertiajs/react';
import { createRoot } from 'react-dom/client';
import { UIProvider } from '@ui/ui.provider';
import { StrictMode } from 'react';
import { I18nextProvider } from 'react-i18next';
import i18n from '../i18n/i18n.config';
import "@app/styles/index.css"

export const initInertia = () => {
  createInertiaApp({
    resolve: (name) => {
      const pages = import.meta.glob('../../../pages/**/*.tsx', { eager: true });
      const page = pages[`../../../pages/${name}.tsx`];
      if (!page) throw new Error(`Page not found: ${name}`);

      return page;
    },
    setup({ el, App, props }) {
      const root = createRoot(el);
      root.render(
        <StrictMode>
          <UIProvider>
            <I18nextProvider i18n={i18n}>
              <App { ...props } />
            </I18nextProvider>
          </UIProvider>
        </StrictMode>
      );
    },
  });
};