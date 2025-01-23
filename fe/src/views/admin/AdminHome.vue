<script setup>
import { BarChart } from 'vue-chart-3';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

const userChartData = {
	labels: ["01.02", "01.03", "01.04", "01.05", "01.06", "01.07", "01.08"],
	datasets: [
		{
			label: "일일 가입자",
			data: [50, 100, 150, 200, 250, 300, 1500],
			backgroundColor: "#3FB8AF",
		},
		{
			label: "일일 거래량",
			data: [20, 80, 120, 180, 320, 200, 150],
			backgroundColor: "#2c817c",
		}
	],
};

const chartOptions = {
	responsive: true,
	maintainAspectRatio: false,
	plugins: {
		legend: {
			position: "bottom",
		},
		tooltip: {
			enabled: true,
		},
	},
};
</script>

<template>
	<main class="p-6 flex-1 space-y-6 overflow-auto height_scrollbar mx-2 max-w-7xl w-full justify-center items-center">
		<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
			<div class="relative bg-white border border-gray-200 shadow rounded-lg py-6 text-center group">
				<div class="font-bold">
					<span class="text-gray-700"><i class="fas fa-users"></i> 총 회원 수</span>
					<h1 class="text-4xl my-3 text-gray-500">1,205명</h1>
				</div>
				<div
					class="text-gray-500 absolute inset-0 font-bold bg-white bg-opacity-85 flex flex-col justify-center items-center rounded-lg opacity-0 group-hover:opacity-100 transition-opacity">
					<p class="flex w-full justify-between px-6">사용자 <span>1,000</span></p>
					<p class="flex w-full justify-between px-6">임대인 <span>200</span></p>
					<p class="flex w-full justify-between px-6">관리자 <span>5</span></p>
				</div>
			</div>
			<div class="relative bg-white border border-gray-200 shadow rounded-lg py-6 text-center group">
				<div class="font-bold">
					<span class="text-gray-700"><i class="fas fa-money-bill-wave"></i> 월별 거래 현황</span>
					<h1 class="text-4xl my-3 text-gray-500">1,205회</h1>
				</div>
				<div
					class="text-gray-500 absolute inset-0 font-bold bg-white bg-opacity-85 flex flex-col justify-center items-center rounded-lg opacity-0 group-hover:opacity-100 transition-opacity">
					<p class="flex w-full justify-between px-6">총 매출 <span>1,000만원</span></p>
					<p class="flex w-full justify-between px-6">총 수익 <span>700만원</span></p>
				</div>
			</div>

			<div class="relative bg-white border border-gray-200 shadow rounded-lg py-6 text-center group">
				<div class="font-bold">
					<span class="text-gray-700"><i class="fas fa-building"></i> 총 임대지 현황</span>
					<h1 class="text-4xl my-3 text-gray-500">1,200곳</h1>
				</div>
				<div
					class="text-gray-500 absolute inset-0 font-bold bg-white bg-opacity-85 flex flex-col justify-center items-center rounded-xl opacity-0 group-hover:opacity-100 transition-opacity">
					<p class="flex w-full justify-between px-6">활성화 <span>1,000</span></p>
					<p class="flex w-full justify-between px-6">비활성화 <span>200</span></p>
				</div>
			</div>
			<div class="relative bg-white border border-gray-200 shadow rounded-lg py-6 text-center group">
				<div class="font-bold">
					<span class="text-gray-700"><i class="fas fa-store"></i> 총 팝업 현황</span>
					<h1 class="text-4xl my-3 text-gray-500">1,200곳</h1>
				</div>
				<div
					class="text-gray-500 absolute inset-0 font-bold bg-white bg-opacity-85 flex flex-col justify-center items-center rounded-xl opacity-0 group-hover:opacity-100 transition-opacity">
					<p class="flex w-full justify-between px-6">활성화 <span>1,000</span></p>
					<p class="flex w-full justify-between px-6">비활성화 <span>200</span></p>
				</div>
			</div>
		</div>

		<div class="grid grid-cols-1 gap-6">
			<div class="bg-white shadow border border-gray-200 rounded-lg p-4">
				<h2 class="text-gray-500 font-bold mb-4">일별 신규 가입자</h2>
				<div class="flex w-full">
					<BarChart :chartData="userChartData" :options="chartOptions" class="w-full" />
				</div>
			</div>
		</div>

		<div class="bg-white shadow border border-gray-200 rounded-lg p-4">
			<h2 class="text-gray-500 font-bold mb-4">최근 거래 내역</h2>
			<table class="w-full text-left">
				<thead>
					<tr class="text-gray-500">
						<th class="p-2">고객명</th>
						<th class="p-2">임대인명</th>
						<th class="p-2">결제 금액</th>
						<th class="p-2">임대 기간</th>
						<th class="p-2">결제 상태</th>
					</tr>
				</thead>
				<tbody>
					<tr class="hover:bg-gray-100 border-t border-gray-300">
						<td class="p-2">홍길동</td>
						<td class="p-2">임꺽정</td>
						<td class="p-2">1000<span class="text-xs">만원</span></td>
						<td class="p-2">24-12-31<span class="text-xs"> ~ </span>25-01-05</td>
						<td class="p-2 text-green-500">임대 완료</td>
					</tr>
					<tr class="hover:bg-gray-100 border-t border-gray-300">
						<td class="p-2">이몽룡</td>
						<td class="p-2">임꺽정</td>
						<td class="p-2">89<span class="text-xs">만원</span></td>
						<td class="p-2">24-12-31<span class="text-xs"> ~ </span>25-01-05</td>
						<td :class="[true ? 'text-blue-500' : 'text-red-500', 'p-2']">결제 완료</td>
					</tr>
				</tbody>
			</table>
		</div>
	</main>

</template>

<style scoped></style>
