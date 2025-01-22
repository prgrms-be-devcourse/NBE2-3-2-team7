<script setup>
import { defineProps } from 'vue';

// props 정의: 각 항목에 대한 데이터를 전달받음
defineProps({
  link: String,
  id: String,
  reservenum: String,
  image: String,
  title: String,
  date: String,
  status: String,
  price: String,
  totalPrice: String,
});
</script>

<template>
  <router-link :to="link" class="block w-full max-w-2xl mx-auto">
    <div class="flex justify-between items-center p-2 border border-gray-300">
      <div class="flex flex-col w-full">
        <div class="flex justify-between">
          <div class="flex flex-col">
            <span class="text-gray-800 text-md mb-2">예약번호 {{ reservenum }}</span>
            <span class="text-gray-800 text-sm">기간: {{ date }}</span>
          </div>
          <div class="text-right">
            <span class="text-sm text-gray-600 block">{{ status }}</span>
            <span class="text-sm text-gray-600 block">{{ price }}원 / 일</span>
            <span class="font-bold text-gray-600 block">총 금액 {{ totalPrice }}원</span>
          </div>
        </div>
      </div>
    </div>
  </router-link>
</template>

<style scoped>
ul {
  list-style: none;
  margin: 0;
  padding: 0;
}
</style>

