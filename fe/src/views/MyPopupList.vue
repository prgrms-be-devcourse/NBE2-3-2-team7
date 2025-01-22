<script setup>
import { ref, onMounted } from 'vue';
import PopupItem from '../components/AppUser/PopupItem.vue';

const popupItems = ref([
  {title: '팝업1', id: 1, type: '애니', image: "https://ilovecharacter.com/news/data/20231024/p1065591351048692_273_thum.png", request: "1"},
  {title: '팝업2', id: 2, type: '전시', image: "https://ilovecharacter.com/news/data/20231024/p1065591351048692_273_thum.png", request: "0"},
]);
</script>

<template>
  <main class="flex-grow flex flex-col items-center">
    <section class="mt-4 w-full px-4">
      <div class="max-w-3xl mx-auto">
        <div class="flex justify-between pb-4 border-b border-gray-300">
          <a href="/user/" class="text-black bg-white hover:bg-gray-100 py-2 px-4 rounded-md transition-colors font-bold cursor-pointer"> << 마이 페이지 </a>
          <a href="/user/popup/add" class="text-white bg-[#3FB8AF] hover:bg-[#2c817c] py-2 px-4 rounded-md transition-colors font-bold cursor-pointer">추가</a>
        </div>
        <div id="my-places-box" class="grid grid-cols-1 sm:grid-cols-2 gap-4 pt-4 max-w-2xl mx-auto">
          <div
              v-for="(item, index) in popupItems"
              :key="index"
              class="relative aspect-square border border-gray-300 rounded-lg overflow-hidden"
          >
            <PopupItem :item="item" />
          </div>
        </div>
      </div>
    </section>
  </main>
</template>