import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './main.css'
import App from './App.vue'
import router from './router/router.js'

const app = createApp(App)

app
	.use(router)
	.use(createPinia())
	.mount('#app')