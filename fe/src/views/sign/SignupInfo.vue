<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useSignupStore } from '@/store/signup';
import defaultProfile from '@/assets/images/default/default_profile.png';

const router = useRouter();

const signupStore = useSignupStore();

const upload = ref(null);
const selectedFile = ref(null);
const previewSrc = ref(defaultProfile);

const name = ref('');

const brand = ref('');
const brandTouched = ref(false);
const brandChecked = ref(false);
const brandExists = ref(false);

const tel = ref('');
const telTouched = ref(false);
const telChecked = ref(false);
const telExists = ref(false);

onMounted(() => {
	if (signupStore.role === null) {
		router.push('/signup/role');
	}
});

const uploadImage = () => {
	upload.value.click();
}

const handleFileChange = (event) => {
	const files = event.target.files;
	if (files && files.length > 0) {
		const file = files[0];

		// 파일 검증
		if (!file.type.match("image/.*")) {
			alert('이미지 파일만 업로드가 가능합니다.');
			return;
		}
		if (file.size > 1024 ** 2 * 2) {
			alert('이미지의 크기를 2MB 이하로 업로드 해주시기 바랍니다.');
			return;
		}

		selectedFile.value = file;

		// 미리보기 업데이트
		const reader = new FileReader();
		reader.onload = (e) => {
			previewSrc.value = e.target.result;
		};
		reader.readAsDataURL(file);
	}
};


const telError = computed(() => {
	const regex = /^\d{3}-\d{3,4}-\d{4}$/;
	return !regex.test(tel.value);
});

const canProceed = computed(() => !telError.value && !telExists.value && telChecked.value && !brandExists.value && brandChecked.value && name.value);

const resetBrandCheck = () => {
	brandExists.value = false;
	brandChecked.value = false;
};

const resetAndFormattedTel = (e) => {
	let inputValue = e.target.value;

	let filteredValue = inputValue.replace(/\D/g, '');

	if (filteredValue.length > 11) {
		filteredValue = filteredValue.slice(0, 11);
	}

	if (filteredValue.length <= 3) {
		filteredValue = filteredValue.replace(/(\d{1,3})/, '$1');
	} else if (inputValue.length <= 7) {
		filteredValue = filteredValue.replace(/(\d{3})(\d{1,4})/, '$1-$2');
	} else if (filteredValue.length <= 10) {
		filteredValue = filteredValue.replace(/(\d{3})(\d{3})(\d{1,4})/, '$1-$2-$3');
	} else {
		filteredValue = filteredValue.replace(/(\d{3})(\d{4})(\d{1,4})/, '$1-$2-$3');
	}
	
	tel.value = filteredValue;
	telExists.value = false;
	telChecked.value = false;
};

// 닉네임(상호명) 확인 버튼 클릭
const checkBrand = () => {
	if (brand.value.trim() === '중복된닉네임') {
		brandExists.value = true;
	} else {
		brandExists.value = false;
	}
	brandChecked.value = true;
};

// 전화번호 확인 버튼 클릭
const checkTel = () => {
	if (tel.value.trim() === '010-1234-5678') {
		telExists.value = true;
	} else {
		telExists.value = false;
	}
	telChecked.value = true;
};

const proceedToNextPage = () => {
	signupStore.setName(name.value);
	router.push('/signup/success');
};
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
					class="rounded-full border-2 border-[#3FB8AF] w-8 h-8 justify-center items-center flex text-[#3FB8AF] font-bold text-xl">
					<i class="fas fa-stream"></i>
				</div>
			</div>
		</section>
		<section class="flex flex-col items-center w-full mt-6">
			<h1 class="text-4xl font-bold text-gray-700">개인 정보 입력</h1>
			<div class="flex flex-col items-center mt-14">

				<div class="space-y-8">
					<div class="flex flex-col">
						<label for="profile" class="ms-2 text-gray-700 font-bold">프로필</label>
						<div
							class="flex flex-col justify-center items-center w-96 border-2 border-gray-300 p-2 mt-2 space-y-4 rounded-md">
							<img :src="previewSrc"
								class="rounded-full w-40 h-40 border-2 border-gray-300 object-contain bg-white" alt="">
							<input type="file" ref="upload" accept="image/gif, image/png, image/jpeg" class="hidden"
								@change="handleFileChange">
							<button @click="uploadImage"
								class="h-12 border p-2 bg-[#3FB8AF] hover:bg-[#2c817c] text-white font-bold transition-colors rounded-md"
								type="button">이미지 변경하기</button>
							<span class="text-xs font-bold">2MB 이하로 업로드 해주세요.</span>
						</div>
					</div>
					<div class="flex flex-col">
						<label for="name" class="ms-2 text-gray-700 font-bold">이름<span class="ms-2 text-left text-red-500 text-sm">*</span></label>
						<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
							<input type="text" id="name" v-model="name" required
								class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
								placeholder="이름 입력" />
						</div>
					</div>
					<div class="flex flex-col">
						<label for="brand" class="ms-2 text-gray-700 font-bold">
							{{ signupStore.isCustomer ? "닉네임" : "상호명" }}<span class="ms-2 text-left text-red-500 text-sm">*</span>
						</label>
						<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
							<input type="text" id="brand" @blur="brandTouched = true" @input="resetBrandCheck"
								v-model="brand" required
								class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
								:placeholder="signupStore.isCustomer ? '닉네임 입력' : '상호명 입력'" />
							<button @click="checkBrand" :disabled="!brand"
								class="h-12 border p-2 bg-[#3FB8AF] hover:bg-[#2c817c] disabled:bg-gray-300 text-white font-bold transition-colors rounded-md">확인</button>
						</div>
						<span v-if="brandChecked &&  brandExists"
							class="ms-2 text-left text-red-500 text-sm">
							이미 사용 중인 {{ signupStore.isCustomer ? "닉네임" : "상호명" }}입니다.
						</span>
						<span v-if="brandChecked && !brandExists"
							class="ms-2 text-left text-blue-500 text-sm">
							사용이 가능한 {{ signupStore.isCustomer ? "닉네임" : "상호명" }}입니다.
						</span>
					</div>
					<div class="flex flex-col">
						<label for="tel" class="ms-2 text-gray-700 font-bold">전화번호<span class="ms-2 text-left text-red-500 text-sm">*</span></label>
						<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
							<input type="text" id="tel" @blur="telTouched = true" @input="resetAndFormattedTel"
								v-model="tel" required maxlength="13"
								class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
								placeholder="전화번호 입력" />
							<button @click="checkTel" :disabled="telError || !tel"
								class="h-12 border p-2 bg-[#3FB8AF] hover:bg-[#2c817c] disabled:bg-gray-300 text-white font-bold transition-colors rounded-md">확인</button>
						</div>
						<span v-if="telExists && telChecked && !telError" class="ms-2 text-left text-red-500 text-sm">
							이미 사용 중인 전화번호입니다.
						</span>
						<span v-if="!telExists && telChecked && !telError" class="ms-2 text-left text-blue-500 text-sm">
							사용이 가능한 전화번호입니다.
						</span>
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
