import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '^/[a-z]{2}(/|$)': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
      '/api': 'http://localhost:8080',
    },
  },
})