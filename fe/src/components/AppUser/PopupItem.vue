<script setup>
import { RouterLink } from 'vue-router';

defineProps({

  link: String,
  image: String,
  title: String,
  location: String,
  date: String,
});
</script>

<template>
  <div class="flex gap-2 overflow-x-scroll px-4">
    <router-link :to="link"
                 class="rounded-md flex p-4 shadow-xl bg-white border hover:transform hover:translate-y-[-10px] transition-transform duration-200 w-full max-w-[600px] h-24">
      <!-- 썸네일 이미지 -->
      <img class="w-32 h-20 rounded-lg shadow-xl bg-white" :src="image" :alt="title">

      <!-- 텍스트 영역 -->
      <div class="flex flex-col justify-between ml-4 w-full">
        <span class="font-bold text-base">{{ title }}</span>
        <div class="flex justify-between text-sm">
          <p>{{ date }}</p>
          <p>{{ location }}</p>
        </div>
      </div>
    </router-link>
  </div>
</template>

<style scoped></style>