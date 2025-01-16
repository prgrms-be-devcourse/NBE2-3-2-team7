<script setup>
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
</script>

<template>
	<div class="flex flex-col min-h-screen">
		<AppHeader />
		<RouterView />
		<AppFooter />
	</div>
</template>
