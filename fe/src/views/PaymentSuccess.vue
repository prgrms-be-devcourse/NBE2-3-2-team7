<script setup>
import { RouterLink } from 'vue-router';
</script>

<template>
	<main class="flex-grow flex flex-col items-center">
		<section class="flex mt-10">
			<div class="flex w-full flex-col">
				<div class="flex justify-center">
					<svg class="w-20 h-20 text-[#3FB8AF]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
						<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
					</svg>
				</div>
				<div class="flex justify-center mt-4">
					<h1 class="text-2xl font-semibold">결제가 완료되었습니다.</h1>
				</div>
				<div class="flex justify-center mt-4">
					<p class="text-gray-600">주문해 주셔서 감사합니다.</p>
				</div>
				<div class="flex justify-center mt-4">
					<router-link to="/user/payment" class="text-[#3FB8AF] hover:underline">주문 상세 내역 확인하기</router-link>
				</div>
			</div>
		</section>
	</main>
</template>

<style scoped></style>
