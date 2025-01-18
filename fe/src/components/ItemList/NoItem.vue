<script setup>

defineProps({
	message: String,
});

</script>

<template>
	<div class="col-span-1 sm:col-span-2 lg:col-span-3 flex justify-center items-center">
		<div class="flex items-center gap-2 text-gray-700 font-bold">
			{{ message }}
		</div>
	</div>
</template>

<style scoped></style>
