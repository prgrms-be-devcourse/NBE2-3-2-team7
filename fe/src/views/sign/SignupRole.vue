<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import TermsForm from '@/components/policy/TermsForm.vue';
import PrivacyForm from '@/components/policy/PrivacyForm.vue';
import { useSignupStore } from '@/store/signup';

const router = useRouter();

const signupStore = useSignupStore();

onMounted(() => {
	signupStore.useSocial();
	// signupStore.useEmail();
});

const isTermsAccepted = ref(false);
const isPrivacyAccepted = ref(false);
const isModalOpen = ref(false);
const modalType = ref(''); // terms, privacy


const canProceed = computed(() => {
	return signupStore.role && isTermsAccepted.value && isPrivacyAccepted.value;
});

const openModal = (type) => {
	modalType.value = type;
	isModalOpen.value = true;
};

const closeModal = () => {
	isModalOpen.value = false;
};

const proceedToNextPage = () => {
	if (signupStore.role === 'customer' && signupStore.social) {
		// 소셜 로그인을 시도해서 새롭게 가입하는는 일반 사용자일 경우
		router.push('/signup/info');
	} else {
		// 그 이외의 경우
		router.push('/signup/account');
	}
};
</script>

<template>
	<main class="flex-grow flex flex-col self-center w-full max-w-4xl">
		<section class="flex flex-col items-center border-b border-gray-300 w-full">
			<div class="flex items-center space-x-16 p-6">
				<div
					class="rounded-full border-2 border-[#3FB8AF] w-8 h-8 justify-center items-center flex text-[#3FB8AF] font-bold text-xl">
					<i class="fas fa-stream"></i>
				</div>
				<div class="rounded-full border-2 border-gray-300 w-8 h-8 justify-center items-center flex text-white">
				</div>
				<div class="rounded-full border-2 border-gray-300 w-8 h-8 justify-center items-center flex text-white">
				</div>
			</div>
		</section>
		<section class="flex flex-col items-center w-full mt-6">
			<h1 class="text-4xl font-bold text-gray-700">회원 구분 선택</h1>
			<div class="flex flex-col items-center mt-14">
				<div class="flex space-x-16 mb-12">
					<div>
						<button :class="[
							'p-6 w-80 h-44 text-center border-2 transition-colors hover:border-[#3FB8AF] border-gray-300 rounded-lg',
							signupStore.isCustomer ? 'bg-[#3FB8AF] text-white' : 'bg-white text-gray-700',
						]" @click="signupStore.setCustomer">
							<span class="text-3xl font-bold mb-4">일반 사용자</span>
						</button>
						<p class="text-gray-700 text-center font-bold">땅 임대와 팝업 등록이 가능합니다.</p>
					</div>
					<div>
						<button :class="[
							'p-6 w-80 h-44 text-center border-2 transition-colors hover:border-[#3FB8AF] border-gray-300 rounded-lg',
							signupStore.isLandlord ? 'bg-[#3FB8AF] text-white' : 'bg-white text-gray-700',
						]" @click="signupStore.setLandlord">
							<span class="text-3xl font-bold mb-4">임대인</span>
						</button>
						<p class="text-gray-700 text-center font-bold">땅과 정산 내역의 관리가 가능합니다.</p>
					</div>
				</div>
				<div class="flex flex-col space-y-4">
					<div
						:class="['flex w-80 border p-2 transition-colors justify-between', isTermsAccepted ? 'border-[#3FB8AF]' : 'border-gray-300']">
						<label for="terms" class="flex items-center gap-2 cursor-pointer flex-1">
							<input type="checkbox" id="terms" class="hidden" v-model="isTermsAccepted" />
							<div :class="['rounded-full border transition-colors border-gray-300 w-6 h-6 justify-center items-center flex font-bold text-xl',
								isTermsAccepted ? 'bg-[#3FB8AF] ' : 'bg-white'
							]">
								<i class="fas fa-check text-sm text-white"></i>
							</div>
							<span class="select-none">이용약관</span>
						</label>
						<span class="text-gray-500 hover:text-[#3FB8AF] text-sm select-none cursor-pointer"
							@click="openModal('terms')">[자세히 보기]</span>
					</div>
					<div
						:class="['flex w-80 border p-2 transition-colors justify-between', isPrivacyAccepted ? 'border-[#3FB8AF]' : 'border-gray-300']">
						<label for="privacy" class="flex items-center gap-2 cursor-pointer flex-1">
							<input type="checkbox" id="privacy" class="hidden" v-model="isPrivacyAccepted" />
							<div :class="['rounded-full border transition-colors border-gray-300 w-6 h-6 justify-center items-center flex font-bold text-xl',
								isPrivacyAccepted ? 'bg-[#3FB8AF] ' : 'bg-white'
							]">
								<i class="fas fa-check text-sm text-white"></i>
							</div>
							<span class="select-none">개인정보 보호정책</span>
						</label>
						<span class="text-gray-500 hover:text-[#3FB8AF] text-sm select-none cursor-pointer"
							@click="openModal('privacy')">[자세히 보기]</span>
					</div>
				</div>
				<!-- 모달 -->
				<div v-if="isModalOpen" class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center"
					@click.self="closeModal">
					<!-- 모달 컨텐츠 -->
					<div class="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full">
						<!-- 모달 헤더 -->
						<h2 class="text-xl font-bold mb-4">
							{{ modalType === 'terms' ? '팝업마켓 이용약관' : '팝업마켓 개인정보 보호정책' }}
						</h2>
						<p
							class="flex-1 overflow-y-auto height_scrollbar h-64 text-gray-700 mb-4 border-y-2 border-gray-300 text-sm leading-relaxed">
							<!-- 약관 내용 -->
							<span v-if="modalType === 'terms'">
								<TermsForm />

							</span>
							<span v-else>
								<PrivacyForm />
							</span>
						</p>
						<!-- 닫기 버튼 -->
						<button
							class="bg-[#3FB8AF] text-white px-4 py-2 rounded-lg hover:bg-[#2c817c] transition-colors"
							@click="closeModal">
							닫기
						</button>
					</div>
				</div>

				<div>
					<button
						class="mt-12 w-80 h-10 bg-[#3FB8AF] text-white rounded-lg disabled:bg-gray-300 disabled:cursor-not-allowed"
						:disabled="!canProceed" @click="proceedToNextPage">
						다음으로
					</button>
				</div>
			</div>
		</section>
	</main>
</template>

<style scoped></style>
