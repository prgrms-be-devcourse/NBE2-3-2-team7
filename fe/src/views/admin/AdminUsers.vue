<script setup>
import { ref, computed } from "vue";
import DeleteModal from "../../components/AdminHome/users/DeleteModal.vue";
import BasePaging from "../../components/common/BasePaging.vue";
import AddModal from "../../components/AdminHome/users/AddModal.vue";
import DetailRow from "../../components/AdminHome/users/DetailRow.vue";

const isDeleteModalOpen = ref(false);
const isAddModalOpen = ref(false);
const selectedUser = ref(null);
const selectedType = ref("");

const rows = ref([
	{ id: 1, name: "홍길동", email: "test1@test.com", tel: "010-1234-1234", role: "임대인", registeredAt: "2025-01-15" },
	{ id: 2, name: "김철수", email: "test2@test.com", tel: "010-1234-1234", role: "사용자", registeredAt: "2025-01-15" },
	{ id: 3, name: "이영희", email: "test3@test.com", tel: "010-1234-1234", role: "관리자", registeredAt: "2025-01-15" },
]);

const expandedRow = ref(null); // 확장된 행의 ID

const toggleRow = (id) => {
	expandedRow.value = expandedRow.value === id ? null : id; // 동일한 ID 클릭 시 닫기
};

const openDeleteModal = (user) => {
	selectedUser.value = user;
	isDeleteModalOpen.value = true;
}

const openAddModal = (user) => {
	selectedUser.value = user;
	isAddModalOpen.value = true;
}

const deleteUser = () => {
	console.log(`삭제 성공`);
	// ajax
	isDeleteModalOpen.value = false;
};

const addUser = () => {
	console.log(`추가 성공`);
	// ajax
	isAddModalOpen.value = false;
};

const filteredUsers = computed(() => {
	return selectedType.value;
});
</script>

<template>
	<main class="flex-col flex-1 overflow-auto height_scrollbar w-full max-w-6xl p-6">
		<div class="flex justify-between items-center mb-6">
			<h1 class="text-2xl font-bold text-gray-700">회원 관리</h1>
			<button @click="openAddModal"
				class="bg-[#3FB8AF] font-bold text-white px-4 h-full rounded-lg hover:bg-[#2c817c] transition-colors">
				관리자 추가
			</button>
		</div>

		<div class="flex items-center mb-4 space-x-4">
			<label for="">검색</label>
			<input type="text" placeholder="이름 또는 이메일 검색"
				class="flex-1 border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring focus:ring-[#3FB8AF]" />
		</div>

		<div class="flex flex-col justify-center overflow-x-auto bg-white border border-gray-200 rounded-lg shadow">
			<table class="table-auto w-full text-center text-sm text-gray-700">
				<thead class="bg-gray-100 text-gray-500 font-bold">
					<tr>
						<th class="p-2">ID</th>
						<th class="p-2">이름</th>
						<th class="p-2">전화번호</th>
						<th class="p-2">이메일</th>
						<th class="p-2">
							<div class="flex items-center justify-center">
								<select v-model="selectedType"
									class="border border-gray-300 rounded-lg p-1 text-sm focus:outline-none focus:ring focus:ring-[#3FB8AF]">
									<option value="">유형</option>
									<option value="사용자">사용자</option>
									<option value="임대인">임대인</option>
									<option value="관리자">관리자</option>
								</select>
							</div>
						</th>
						<th class="p-2">가입일</th>
						<th class="p-2">관리</th>
					</tr>
				</thead>
				<tbody>
					<template v-for="row in rows" :key="row.id">
						<tr v-if="!filteredUsers || filteredUsers === row.role" @click="toggleRow(row.id)"
							class="border-t border-gray-300 hover:bg-gray-100 cursor-pointer">
							<td class="p-2">{{ row.id }}</td>
							<td class="p-2">{{ row.name }}</td>
							<td class="p-2">{{ row.tel }}</td>
							<td class="p-2">{{ row.email }}</td>
							<td class="p-2">{{ row.role }}</td>
							<td class="p-2">{{ row.registeredAt }}</td>
							<td class="p-2" @click.stop>
								<button
									class="py-2 px-3 bg-white border border-red-500 text-red-500 rounded-lg hover:bg-red-500 hover:text-white text-xs ml-2 transition-colors"
									@click="openDeleteModal()">
									삭제
								</button>
							</td>
						</tr>
						<DetailRow v-if="expandedRow === row.id" :row="row" />
					</template>
				</tbody>
			</table>
			<BasePaging class="justify-center items-center flex" />
			<AddModal v-if="isAddModalOpen" :user="selectedUser" @close="isAddModalOpen = false" @save="addUser" />
			<DeleteModal v-if="isDeleteModalOpen" :user="selectedUser" @close="isDeleteModalOpen = false"
				@delete="deleteUser" />
		</div>
	</main>
</template>

<style scoped></style>
