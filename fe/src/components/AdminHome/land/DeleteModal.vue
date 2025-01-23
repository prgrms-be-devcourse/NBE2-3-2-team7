<script setup>

defineProps({
	land: {
		type: Object,
		required: true,
	},
});

const emitEvent = defineEmits(["close", "delete"]);

// 모달 닫기
const closeModal = () => {
	emitEvent("close");
};

// 삭제 확정
const deleteLand = () => {
	emitEvent("delete");
	closeModal();
};
</script>

<template>
	<div class="fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center z-50" @click.self="closeModal">
		<div class="bg-white rounded-lg shadow-lg p-6 w-96 text-center">
			<h2 class="text-lg font-bold text-gray-700 mb-4">임대지 삭제</h2>
			<div class="text-sm text-gray-700 mb-6">
				<p>정말로 이 임대지를 삭제하시겠습니까?</p>
				<p>삭제 후에는 복구할 수 없습니다.</p>
			</div>
			<div class="flex justify-center space-x-4">
				<button class="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition-colors"
					@click="deleteLand">
					삭제
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
