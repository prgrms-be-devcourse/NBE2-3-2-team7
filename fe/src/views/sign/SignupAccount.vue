<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { initSingleFlatpickr } from '../../utils/init.plugin';
import { useSignupStore } from '../../store/signup';
import { validateBusinessman } from '../../services/businessman.api'

const router = useRouter();

const signupStore = useSignupStore();

const businessName = ref("");
const businessId = ref("");
const businessDate = ref("");
const businessError = ref(false); // 사업자 등록 번호 유효성 오류
const businessExists = ref(false); // 사업자 등록 번호 중복 여부
const businessChecked = ref(false); // 확인 버튼 클릭 여부

const email = ref("");
const emailTouched = ref(false); // 이메일 입력 여부
const emailChecked = ref(false); // 확인 버튼을 클릭 여부
const emailExists = ref(false); // 이메일 중복 여부

const password = ref("");
const isPasswordVisible = ref(false); // 화면 표시 여부
const passwordTouched = ref(false); // 비밀번호 입력 여부

const passwordOk = ref("");
const isPasswordOkVisible = ref(false); // 화면 표시 여부
const passwordOkTouched = ref(false); // 비밀번호 확인 입력 여부

onMounted(() => {
	initSingleFlatpickr();
	if (signupStore.role === null) {
		router.push('/signup/role');
	}
});

// 사업자 정보 변경 시 확인 상태 초기화
const resetBusinessCheck = () => {
	businessChecked.value = false; // 확인 초기화
	businessExists.value = false; // 중복 상태 초기화
};

const handleBusinessIdInput = (event) => {
	const inputValue = event.target.value;
	const filteredValue = inputValue.replace(/\D/g, "");

	businessId.value = filteredValue;
	event.target.value = filteredValue;
	resetBusinessCheck();
};

// 사업자 등록 번호 확인 (API 요청)
const checkBusiness = async () => {
	try {
		// 1단계: 사업자 등록 번호 유효성 검사 (API 요청)
		const isValid = await validateBusinessId(); // 예제: 유효성 검사
		if (!isValid) {
			businessError.value = true;
			businessChecked.value = true;
			return;
		}

		// 2단계: 서비스 내 중복 여부 확인 (API 요청)
		const isExisting = await checkBusinessIdExists(businessId.value); // 예제: 중복 검사
		businessExists.value = isExisting;
		businessError.value = false;
		businessChecked.value = true;
	} catch (error) {
		console.error("API 요청 실패:", error);
		businessError.value = true;
	}
};

// Mock: 사업자 등록 번호 유효성 검사 API
const validateBusinessId = async () => {
	const request = [
		{
			"b_no": businessId.value,
			"start_dt": businessDate.value.replaceAll('-', ''),
			"p_nm": businessName.value,
		}
	];

	const response = await validateBusinessman(request)


	return response.data[0].valid === '01';
};

// 서비스 중복 여부 확인 API
const checkBusinessIdExists = async (id) => {
	// TODO -> API로 대체
	const existingIds = ["1234567890"];
	return new Promise((resolve) => setTimeout(() => resolve(existingIds.includes(id)), 100));
};

const togglePasswordVisibility = () => {
	isPasswordVisible.value = !isPasswordVisible.value;
};

const togglePasswordOkVisibility = () => {
	isPasswordOkVisible.value = !isPasswordOkVisible.value;
};

const checkEmail = () => {
	if (!emailError.value) {
		const existingEmails = ["test@example.com", "user@email.com"]; // API로 변경 예정
		emailExists.value = existingEmails.includes(email.value);
		emailChecked.value = true;
	}
};

const resetEmailCheck = () => {
	emailChecked.value = false;
	emailExists.value = false;
};

const emailError = computed(() => {
	const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	return !regex.test(email.value);
});

const passwordError = computed(() => {
	const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
	return !regex.test(password.value);
});

const passwordOkError = computed(() => {
	return password.value !== passwordOk.value;
});

const canProceed = computed(() => {
	return (!passwordOkError.value && !passwordError.value && !emailError.value && !emailExists.value && emailChecked.value && businessChecked.value && !businessError.value && !businessExists.value) ||
		(signupStore.social && businessChecked.value && !businessError.value && !businessExists.value);
});

const proceedToNextPage = () => {
	router.push('/signup/info');
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
					class="rounded-full border-2 border-[#3FB8AF] w-8 h-8 justify-center items-center flex text-[#3FB8AF] font-bold text-xl">
					<i class="fas fa-stream"></i>
				</div>
				<div class="rounded-full border-2 border-gray-300 w-8 h-8 justify-center items-center flex text-white">
				</div>
			</div>
		</section>
		<section class="flex flex-col items-center w-full mt-6">
			<h1 class="text-4xl font-bold text-gray-700">회원 정보 입력</h1>
			<div class="flex flex-col items-center mt-14">
				<div class="space-y-8">
					<div v-if="signupStore.isLandlord" class="flex flex-col">
						<label for="email" class="ms-2 text-gray-700 font-bold">사업자 등록 번호 확인<span
								class="ms-2 text-left text-red-500 text-sm">*</span></label>
						<div class="flex flex-col w-96 border-2 border-gray-300 mt-2 rounded-md">
							<div class="flex p-2 space-x-2">
								<input type="text" @input="resetBusinessCheck" v-model="businessName" required
									class="h-12 w-40 flex p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
									placeholder="사업자 이름" />
								<input type="text" @input="handleBusinessIdInput" v-model="businessId" maxlength="10"
									required
									class="h-12 w-40 flex p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
									placeholder="사업자 등록 번호" />
							</div>
							<div class="flex p-2 space-x-2">
								<input type="registered" id="date-single" v-model="businessDate" required
									@input="resetBusinessCheck"
									class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
									placeholder="사업자 등록일" />
								<button @click="checkBusiness" :disabled="!businessId || !businessName || !businessDate"
									class="h-12 border p-2 bg-[#3FB8AF] hover:bg-[#2c817c] disabled:bg-gray-300 text-white font-bold transition-colors rounded-md">확인</button>
							</div>
						</div>
						<span v-if="businessChecked && businessError" class="ms-2 text-left text-red-500 text-sm">
							유효하지 않은 사업자 등록 번호입니다.
						</span>
						<span v-if="businessChecked && !businessError && businessExists"
							class="ms-2 text-left text-red-500 text-sm">
							이미 사용 중인 사업자 등록 번호입니다.
						</span>
						<span v-if="businessChecked && !businessError && !businessExists"
							class="ms-2 text-left text-blue-500 text-sm">
							사용이 가능한 사업자 등록 번호입니다.
						</span>
					</div>
					<div v-if="!signupStore.social" class="flex flex-col">
						<label for="email" class="ms-2 text-gray-700 font-bold">이메일<span
								class="ms-2 text-left text-red-500 text-sm">*</span></label>
						<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
							<input type="email" id="email" @blur="emailTouched = true" @input="resetEmailCheck"
								v-model="email" required
								class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
								placeholder="이메일 주소 입력" />
							<button @click="checkEmail" :disabled="emailError || !email"
								class="h-12 border p-2 bg-[#3FB8AF] hover:bg-[#2c817c] disabled:bg-gray-300 text-white font-bold transition-colors rounded-md">확인</button>
						</div>
						<span v-if="emailTouched && emailError" class="ms-2 text-left text-red-500 text-sm">
							이메일이 양식에 맞지 않습니다.
						</span>
						<span v-if="emailChecked && !emailError && !emailExists"
							class="ms-2 text-left text-blue-500 text-sm">
							사용이 가능한 이메일입니다.
						</span>
						<span v-if="emailChecked && !emailError && emailExists"
							class="ms-2 text-left text-red-500 text-sm">
							이미 사용 중인 이메일입니다.
						</span>
					</div>
					<div v-if="!signupStore.social" class="flex flex-col">
						<label for="password" class="ms-2 text-gray-700 font-bold">비밀번호<span
								class="ms-2 text-left text-red-500 text-sm">*</span></label>
						<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
							<input :type="isPasswordVisible ? 'text' : 'password'" v-model="password" required
								id="password" @blur="passwordTouched = true"
								class="h-12 p-2 flex-1 focus-visible:outline-[#3FB8AF]"
								placeholder="8자리 이상 영문, 숫자, 특수문자 포함" />
							<div class="h-12 p-2 flex items-center justify-center transition-colors rounded-md w-10 text-gray-500 cursor-pointer"
								@click="togglePasswordVisibility">
								<i v-if="isPasswordVisible" class="fas fa-eye"></i>
								<i v-else class="fas fa-eye-slash"></i>
							</div>
						</div>
						<span v-if="passwordError && passwordTouched" class="ms-2 text-left text-red-500 text-sm">
							{{ /* v-else 에러 반환 */ "비밀번호가 양식에 맞지 않습니다." }}
						</span>
						<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
							<input :type="isPasswordOkVisible ? 'text' : 'password'" @blur="passwordOkTouched = true"
								v-model="passwordOk" class="h-12 p-2 flex-1 focus-visible:outline-[#3FB8AF]" required
								placeholder="비밀번호 확인" />
							<div class="h-12 p-2 flex items-center justify-center transition-colors rounded-md w-10 text-gray-500 cursor-pointer"
								@click="togglePasswordOkVisibility">
								<i v-if="isPasswordOkVisible" class="fas fa-eye"></i>
								<i v-else class="fas fa-eye-slash"></i>
							</div>
						</div>
						<span v-if="passwordOkError && passwordOkTouched" class="ms-2 text-left text-red-500 text-sm">
							{{ /* v-else 에러 반환 */ "비밀번호가 일치하지 않습니다." }}
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
