<script setup>
import { RouterLink } from 'vue-router';
</script>

<template>
	<main class="flex-grow flex flex-col items-center">
		<section class="flex mt-10">
			<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
				<div class="flex flex-col items-center justify-center">
					<h1 class="text-4xl font-bold text-center text-gray-900">결제 실패</h1>
					<p class="text-lg text-center text-gray-600 mt-4">결제에 실패하였습니다. 다시 시도해주세요.</p>
					<router-link to="/"
						class="mt-6 px-6 py-3 bg-[#3FB8AF] text-white text-lg font-semibold rounded-lg hover:bg-[#3FB8AF]">
						홈으로 돌아가기
					</router-link>
				</div>
			</div>
		</section>
	</main>
</template>

<style scoped></style>
