<script setup>
import { ref, onMounted } from 'vue'
import { countStore } from '@/store/count';
import data from '@/services/test.api'

const store = countStore();

// 상태 관리
const count = ref(store.count) // store의 count 상태를 가져옴

const apiData = ref(null); // API에서 가져온 데이터를 저장할 변수
const loading = ref(false); // 데이터 로딩 상태
const error = ref(null); // 에러 상태

onMounted(() => {
	fetchApiData(); // API 데이터 호출
});

const increment = () => {
	store.increment(); // store의 increment 함수 호출
	count.value = store.count; // count 상태 업데이트
};

const fetchApiData = async () => {
	loading.value = true;
	error.value = null;
	try {
		const result = await data.testApi(); // testApi 호출
		apiData.value = result; // 받은 데이터를 apiData에 저장
	} catch (err) {
		error.value = err; // 에러 처리
	} finally {
		loading.value = false; // 로딩 상태 종료
	}
};

</script>

<template>
	<div class="card">
		<button type="button" @click="increment">count is {{ count }}</button>
	</div>
	<!-- API 호출 결과 출력 -->
	<div v-if="loading">Loading...</div>
	<div v-if="error" class="error">{{ error }}</div>
	<div v-if="apiData">
		<span>API Data : </span>
		<span>{{ apiData }}</span>
	</div>
</template>

<style scoped>
</style>
