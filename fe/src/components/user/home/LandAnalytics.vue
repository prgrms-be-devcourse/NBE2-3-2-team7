<script setup>
import { Chart, registerables } from 'chart.js';
import { BarChart } from 'vue-chart-3';
import { ref, computed} from 'vue';

Chart.register(...registerables);

const data = ref([
  { title: '임대지1', period: '2025-01-20', revenue: 1000000 },
  { title: '임대지2', period: '2025-01-19', revenue: 800000 },
  { title: '임대지3', period: '2025-01-19', revenue: 400000 },
]);

const chartData = computed(() => {
  // 1. 날짜 계산
  const today = new Date();
  const startDate = new Date(today);
  startDate.setDate(today.getDate() - 7);

  // 2. 날짜 형식 변환
  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  // 3. 최근 7일 간의 날짜 배열 생성
  const periods = [];
  for (let date = startDate; date <= today; date.setDate(date.getDate() + 1)) {
    periods.push(formatDate(new Date(date)));
  }

  // 4. 그룹화된 데이터 생성
  const groupedData = data.value.reduce((acc, cur) => {
    if (!acc[cur.title]) {
      acc[cur.title] = {};
    }
    acc[cur.title][cur.period] = cur.revenue;
    return acc;
  }, {});

  // 4. 차트 데이터 반환
  return {
    labels: periods,
    datasets: Object.entries(groupedData).map(([title, periodData]) => ({
      label: title,
      data: periods.map(period => periodData[period] || 0),
      backgroundColor: titleColors[title],
    })),
  };
});

const titleColors = {
  임대지1: '#4CAF50',
  임대지2: '#FF9800',
  임대지3: '#03A9F4',
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
  },
};

</script>

<template>
  <div class="p-4 bg-white rounded-lg shadow-md">
    <h2 class="text-xl font-bold mb-4">수익 통계</h2>
    <div class="bar-chart-container" >
      <BarChart :chartData="chartData" :options="chartOptions" />
    </div>
  </div>
</template>

<style scoped>
.bar-chart-container {
  width: 100%;
  height: 400px;
}
</style>

