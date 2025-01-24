<script setup>
import { onMounted } from 'vue';
import { useRouter, RouterLink } from 'vue-router';
import { useSignupStore } from '@/store/signup';

const router = useRouter();

const signupStore = useSignupStore();

onMounted(() => {
	if (signupStore.name === null) {
		router.push('/');
	}
});
</script>

<template>
	<main class="flex-grow flex flex-col self-center w-full max-w-4xl">
		<section class="flex flex-col items-center border-b border-gray-300 w-full">
			<div class="flex items-center space-x-16 p-6">
				<div
					class="rounded-full border-2 border-gray-300 bg-[#3FB8AF] w-8 h-8 justify-center items-center flex text-white">
					<i class="fas fa-check"></i>
				</div>
				<div
					class="rounded-full border-2 border-gray-300 bg-[#3FB8AF] w-8 h-8 justify-center items-center flex text-white">
					<i class="fas fa-check"></i>
				</div>
				<div
					class="rounded-full border-2 border-gray-300 bg-[#3FB8AF] w-8 h-8 justify-center items-center flex text-white">
					<i class="fas fa-check"></i>
				</div>
			</div>
		</section>
		<section class="flex flex-col items-center w-full mt-6">
			<h1 class="text-4xl font-bold text-gray-700">회원 가입 완료</h1>
			<div class="flex flex-col items-center mt-14">
				<div class="text-[#3FB8AF] text-5xl mb-6">
					<i class="fas fa-user-check"></i>
				</div>
				<div class="text-center font-bold text-2xl">
					<h3>{{ signupStore.name }}님의 회원가입을 축하합니다.</h3>
					<h3>다양한 서비스를 이용해보세요!</h3>
				</div>
				<div class="flex flex-col space-y-4">
					<router-link to="/"
						class="mt-12 w-80 h-10 bg-white text-[#3FB8AF] border-2 border-[#3FB8AF] hover:bg-gray-200 rounded-lg flex justify-center items-center font-bold transition-colors">
						홈
					</router-link>
					<router-link to="/signin"
						class="mt-12 w-80 h-10 bg-[#3FB8AF] text-white border-2 border-[#3FB8AF] hover:bg-[#2c817c] rounded-lg flex justify-center items-center font-bold transition-colors">
						로그인
					</router-link>
				</div>
			</div>
		</section>
	</main>
</template>

<style scoped></style>
