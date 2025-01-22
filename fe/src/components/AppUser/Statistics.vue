<script setup>
import { ref } from 'vue';

// 샘플 데이터
const landStatus = [
  { title: "임대지1", tenants: 3, status: "임대 중" },
  { title: "임대지2", tenants: 5, status: "임대 중" },
  { title: "임대지3", tenants: 0, status: "임대 종료" },
];

const revenueData = [
  { title: "임대지1", revenue: "₩2,300,000" },
  { title: "임대지2", revenue: "₩4,300,000" },
  { title: "임대지3", revenue: "₩0" },
];
</script>

<template>
  <main class="flex flex-col items-center w-full px-4 py-6 bg-gray-50">
    <!-- 수익 현황 섹션 -->
    <section class="w-full max-w-4xl bg-white rounded-lg shadow-md p-6">
      <h2 class="text-xl font-bold mb-4">이번 달 수익 현황</h2>
      <table class="table-auto w-full text-left border-collapse">
        <thead>
        <tr>
          <th class="border-b-2 p-2">임대지</th>
          <th class="border-b-2 p-2">총 수익</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(revenue, index) in revenueData" :key="index" class="odd:bg-gray-100">
          <td class="border-b p-2">{{ revenue.title }}</td>
          <td class="border-b p-2">{{ revenue.revenue }}</td>
        </tr>
        </tbody>
      </table>
    </section>
  </main>
</template>

<style scoped>
table {
  border-spacing: 0;
}

th, td {
  text-align: left;
}

th {
  background-color: #f9fafb;
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: #f4f4f5;
}
</style>
