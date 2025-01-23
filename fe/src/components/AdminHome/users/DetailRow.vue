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
				<!-- 공통 정보 -->
				<div class="col-span-2">
					<div class="grid grid-cols-2 gap-x-4 gap-y-2">
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">회원 ID</span>
							<span class="text-gray-900">{{ row.id }}</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">이름</span>
							<span class="text-gray-900">{{ row.name }}</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">이메일</span>
							<span class="text-gray-900">{{ row.email }}</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">전화번호</span>
							<span class="text-gray-900">{{ row.tel }}</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">가입일</span>
							<span class="text-gray-900">{{ row.registeredAt }}</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">구분</span>
							<span class="text-gray-900">{{ row.role }}</span>
						</div>
					</div>
				</div>

				<!-- 사용자 정보 -->
				<div v-if="row.role === '사용자'" class="col-span-2">
					<div class="grid grid-cols-2 gap-x-4 gap-y-2">
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">등록된 팝업</span>
							<span class="text-gray-900">{{ 123 }}개</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">종료된 팝업</span>
							<span class="text-gray-900">{{ 10 }}개</span>
						</div>
					</div>
				</div>

				<!-- 임대인 정보 -->
				<div v-else-if="row.role === '임대인'" class="col-span-2">
					<div class="grid grid-cols-2 gap-x-4 gap-y-2">
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">사업자등록번호</span>
							<span class="text-gray-900">{{ '123-456-7890' }}</span>
						</div>
						<div class="flex">
							<span class="font-bold text-gray-600 w-32">보유한 임대지</span>
							<span class="text-gray-900">{{ 10 }}곳</span>
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</template>

<style scoped></style>
