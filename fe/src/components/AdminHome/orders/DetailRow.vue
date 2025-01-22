<script setup>
import { defineProps } from "vue";

// 상위 컴포넌트에서 전달받은 데이터
defineProps({
	row: {
		type: Object,
		required: true,
	},
});
</script>

<template>
	<tr class="bg-gray-50">
		<td colspan="7" class="p-4 border-t border-gray-300">
			<div class="grid grid-cols-2 gap-x-6 gap-y-4">
				<!-- 사용자 정보 -->
				<div class="col-span-2">
					<h3 class="font-bold text-lg text-gray-700 mb-2">사용자 정보</h3>
					<div class="grid grid-cols-2 gap-x-4 gap-y-2">
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">사용자 ID</span>
							<span class="text-gray-900">14</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">사용자</span>
							<span class="text-gray-900">홍길동</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">이메일</span>
							<span class="text-gray-900">{{ row.userEmail }}</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">전화번호</span>
							<span class="text-gray-900">{{ row.userPhone }}</span>
						</div>
					</div>
				</div>

				<!-- 결제 정보 -->
				<div class="col-span-2">
					<h3 class="font-bold text-lg text-gray-700 mb-2">결제 정보</h3>
					<div class="grid grid-cols-2 gap-x-4 gap-y-2">
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">주문 번호</span>
							<span class="text-gray-900">PAY001</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">결제 금액</span>
							<span class="text-gray-900">1,600,000원</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">결제 상태</span>
							<span class="text-gray-900">결제 완료</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">결제 일자</span>
							<span class="text-gray-900">2025-01-01</span>
						</div>
					</div>
				</div>

				<!-- 임대지 정보 -->
				<div class="col-span-2">
					<h3 class="font-bold text-lg text-gray-700 mb-2">임대지 정보</h3>
					<div class="grid grid-cols-2 gap-x-4 gap-y-2">
						<div class="flex col-span-2">
							<span class="font-bold text-gray-600 w-32">제목</span>
							<span class="text-gray-900">{{ row.rentalTitle }}</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">임대인 ID</span>
							<span class="text-gray-900">10</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">임대인</span>
							<span class="text-gray-900">이몽룡</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">이메일</span>
							<span class="text-gray-900">ho12ng@example.com</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">전화번호</span>
							<span class="text-gray-900">010-5678-9101</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">가격</span>
							<span class="text-gray-900">200,000원</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">면적</span>
							<span class="text-gray-900">30평</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">주소</span>
							<span class="text-gray-900">{{ row.rentalAddress }}</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">임대 기간</span>
							<span class="text-gray-900">2025-01-05 ~ 2025-01-12</span>
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</template>

<style scoped></style>
