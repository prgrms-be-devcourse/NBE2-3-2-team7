<script setup>
import { ref, computed, onMounted } from 'vue';
import { Chart, registerables } from 'chart.js';
import { BarChart } from 'vue-chart-3';
import { initNoMinDateFlatpickr } from "@/utils/init.plugin.js";
import flatpickr from "flatpickr";

Chart.register(...registerables);

const today = new Date(); // 오늘 날짜
const todayString = today.toISOString().split('T')[0]; // 오늘 날짜 -> YYYY-MM-DD 문자열로 변환

// 7일 전 날짜 계산
const sevenDaysAgo = new Date();
sevenDaysAgo.setDate(today.getDate() - 7);
const sevenDaysAgoString = sevenDaysAgo.toISOString().split('T')[0];

// 7일전 ~ 오늘로 설정
const selectedRange = ref(`${sevenDaysAgoString} ~ ${todayString}`);

// 날짜 범위 파싱 함수
// selectedRange를 startDate와 endDate로 변환
const parseRange = (range) => {
  if (!range || typeof range !== "string" || (!range.includes(" ~ ") && !range.includes(" to "))) {
    return { startDate: null, endDate: null };
  }

  // to로 구분된 범위 처리
  const delimiter = range.includes(" ~ ") ? " ~ " : " to ";
  const [startDate, endDate] = range.split(delimiter).map(date => new Date(date));

  if (isNaN(startDate) || isNaN(endDate)) {
    // console.error("유효하지 않은 날짜 변환:", { startDate, endDate });
    return { startDate: null, endDate: null };
  }

  return { startDate, endDate };
};


const data = ref([
  { title: '임대지1', period: '2025-01-20', revenue: 1000000 },
  { title: '임대지2', period: '2025-01-19', revenue: 800000 },
  { title: '임대지3', period: '2025-01-19', revenue: 400000 },
  { title: '임대지1', period: '2025-01-18', revenue: 1200000 },
  { title: '임대지2', period: '2025-01-18', revenue: 600000 },
  { title: '임대지3', period: '2025-01-17', revenue: 950000 },
  { title: '임대지1', period: '2025-01-17', revenue: 300000 },
  { title: '임대지2', period: '2025-01-16', revenue: 1100000 },
  { title: '임대지3', period: '2025-01-16', revenue: 650000 },
  { title: '임대지1', period: '2025-01-15', revenue: 1020000 },
  { title: '임대지2', period: '2025-01-15', revenue: 280000 },
  { title: '임대지3', period: '2025-01-14', revenue: 430000 },
  { title: '임대지1', period: '2025-01-24', revenue: 330000 },
]);

onMounted(() => {
  initNoMinDateFlatpickr();

  // 날짜 선택기 초기화
  // : 사용자가 날짜 범위 변경하면 selectedRange 값 업데이트
  flatpickr("#date-range", {
    mode: "range",
    dateFormat: "Y-m-d",
    defaultDate: [sevenDaysAgoString, todayString], // 기본 값 : 7일 전 ~ 오늘
    onChange: (selectedDates) => {
      if (selectedDates.length === 2) {
        const [startDate, endDate] = selectedDates;

        const startDateString = startDate.toISOString().split('T')[0];
        const endDateString = endDate.toISOString().split('T')[0];

        selectedRange.value = `${startDateString} ~ ${endDateString}`;
      }
      /*
      else {
        console.warn("선택된 날짜가 부족합니다 : ", selectedDates);
      }
       */
    }
  });
});

const chartData = computed(() => {
  // 1. 날짜 범위 파싱
  const { startDate, endDate } = parseRange(selectedRange.value);

  // 2. 모든 날짜 배열 생성 (startDate ~ endDate 사이)
  const allDates = [];
  let currentDate = new Date(startDate);
  while (currentDate <= endDate) {
    allDates.push(currentDate.toISOString().split('T')[0]); // YYYY-MM-DD 형식으로 저장
    currentDate.setDate(currentDate.getDate() + 1); // 하루씩 더함
  }

  // 3. 데이터 필터링 : 기간이 범위 내에 있는 데이터만 추출
  const filteredData = data.value.filter(item => {
    const itemDate = new Date(item.period);
    return itemDate >= startDate && itemDate <= endDate;
  });

  // 4. 데이터 그룹화
  const groupedData = filteredData.reduce((acc, cur) => {
    if (!acc[cur.title]) {
      acc[cur.title] = {};
    }
    acc[cur.title][cur.period] = cur.revenue;
    return acc;
  }, {});

  // 5. 차트 형식으로 반환
  return {
    labels: allDates,
    datasets: Object.entries(groupedData).map(([title, periodData]) => ({
      label: title,
      data: allDates.map(period => periodData[period] !== undefined ? periodData[period] : 0), // 0으로 채우기
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
  <main class="flex-grow flex flex-col items-center">
    <div class="flex flex-col items-start mb-8 w-3/5">
      <!-- 상단 버튼들 -->
      <div class="flex justify-between pb-4 pt-3 border-b border-gray-300 w-full mb-4">
        <router-link to="/user" class="text-black bg-white hover:bg-gray-100 py-2 px-4 rounded-md transition-colors font-bold cursor-pointer">
          <i class="fas fa-angles-left"></i> 마이 페이지
        </router-link>
        <span class="text-black bg-white py-2 px-4 rounded-md transition-colors font-bold">임대지별 수익 통계</span>
      </div>
      <div class="bg-white min-w-60 border shadow px-4 border-gray-200 rounded-md p-2 flex justify-center items-center w-full mb-4">
        <label for="date-range" class="font-bold mr-4">조회 기간</label>
        <input type="text" id="date-range" v-model="selectedRange" placeholder="기간을 선택하세요." class="w-1/2">
      </div>
      <!-- 차트 -->
      <div class="w-full">
        <BarChart :chartData="chartData" :options="chartOptions" />
      </div>
    </div>
  </main>
</template>