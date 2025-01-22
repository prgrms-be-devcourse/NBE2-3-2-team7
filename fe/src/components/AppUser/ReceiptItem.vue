<script setup>
import { RouterLink } from 'vue-router';

defineProps({
  // link, 임대지, 결제 일자, 결제 금액, 예약 상태
  link: String,
  landitem: String,
  date: String,
  amount: String,
  status: String,
});
</script>

<template>
  <div class="flex gap-1 overflow-x-scroll px-4">
    <router-link :to="link"
                 class="rounded-md flex justify-between items-center p-4 shadow-xl bg-white border transition-transform duration-200 w-full max-w-[600px] h-16">
      <span class="font-bold text-base">{{ date }}</span>
      <!-- 세로로 나열되는 date, amount, status -->
      <div class="flex flex-col text-sm space-y-0.5 text-right">
        <p>{{ landitem }}</p>
        <p>{{ amount }}</p>
        <p>{{ status }}</p>
      </div>
    </router-link>
  </div>
</template>

<style scoped></style>