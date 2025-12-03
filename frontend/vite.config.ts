import { defineConfig } from 'vite'
import { resolve } from 'path'
import react from '@vitejs/plugin-react'

export default defineConfig({
  root: resolve(__dirname, "src"),
  base: "/",
  plugins: [react()],
  build: {
    outDir: resolve("static/dist"),
    assetsDir: "",
    manifest: "manifest.json",
    emptyOutDir: true,
    rollupOptions: {
      // Overwrite default .html entry to main.tsx in the static directory
      input: resolve("src/main.tsx"),
    },
  },
});