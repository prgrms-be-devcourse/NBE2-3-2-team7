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
  <router-link :to="`/user/land/${item.id}`" class="w-full h-full relative flex flex-col items-center justify-center">
    <span class="absolute top-1 right-2 bg-white bg-opacity-75 text-gray-900 text-base font-bold px-2 py-1 rounded mb-2">
        {{ item.location }}
    </span>
    <div class="relative w-full h-3/5 flex items-center justify-center bg-gray-100">
      <img :src="item.image" alt="이미지" class="max-w-full max-h-full object-contain " />
    </div>
    <div class="w-full flex flex-col justify-between p-1 bg-white h-1/8 rounded-b-lg pt-4">
      <h3 class="text-base text-left truncate">{{ item.title }}</h3>
      <router-link
          :to="`/user/land/${item.id}/reservation`"
          class="text-green-700 hover:underline cursor-pointer"
      >
        예약 현황 보기 ( {{item.status}} )
      </router-link>
    </div>
  </router-link>
</template>

<style scoped>
.relative {
  position: relative;
}
</style>

