<script setup>
import { Chart, registerables } from 'chart.js';
import { BarChart } from 'vue-chart-3';
import { ref, computed, onMounted } from 'vue';
import { initNoMinDateFlatpickr } from '../../utils/init.plugin'


Chart.register(...registerables);

const today = new Date();
const todayString = today.toISOString().split('T')[0];

const sevenDaysAgo = new Date();
sevenDaysAgo.setDate(today.getDate() - 7);
const sevenDaysAgoString = sevenDaysAgo.toISOString().split('T')[0];

const selectedRange = ref(`${sevenDaysAgoString} ~ ${todayString}`);
const selectedPeriod = ref('daily');
const selectedMetric = ref('revenue');
const data = ref([
	{ location: '서울', period: '2025-01-20', revenue: 1000000 },
	{ location: '서울', period: '2025-01-19', revenue: 800000 },
	{ location: '부산', period: '2025-01-19', revenue: 400000 },
	{ location: '서울', period: '2025-01-18', revenue: 1200000 },
	{ location: '부산', period: '2025-01-18', revenue: 600000 },
	{ location: '서울', period: '2025-01-17', revenue: 950000 },
	{ location: '대구', period: '2025-01-17', revenue: 300000 },
	{ location: '서울', period: '2025-01-16', revenue: 1100000 },
	{ location: '부산', period: '2025-01-16', revenue: 650000 },
	{ location: '서울', period: '2025-01-15', revenue: 1020000 },
	{ location: '대구', period: '2025-01-15', revenue: 280000 },
	{ location: '부산', period: '2025-01-14', revenue: 430000 },
]);

onMounted(() => {
	initNoMinDateFlatpickr();
});

const chartData = computed(() => {
	const periods = [...new Set(data.value.map(item => item.period))].sort((a, b) => new Date(a) - new Date(b));
	const groupedData = data.value.reduce((acc, cur) => {
		if (!acc[cur.location]) {
			acc[cur.location] = {};
		}
		acc[cur.location][cur.period] = cur.revenue;
		return acc;
	}, {});

	return {
		labels: periods,
		datasets: Object.entries(groupedData).map(([location, periodData]) => ({
			label: location,
			data: periods.map(period => periodData[period] || 0),
			backgroundColor: locationColors[location],
		})),
	};
});

const locationColors = {
	서울: '#4CAF50',
	부산: '#FF9800',
	대구: '#03A9F4',
};

const chartOptions = {
	responsive: true,
	maintainAspectRatio: false,
	plugins: {
		legend: {
			position: 'bottom',
		},
	},
	scales: {
		// x: {
		// 	stacked: true,
		// },
		// y: {
		// 	stacked: true,
		// },
	},
};
</script>

<template>
	<main class="flex-col flex-1 overflow-auto height_scrollbar w-full max-w-6xl p-6">
		<div class="flex justify-start items-center mb-6">
			<h1 class="text-2xl font-bold text-gray-700">데이터 분석 및 통계</h1>
		</div>
		<div class="bg-white shadow border border-gray-200 rounded-lg p-4 mb-6">
			<h2 class="text-gray-500 font-bold mb-4">지역별 수익 통계</h2>
			<div class="flex items-center space-x-4 py-2">
				<div class="bg-white min-w-60 border shadow px-4 border-gray-200 rounded-md p-2">
					<label class="font-bold">데이터 유형</label>
					<select class="w-full" v-model="selectedMetric">
						<option value="revenue">매출</option>
						<option value="transactions">거래 횟수</option>
					</select>
				</div>
				<div class="bg-white min-w-60 border shadow px-4 border-gray-200 rounded-md p-2">
					<label for="date-range" class="font-bold">조회 기간</label>
					<input type="text" id="date-range" v-model="selectedRange" placeholder="기간을 선택하세요." class="w-full">
				</div>
				<div class="bg-white min-w-60 border shadow px-4 border-gray-200 rounded-md p-2">
					<label class="font-bold">기간 단위</label>
					<select class="w-full" v-model="selectedPeriod">
						<option value="daily">일간</option>
						<option value="weekly">주간</option>
						<option value="monthly">월간</option>
					</select>
				</div>
			</div>
			<BarChart :chartData="chartData" :options="chartOptions" />
		</div>
	</main>
</template>

<style scoped></style>
