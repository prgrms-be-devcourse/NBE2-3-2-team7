<script setup>
import AppHeader from '../components/AppHome/AppHeader.vue'
import AppFooter from '../components/AppHome/AppFooter.vue'
</script>

<template>
	<div class="flex flex-col min-h-screen">
		<AppHeader />
		<RouterView />
		<AppFooter />
	</div>
</template>

<style scoped></style>
