<script setup>
import { RouterLink } from 'vue-router';

defineProps({
	link: String,
	image: String,
	title: String,
	type: String,
	location: String,
});
</script>

<template>
	<div class="snap-center shrink-0 first:pl-8 last:pr-8">
		<router-link :to="link"
			class="rounded-md flex flex-col p-4 shadow-xl bg-white border hover:transform hover:translate-y-[-10px] transition-transform duration-200">
			<img class="shrink-0 w-80 h-40 rounded-lg shadow-xl bg-white" :src="image" :alt="title">
			<span class="my-4 font-bold">{{ title }}</span>
			<div class="flex justify-between">
				<p>{{ type }}</p>
				<p>{{ location }}</p>
			</div>
		</router-link>
	</div>
</template>

<style scoped></style>
