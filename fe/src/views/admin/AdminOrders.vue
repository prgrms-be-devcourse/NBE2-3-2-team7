<script setup>
import { ref, computed } from "vue";
import BasePaging from "../../components/common/BasePaging.vue";
import DetailRow from "../../components/AdminHome/orders/DetailRow.vue"
import RefundModal from "../../components/AdminHome/orders/RefundModal.vue";

const selectedStatus = ref("");
const selectedOrder = ref(null);
const isRefundModalOpen = ref(false);
const rows = ref([
	{
		id: 1,
		userName: "홍길동",
		userEmail: "hong@example.com",
		userPhone: "010-1234-5678",
		userAddress: "서울 강남구 테헤란로 12",
		paymentAmount: "10,000원",
		paymentMethod: "신용카드",
		discount: "1,000원",
		status: "결제 완료",
		rentalTitle: "강남역 5분 거리 매장",
		rentalArea: 30,
		rentalAddress: "서울 강남구 테헤란로 15",
		rentalFacilities: "Wi-Fi, 주차장",
	},
]);


const expandedRow = ref(null);

const toggleRow = (id) => {
	expandedRow.value = expandedRow.value === id ? null : id;
};

const filteredStatus = computed(() => {
	return selectedStatus.value
});

const openRefundModal = (order) => {
	selectedOrder.value = order;
	isRefundModalOpen.value = true;
};
</script>

<template>
	<main class="flex-col flex-1 overflow-auto height_scrollbar w-full max-w-6xl p-6">
		<div class="flex justify-between items-center mb-6">
			<h1 class="text-2xl font-bold text-gray-700">거래 내역 관리</h1>
		</div>

		<div class="flex items-center mb-4 space-x-4">
			<label for="">검색</label>
			<input type="text" placeholder="사용자 이름 검색"
				class="flex-1 border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring focus:ring-[#3FB8AF]" />
		</div>

		<div class="flex flex-col justify-center overflow-x-auto bg-white border border-gray-200 rounded-lg shadow">
			<table class="table-auto w-full text-center text-sm text-gray-700">
				<thead class="bg-gray-100 text-gray-500 font-bold">
					<tr>
						<th class="p-2">주문 번호</th>
						<th class="p-2">사용자 이름</th>
						<th class="p-2">결제 금액</th>
						<th class="p-2">임대 기간</th>
						<th class="p-2">
							<div class="flex items-center justify-center">
								<select v-model="selectedStatus"
									class="border border-gray-300 rounded-lg p-1 text-sm focus:outline-none focus:ring focus:ring-[#3FB8AF]">
									<option value="">결제 상태</option>
									<option value="completed">결제 완료</option>
									<option value="leased">임대 완료</option>
									<option value="refund">환불 완료</option>
								</select>
							</div>
						</th>
						<th class="p-2">결제 일자</th>
						<th class="p-2">관리</th>
					</tr>
				</thead>
				<tbody>
					<template v-for="row in rows" :key="row.id">
						<tr v-if="!filteredStatus || filteredStatus === 'completed'" @click="toggleRow(row.id)"
							class="border-t border-gray-300 hover:bg-gray-100 cursor-pointer">
							<td class="p-2">PAY001</td>
							<td class="p-2">홍길동</td>
							<td class="p-2">10,000원</td>
							<td class="p-2">2025-01-05 ~ 2025-01-12</td>
							<td class="p-2">
								<span
									:class="[row.status === '결제 완료' ? 'bg-[#3FB8AF]' : 'bg-red-500', 'text-white text-xs px-2 py-1 rounded']">
									{{ row.status }}
								</span>
							</td>
							<td class="p-2">2025-01-05</td>
							<td class="p-2" @click.stop>
								<button
									class="py-2 px-3 bg-white border border-red-500 text-red-500 rounded-lg hover:bg-red-500 hover:text-white text-xs ml-2 transition-colors"
									@click="openRefundModal()">
									환불
								</button>
							</td>
						</tr>
						<DetailRow v-if="expandedRow === row.id" :row="row" />
					</template>
				</tbody>
			</table>
			<BasePaging class="justify-center items-center flex" />
			<RefundModal v-if="isRefundModalOpen" :refund="selectedOrder" @close="isRefundModalOpen = false" />
		</div>
	</main>
</template>
