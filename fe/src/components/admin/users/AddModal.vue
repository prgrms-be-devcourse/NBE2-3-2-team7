<script setup>
import { reactive, ref, computed } from "vue";
import defaultProfile from '../../../assets/images/default/default_profile.png';

const selectedRole = ref('customer');

const upload = ref(null);
const selectedFile = ref(null);
const previewSrc = ref(defaultProfile);

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

const name = ref('');

const brand = ref('');
const brandTouched = ref(false);
const brandChecked = ref(false);
const brandExists = ref(false);

const tel = ref('');
const telTouched = ref(false);
const telChecked = ref(false);
const telExists = ref(false);

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

const telError = computed(() => {
	const regex = /^\d{3}-\d{3,4}-\d{4}$/;
	return !regex.test(tel.value);
});

const canProceed = computed(() => !passwordOkError.value && !passwordError.value && !emailError.value && !emailExists.value && emailChecked.value && !telError.value && !telExists.value && telChecked.value && !brandExists.value && brandChecked.value && name.value);

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

const props = defineProps({
	user: {
		type: Object,
		required: true,
	},
});

const emitEvent = defineEmits(["close", "save"]);

// 사용자 수정 데이터
const editedUser = reactive({ ...props.user });

// 모달 닫기
const closeModal = () => {
	emitEvent("close");
};

// 수정 내용 저장
const saveUser = () => {
	emitEvent("save", editedUser); // 부모 컴포넌트로 수정된 사용자 데이터 전달
	closeModal();
};
</script>

<template>
	<div class="fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center z-50" @click.self="closeModal">
		<!-- 모달 내용 -->
		<div class="bg-white rounded-lg shadow-lg p-6">
			<h2 class="text-xl font-bold text-gray-700 mb-4">관리자 추가</h2>

			<!-- 수정 폼 -->
			<form @submit.prevent="saveUser" class="space-y-4">
				<div class="flex space-x-4">
					<div class="flex flex-col">
						<label for="profile" class="ms-2 text-gray-700 font-bold">프로필</label>
						<div class="flex flex-col justify-center items-center p-2 mt-2 space-y-4 rounded-md">
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
					<div class="space-y-1">
						<div class="flex flex-col">
							<label for="email" class="ms-2 text-gray-700 font-bold">이메일<span
									class="ms-2 text-left text-red-500 text-sm">*</span></label>
							<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
								<input type="email" id="email" @blur="emailTouched = true" @input="resetEmailCheck"
									v-model="email" required
									class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
									placeholder="이메일 주소 입력" />
								<button @click="checkEmail" :disabled="emailError || !email" type="button"
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
						<div class="flex flex-col">
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
								비밀번호가 양식에 맞지 않습니다.
							</span>
							<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
								<input :type="isPasswordOkVisible ? 'text' : 'password'"
									@blur="passwordOkTouched = true" v-model="passwordOk"
									class="h-12 p-2 flex-1 focus-visible:outline-[#3FB8AF]" required
									placeholder="비밀번호 확인" />
								<div class="h-12 p-2 flex items-center justify-center transition-colors rounded-md w-10 text-gray-500 cursor-pointer"
									@click="togglePasswordOkVisibility">
									<i v-if="isPasswordOkVisible" class="fas fa-eye"></i>
									<i v-else class="fas fa-eye-slash"></i>
								</div>
							</div>
							<span v-if="passwordOkError && passwordOkTouched"
								class="ms-2 text-left text-red-500 text-sm">
								비밀번호가 일치하지 않습니다.
							</span>
						</div>
					</div>
					<div class="flex flex-col space-y-1">
						<div class="flex flex-col">
							<label for="name" class="ms-2 text-gray-700 font-bold">
								이름
								<span class="ms-2 text-left text-red-500 text-sm">*</span>
							</label>
							<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
								<input type="text" id="name" v-model="name" required
									class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
									placeholder="이름 입력" />
							</div>
						</div>
						<div class="flex flex-col">
							<label for="brand" class="ms-2 text-gray-700 font-bold">
								{{ selectedRole !== 'landlord' ? "닉네임" : "상호명" }}
								<span class="ms-2 text-left text-red-500 text-sm">*</span>
							</label>
							<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
								<input type="text" id="brand" @blur="brandTouched = true" @input="resetBrandCheck"
									v-model="brand" required
									class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
									:placeholder="selectedRole !== 'landlord' ? '닉네임 입력' : '상호명 입력'" />
								<button @click="checkBrand" :disabled="!brand" type="button"
									class="h-12 border p-2 bg-[#3FB8AF] hover:bg-[#2c817c] disabled:bg-gray-300 text-white font-bold transition-colors rounded-md">확인</button>
							</div>
							<span v-if="brandChecked && brandExists" class="ms-2 text-left text-red-500 text-sm">
								이미 사용 중인 {{ selectedRole !== 'landlord' ? "닉네임" : "상호명" }}입니다.
							</span>
							<span v-if="brandChecked && !brandExists" class="ms-2 text-left text-blue-500 text-sm">
								사용이 가능한 {{ selectedRole !== 'landlord' ? "닉네임" : "상호명" }}입니다.
							</span>
						</div>
						<div class="flex flex-col">
							<label for="tel" class="ms-2 text-gray-700 font-bold">
								전화번호
								<span class="ms-2 text-left text-red-500 text-sm">*</span>
							</label>
							<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
								<input type="text" id="tel" @blur="telTouched = true" @input="resetAndFormattedTel"
									v-model="tel" required maxlength="13"
									class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
									placeholder="전화번호 입력" />
								<button @click="checkTel" :disabled="telError || !tel" type="button"
									class="h-12 border p-2 bg-[#3FB8AF] hover:bg-[#2c817c] disabled:bg-gray-300 text-white font-bold transition-colors rounded-md">확인</button>
							</div>
							<span v-if="telExists && telChecked && !telError"
								class="ms-2 text-left text-red-500 text-sm">
								이미 사용 중인 전화번호입니다.
							</span>
							<span v-if="!telExists && telChecked && !telError"
								class="ms-2 text-left text-blue-500 text-sm">
								사용이 가능한 전화번호입니다.
							</span>
						</div>
					</div>
				</div>

				<!-- 버튼 -->
				<div class="flex justify-center space-x-2">
					<button type="submit"
						class="px-4 py-2 bg-[#3FB8AF] text-white rounded-lg hover:bg-[#2c817c] disabled:bg-gray-300"
						@click="saveUser" :disabled="!canProceed">
						저장
					</button>
					<button type="button" class="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600"
						@click="closeModal">
						닫기
					</button>
				</div>
			</form>
		</div>
	</div>
</template>

<style scoped></style>
