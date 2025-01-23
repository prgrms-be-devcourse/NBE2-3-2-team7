<script setup>
import AdminAside from '@/components/admin/AdminAside.vue';
import AdminHeader from '@/components/admin/AdminHeader.vue';

</script>

<template>
	<div class="flex h-screen">
		<AdminAside />
		<div class="flex flex-1 flex-col items-center">
			<div class="w-full">
				<AdminHeader />
				<div class="border-b border-[#2c817c] w-full"></div>
			</div>
			
			<RouterView />
		</div>
	</div>
</template>

<style scoped></style>
