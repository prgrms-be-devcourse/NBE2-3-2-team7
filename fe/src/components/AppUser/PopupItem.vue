<script>
export default {
  props: {
    item: {
      type: Object,
      required: true,
    },
  },
};
</script>

<template>
  <router-link :to="`/user/popup/${item.id}`" class="w-full h-full relative flex flex-col items-center justify-center">
    <span class="absolute top-1 right-2 bg-white bg-opacity-75 text-gray-900 text-base font-bold px-2 py-1 rounded mb-2">
        {{ item.type }}
    </span>
    <!-- 이미지 -->
    <div class="relative w-full h-3/5 flex items-center justify-center bg-gray-100">
      <img :src="item.image" alt="이미지" class="max-w-full max-h-full object-contain " />
    </div>
    <!-- 텍스트 컨테이너 -->
    <div class="w-full flex flex-col justify-between p-1 bg-white h-1/8 rounded-b-lg pt-4">
      <h3 class="text-base font-semibold text-left truncate">{{ item.title }}</h3>
      <span> 입점 요청 확인 ( {{item.request}} 회) </span>
    </div>
  </router-link>
</template>

<style scoped>
.relative {
  position: relative;
}
</style>