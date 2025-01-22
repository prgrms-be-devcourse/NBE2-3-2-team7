<script setup>
import { ref, onMounted } from 'vue';
import LandItem from '../components/AppUser/LandItem.vue';

const landItems = ref([
  {title: '임대지1', id: 1, location: '서울 용산구 한남동', image: "https://images.squarespace-cdn.com/content/v1/63d617371ac8c311f82d07e6/2e876958-75ea-446d-9eca-91c277881729/EBRN6535.jpg?format=1500w", status: "등록중", link: "/user/land/1",},
  {title: '임대지2', id: 2, location: '서울 종로구 통인동', image: "https://images.squarespace-cdn.com/content/v1/63d617371ac8c311f82d07e6/2e876958-75ea-446d-9eca-91c277881729/EBRN6535.jpg?format=1500w", status: "관리중", link: "/user/land/2",},
]);
</script>

<template>
  <main class="flex-grow flex flex-col items-center">
    <section class="mt-4 w-full px-4">
      <div class="max-w-3xl mx-auto">
        <div class="flex justify-between pb-4 border-b border-gray-300">
          <a href="/user/" class="text-black bg-white hover:bg-gray-100 py-2 px-4 rounded-md transition-colors font-bold cursor-pointer"> << 마이 페이지 </a>
          <a href="/user/land/add" class="text-white bg-[#3FB8AF] hover:bg-[#2c817c] py-2 px-4 rounded-md transition-colors font-bold cursor-pointer">추가</a>
        </div>
        <div id="my-places-box" class="grid grid-cols-1 sm:grid-cols-2 gap-4 pt-4 max-w-2xl mx-auto">
          <div
              v-for="(item, index) in landItems"
              :key="index"
              class="relative aspect-square border border-gray-300 rounded-lg overflow-hidden"
          >
            <LandItem :item="item" />
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

