<script setup>
import MobileModal from './MobileModal.vue';
import { RouterLink } from 'vue-router';
import { ref } from 'vue';

const modal = ref(false);

const handleModal = () => {
	modal.value = !modal.value;
}

</script>

<template>
	<header class="bg-white border-b-2">
		<nav class="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8" aria-label="Global">
			<!-- 기존 로고 부분은 유지 -->
			<div class="flex lg:flex-1">
				<router-link to="/" class="-m-1.5 p-1.5">
					<img class="h-16 w-auto" src="../assets/logo.png" alt="">
				</router-link>
			</div>

			<div class="flex lg:hidden">
				<button @click="handleModal" type="button"
					class="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700">
					<svg class="size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
						aria-hidden="true" data-slot="icon">
						<path stroke-linecap="round" stroke-linejoin="round"
							d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
					</svg>
				</button>
			</div>

			<!-- 메인 네비게이션 링크 -->
			<div class="hidden lg:flex lg:gap-x-12">
				<router-link to="/land"
					class="text-xl/6 font-semibold p-4 rounded-lg hover:bg-gray-200 transition-colors hover:text-[#3FB8AF] text-gray-900">임대지</router-link>
				<router-link to="/popup"
					class="text-xl/6 font-semibold p-4 rounded-lg hover:bg-gray-200 transition-colors hover:text-[#3FB8AF] text-gray-900">팝업</router-link>
				<router-link to="/user" style="display: none"
					class="text-xl/6 font-semibold p-4 rounded-lg hover:bg-gray-200 transition-colors hover:text-[#3FB8AF] text-gray-900">회원</router-link>
			</div>

			<!-- 로그인/로그아웃 버튼 수정 -->
			<div class="hidden lg:flex lg:flex-1 lg:justify-end">
				<router-link to="/login" id="login-button"
					class="text-xl/6 font-semibold p-4 rounded-lg hover:bg-slate-200 transition-colors hover:text-[#3FB8AF] text-gray-900">
					Log in <span aria-hidden="true">&rarr;</span>
				</router-link>
				<button id="logout-button" style="display: none"
					class="text-xl/6 font-semibold p-4 rounded-lg hover:bg-slate-200 transition-colors hover:text-[#3FB8AF] text-gray-900">
					Log out <span aria-hidden="true">&rarr;</span>
				</button>
			</div>
		</nav>
		<MobileModal v-if="modal" @close="handleModal"/>
	</header>
</template>

<style scoped></style>
