/*
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
	plugins: [vue()],
	server: {
		proxy: {
			'/api': 'http://localhost:8080'
		}
	}
})
 */

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url' // URL 모듈 추가

// https://vite.dev/config/
export default defineConfig({
	plugins: [vue()],
	resolve: {
		alias: {
			'@': fileURLToPath(new URL('./src', import.meta.url)), // '@'를 'src'로 설정
		},
	},
	server: {
		proxy: {
			'/api': 'http://localhost:8080',
		},
	},
})

