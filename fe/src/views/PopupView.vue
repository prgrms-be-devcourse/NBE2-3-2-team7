<script setup>
import { RouterLink } from 'vue-router';
import KakaoMap from '../components/ItemView/KakaoMap.vue';
import { onMounted } from 'vue';
import { imageSlider } from '../utils/global.util';

const addr = '서울특별시 강남구 역삼동';

onMounted(() => {
	imageSlider();
});

</script>

<template>
	<main class="flex-grow flex flex-col items-center">
		<div class="flex mt-2 max-w-7xl w-full justify-center px-4">
			<div class="max-w-7xl w-full flex items-center flex-shrink-0">
				<router-link href="/popup" class="font-bold p-2">&#10094;&#10094;</router-link>
				<span id="title" class="font-bold text-xl">팝업 스토어 기획 제목</span>
			</div>
		</div>
		<section class="flex w-full justify-center">
			<div class="flex flex-col lg:flex-row px-4 max-w-7xl w-full space-y-4 lg:space-y-0 lg:space-x-4">
				<div class="p-4 w-full border rounded-lg border-gray-300">
					<div class="flex-1 space-y-4">
						<div
							class="relative border overflow-hidden border-gray-300 rounded-md h-64 md:h-[24rem] lg:h-[36rem] bg-gray-300">
							<div id="image-container" class="flex w-full transition-transform h-full duration-1000"
								style="transform: translateX(0%);">
								<div class="w-full h-full flex-shrink-0"> <!-- v-for -->
									<img src="" class="mx-auto h-full w-full object-cover" alt="">
								</div>
							</div>
							<div class="absolute inset-0 w-full flex items-center justify-between px-4">
								<button id="prev-img-btn"
									class="text-2xl font-bold py-2 px-4 rounded-full hover:bg-gray-300 opacity-50 transition-colors">&#10094;</button>
								<button id="next-img-btn"
									class="text-2xl font-bold py-2 px-4 rounded-full hover:bg-gray-300 opacity-50 transition-colors">&#10095;</button>
							</div>
							<div class="absolute bottom-2 right-4 text-sm font-bold"><span id="slide-img-num"></span> /
								<span id="slide-img-total"></span>
							</div>
						</div>

						<!-- TODO -->
						<div
							class="flex lg:space-x-4 lg:space-y-0 space-x-0 space-y-4 lg:flex-row flex-col justify-between">
							<div class="space-y-4 text-sm w-full">
								<div class="space-y-2">
									<strong>희망 지역</strong>
									<p id="target-place"></p>
								</div>
								<div class="space-y-2">
									<strong>팝업 유형</strong>
									<p id="popup-type"></p>
								</div>
								<div class="space-y-2">
									<strong>임대 기간</strong>
									<p id="rental-date"></p>
								</div>
								<div class="space-y-2">
									<strong>주요 타겟</strong>
									<p id="target-age"></p>
								</div>
								<div class="space-y-2">
									<strong>면적</strong>
									<p id="target-area"></p>
								</div>
								<div class="space-y-2">
									<strong>팝업 설명</strong>
									<p id="popup-description">
									</p>
								</div>
								<div class="space-y-2">
									<strong>임대지 주소</strong>
									<p id="place-full-address"></p>
									<div class="border border-gray-300 rounded-md">
										<div class="h-80 w-full bg-gray-200 items-center flex justify-center">
											<KakaoMap :addr="addr" />
										</div>
									</div>
								</div>
							</div>

							<!-- <div
								class="w-full flex-shrink-0 lg:w-72 border max-h-fit border-gray-300 rounded-md p-4 shadow-md sticky top-4">
								<div class="text-center font-bold mb-2 pb-2 border-b">입점 요청</div>
								<div class="text-sm space-y-2 flex flex-col">
									<div class="bg-white px-4 py-2 flex-shrink-0 border border-gray-300 rounded-md">
										<label for="my-place" class="font-bold">임대지 목록</label>
										<select id="my-place" class="w-full px-1">
										</select>
									</div>
								</div>
								<button onclick="invitation()"
									class="w-full bg-[#3FB8AF] text-white py-2 rounded-md mt-2 hover:bg-[#2c817c] transition-colors">
									권유하기
								</button>
							</div> -->
						</div>
					</div>
				</div>
			</div>
		</section>
	</main>
</template>

<style scoped></style>
