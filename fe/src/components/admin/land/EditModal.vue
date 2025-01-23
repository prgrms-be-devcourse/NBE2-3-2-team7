<script setup>
import { reactive } from "vue";

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
		<div class="bg-white rounded-lg shadow-lg p-6 w-96">
			<h2 class="text-xl font-bold text-gray-700 mb-4 text-center">사용자 정보 수정</h2>

			<!-- 수정 폼 -->
			<form @submit.prevent="saveUser">
				<div class="mb-4">
					<label for="name" class="block text-sm font-medium text-gray-600 mb-1">이름</label>
					<input id="name" v-model="editedUser.name" type="text"
						class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring focus:ring-[#3FB8AF]"
						required />
				</div>

				<div class="mb-4">
					<label for="email" class="block text-sm font-medium text-gray-600 mb-1">이메일</label>
					<input id="email" v-model="editedUser.email" type="email"
						class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring focus:ring-[#3FB8AF]"
						required />
				</div>

				<div class="mb-4">
					<label for="userType" class="block text-sm font-medium text-gray-600 mb-1">유형</label>
					<select id="userType" v-model="editedUser.type"
						class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring focus:ring-[#3FB8AF]">
						<option value="user">사용자</option>
						<option value="landlord">임대인</option>
						<option value="admin">관리자</option>
					</select>
				</div>

				<!-- 버튼 -->
				<div class="flex justify-center space-x-4">
					<button type="submit" class="px-4 py-2 bg-[#3FB8AF] text-white rounded-lg hover:bg-[#2c817c]"
						@click="saveUser">
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
