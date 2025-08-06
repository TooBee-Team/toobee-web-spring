import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/user': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/login': {
        target: 'http://localhost:8080',
        changeOrigin: true,
<<<<<<< HEAD
      },
      '/project': {
        target: 'http://localhost:8080',
        changeOrigin: true,
=======
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
      }
    }
  }
})
