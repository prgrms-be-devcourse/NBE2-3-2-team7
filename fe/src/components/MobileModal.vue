<script setup>
import { defineEmits } from 'vue';

const emit = defineEmits(['close']);
</script>

<template>
	<div id="mobile-modal" role="dialog" aria-modal="true">
		<div @click="emit('close')" class="fixed inset-0 z-20"></div>
		<div
			class="fixed inset-y-0 right-0 z-20 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10">
			<div class="flex items-center justify-between">
				<!-- 기존 내용 유지 -->
				<router-link to="/" class="-m-1.5 p-1.5">
					<img class="h-16 w-auto" src="../assets/logo.png" alt="">
				</router-link>
				<button @click="emit('close')" type="button" class="-m-2.5 rounded-md p-2.5 text-gray-700">
					<svg class="size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
						aria-hidden="true" data-slot="icon">
						<path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
					</svg>
				</button>
			</div>
			<div class="mt-6 flow-root">
				<div class="-my-6 divide-y divide-gray-500/10">
					<div class="space-y-2 py-6">
						<router-link to="/land"
							class="-mx-3 block rounded-lg px-3 py-2 text-base/7 font-semibold text-gray-900 hover:bg-gray-200 transition-colors hover:text-[#3FB8AF]">임대지</router-link>
						<router-link to="/popup"
							class="-mx-3 block rounded-lg px-3 py-2 text-base/7 font-semibold text-gray-900 hover:bg-gray-200 transition-colors hover:text-[#3FB8AF]">팝업</router-link>
						<router-link to="/user" id="mobile-mypage-button" style="display: none"
							class="-mx-3 block rounded-lg px-3 py-2 text-base/7 font-semibold text-gray-900 hover:bg-gray-200 transition-colors hover:text-[#3FB8AF]">회원</router-link>
					</div>
					<div class="py-6">
						<a href="/login" id="mobile-login-button"
							class="-mx-3 block rounded-lg px-3 py-2.5 text-base/7 font-semibold text-gray-900 hover:bg-gray-200 transition-colors hover:text-[#3FB8AF]">
							Log in <span aria-hidden="true">&rarr;</span>
						</a>
						<button id="mobile-logout-button" onclick="handleLogout()" style="display: none"
							class="-mx-3 block rounded-lg px-3 py-2.5 text-base/7 font-semibold text-gray-900 hover:bg-gray-200 transition-colors hover:text-[#3FB8AF]">
							Log out <span aria-hidden="true">&rarr;</span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style scoped></style>
