import React from 'react'
import { createInertiaApp } from '@inertiajs/react'
import { createRoot } from 'react-dom/client'
import { MantineProvider } from '@mantine/core'
import '@mantine/core/styles.css'

createInertiaApp({
  resolve: (name) => {
    console.log("Inertia ищет компонент:", name);
    const pages = import.meta.glob('./Pages/**/*.tsx', { eager: true })
    const pagePath = `./Pages/${name}.tsx`;
    return pages[pagePath];
  },
  setup({ el, App, props }) {
    createRoot(el).render(
      <React.StrictMode>
        <MantineProvider>
          <App {...props} />
        </MantineProvider>
      </React.StrictMode>
    )
  },
})