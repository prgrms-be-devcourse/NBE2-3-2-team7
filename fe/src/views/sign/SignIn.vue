<script setup>
import { ref } from 'vue';
// eslint-disable-next-line no-unused-vars
const { VITE_BASE_API_URL } = import.meta.env;

const isPasswordVisible = ref(false); // 화면 표시 여부

let error = ref(true);
const signinChecked = ref(false);

const togglePasswordVisibility = () => {
	isPasswordVisible.value = !isPasswordVisible.value;
};

</script>

<template>
	<main class="flex-grow flex flex-col items-center">
		<section class="flex flex-col items-center w-full mt-6">
			<img src="../../assets/logo.png" alt="팝업마켓">
			<div class="flex flex-col items-center mt-14">
				<div class="min-w-96">
					<form class="space-y-6">
						<div class="flex flex-col">
							<label for="email" class="ms-2 text-gray-700 font-bold">
								이메일
							</label>
							<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
								<input type="email" id="email" @blur="emailTouched = true" @input="resetEmailCheck"
									v-model="email" required
									class="h-12 p-2 flex-1 focus-visible:outline-none focus-visible:border-[#3FB8AF] border-2 border-white transition-colors"
									placeholder="이메일 입력" />
							</div>
						</div>
						<div class="flex flex-col">
							<label for="password" class="ms-2 text-gray-700 font-bold">
								비밀번호
							</label>
							<div class="flex w-96 border-2 border-gray-300 p-2 mt-2 space-x-2 rounded-md">
								<input :type="isPasswordVisible ? 'text' : 'password'" v-model="password" required
									id="password" @blur="passwordTouched = true"
									class="h-12 p-2 flex-1 focus-visible:outline-[#3FB8AF]" placeholder="비밀번호 입력" />
								<div class="h-12 p-2 flex items-center justify-center transition-colors rounded-md w-10 text-gray-500 cursor-pointer"
									@click="togglePasswordVisibility">
									<i v-if="isPasswordVisible" class="fas fa-eye"></i>
									<i v-else class="fas fa-eye-slash"></i>
								</div>
							</div>
						</div>
						<div>
							<button type="submit"
								class="flex w-full justify-center rounded-md bg-[#3FB8AF] px-3 py-1.5 text-sm/6 font-semibold text-white shadow-sm hover:bg-[#2c817c]">Sign
								in with Email</button>
						</div>
						<div v-if="error && signinChecked" class="mt-4 text-center text-red-500 text-sm">
							이메일 또는 비밀번호가 올바르지 않습니다.
						</div>
					</form>
					<div class="mt-4">
						<div class="relative">
							<div class="absolute inset-0 flex items-center">
								<div class="w-full border-t border-gray-300"></div>
							</div>
							<div class="relative flex justify-center text-xs">
								<span class="bg-white px-2 text-gray-500">또는</span>
							</div>
						</div>
						<div class="mt-4 flex items-center justify-center ">
							<a href="/oauth2/authorization/google"
								class="h-9 border-gray-300 rounded-md border-2 bg-[#f2f2f2] hover:border-gray-400 transition-colors">
								<!-- Google Sign-In -->
								<img src="../../assets/images/logos/google_signin.png" alt="Google"
									class="object-contain h-full">
							</a>
						</div>

						<!-- 회원가입 링크 -->
						<p class="mt-5 text-center flex justify-center text-xs text-gray-400">
							<router-link to="/signup/role" class="hover:text-[#3FB8AF] transition-colors">
								Sign up with Email
							</router-link>
						</p>
					</div>
				</div>
			</div>
		</section>
	</main>
</template>

<style scoped></style>
