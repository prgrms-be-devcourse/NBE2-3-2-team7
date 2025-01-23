<script setup>

defineProps({
	order: {
		type: Object,
		required: true,
	},
});

const emitEvent = defineEmits(["close", "refund"]);

// 모달 닫기
const closeModal = () => {
	emitEvent("close");
};

// 삭제 확정
const refundOrder = () => {
	emitEvent("refund");
	closeModal();
};
</script>

<template>
	<div class="fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center z-50" @click.self="closeModal">
		<div class="bg-white rounded-lg shadow-lg p-6 w-96 text-center">
			<h2 class="text-lg font-bold text-gray-700 mb-4">환불 확인</h2>
			<div class="text-sm text-gray-700 mb-6">
				<p>정말로 환불을 하시겠습니까?</p>
			</div>
			<div class="flex justify-center space-x-4">
				<button class="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition-colors"
					@click="refundOrder">
					환불
				</button>
				<button class="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition-colors"
					@click="closeModal">
					닫기
				</button>
			</div>
		</div>
	</div>
</template>

<style scoped></style>
