<script setup>
import { ref, computed } from "vue";
import DeleteModal from "@/components/admin/land/DeleteModal.vue";
import BasePaging from "@/components/common/BasePaging.vue";
import DetailRow from "@/components/admin/land/DetailRow.vue";

const isDeleteModalOpen = ref(false);
const selectedLand = ref(null);
const selectedStatus = ref("");
const selectedLocation = ref("");

const rows = ref([
	{ id: 1, title: "강남역 5분 거리 건물", location: "서울", price: "100,000원", status: "활성화", date: "2025-01-15" },
	{ id: 2, title: "부산 해운대 상가", location: "부산", price: "80,000원", status: "비활성화", date: "2025-01-10" },
]);

const expandedRow = ref(null);

const toggleRow = (id) => {
	expandedRow.value = expandedRow.value === id ? null : id;
};



const deleteLand = () => {
	console.log(`삭제 성공`);
	// ajax
	isDeleteModalOpen.value = false;
};

const openDeleteModal = (land) => {
	selectedLand.value = land;
	isDeleteModalOpen.value = true;
}

computed(() => {
	return selectedStatus.value && selectedLocation.value;
});
</script>

<template>
	<main class="flex-col flex-1 overflow-auto height_scrollbar w-full max-w-6xl p-6">
		<div class="flex justify-between items-center mb-6">
			<h1 class="text-2xl font-bold text-gray-700">임대지 관리</h1>
		</div>

		<div class="flex items-center mb-4 space-x-4">
			<label for="">검색</label>
			<input type="text" placeholder="제목 검색"
				class="flex-1 border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring focus:ring-[#3FB8AF]" />
		</div>

		<div class="flex flex-col justify-center overflow-x-auto bg-white border border-gray-200 rounded-lg shadow">
			<table class="table-auto w-full text-center text-sm text-gray-700">
				<thead class="bg-gray-100 text-gray-500 font-bold">
					<tr>
						<th class="p-2">ID</th>
						<th class="p-2">제목</th>
						<th class="p-2">
							<div class="flex items-center justify-center">
								<select v-model="selectedLocation"
									class="border border-gray-300 rounded-lg p-1 text-sm focus:outline-none focus:ring focus:ring-[#3FB8AF]">
									<option value="">지역</option>
									<option value="서울">서울</option>
									<option value="부산">부산</option>
									<option value="대구">대구</option>
									<option value="인천">인천</option>
									<option value="광주">광주</option>
									<option value="대전">대전</option>
									<option value="울산">울산</option>
									<option value="세종">세종</option>
									<option value="경기">경기</option>
									<option value="충북">충북</option>
									<option value="충남">충남</option>
									<option value="전북">전북</option>
									<option value="전남">전남</option>
									<option value="경북">경북</option>
									<option value="경남">경남</option>
									<option value="강원">강원</option>
									<option value="제주">제주</option>
								</select>
							</div>
						</th>
						<th class="p-2">가격/일</th>
						<th class="p-2">
							<div class="flex items-center justify-center">
								<select v-model="selectedStatus"
									class="border border-gray-300 rounded-lg p-1 text-sm focus:outline-none focus:ring focus:ring-[#3FB8AF]">
									<option value="">상태</option>
									<option value="active">활성화</option>
									<option value="inactive">비활성화</option>
								</select>
							</div>
						</th>
						<th class="p-2">등록일</th>
						<th class="p-2">관리</th>
					</tr>
				</thead>
				<tbody>
					<template v-for="row in rows" :key="row.id">
						<tr class="border-t border-gray-300 hover:bg-gray-100e" @click="toggleRow(row.id)">
							<td class="p-2">{{ row.id }}</td>
							<td class="p-2">{{ row.title }}</td>
							<td class="p-2">{{ row.location }}</td>
							<td class="p-2">{{ row.price }}</td>
							<td class="p-2">
								<span
									:class="[row.status === '활성화' ? 'bg-[#3FB8AF]' : 'bg-red-500', 'text-white text-xs px-2 py-1 rounded']">
									{{ row.status }}
								</span>
							</td>
							<td class="p-2">{{ row.date }}</td>
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
			<DeleteModal v-if="isDeleteModalOpen" :user="selectedUser" @close="isDeleteModalOpen = false"
				@delete="deleteLand" />
		</div>
	</main>
</template>

<style scoped></style>
