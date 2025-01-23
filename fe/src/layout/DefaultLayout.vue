<script setup>
import AppHeader from '@/components/app/AppHeader.vue'
import AppFooter from '@/components/app/AppFooter.vue'
</script>

<template>
	<div class="flex flex-col min-h-screen">
		<AppHeader />
		<RouterView />
		<AppFooter />
	</div>
</template>

<style scoped></style>
