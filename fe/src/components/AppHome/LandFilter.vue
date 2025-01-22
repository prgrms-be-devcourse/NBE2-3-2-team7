<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { usePriceSliderStore } from '../../store/price.slider';
import { useAreaSliderStore } from '../../store/area.slider';
import { initFlatpickr, initPriceSlider, initAreaSlider } from '../../utils/init.plugin';

const router = useRouter();

const priceSliderStore = usePriceSliderStore()
const areaSliderStore = useAreaSliderStore()

const minPrice = ref(priceSliderStore.min);
const maxPrice = ref(priceSliderStore.max);

const minArea = ref(areaSliderStore.min);
const maxArea = ref(areaSliderStore.max);

onMounted(() => {
	initFlatpickr();
	initPriceSlider(minPrice, maxPrice, priceSliderStore);
	initAreaSlider(minArea, maxArea, areaSliderStore);
});

const findLand = () => {
	const loc = document.getElementById('location');
	const period = document.getElementById('date-range').value.split(' ~ ');

	const params = {
		minArea: minArea.value || minArea.value === 0 ? minArea.value : undefined,
		maxArea: maxArea.value || maxArea.value === 0 ? maxArea.value : undefined,
		location: loc.value || undefined,
		minPrice: minPrice.value || minPrice.value === 0 ? minPrice.value : undefined,
		maxPrice: maxPrice.value || maxPrice.value === 0 ? maxPrice.value : undefined,
		start: period[0] || undefined,
		end: period[1] || undefined,
	};

	// eslint-disable-next-line no-unused-vars
	const query = Object.fromEntries(Object.entries(params).filter(([_, v]) => v !== undefined));

	router.push({ path: '/land', query });
};

</script>

<template>
	<div id="filter-box" class="flex flex-col min-w-80 lg:flex-row lg:space-y-0 lg:space-x-4 space-y-4">
		<div class="flex space-x-4">
			<div class="bg-white px-4 py-2 rounded-md border border-gray-300 min-w-[12rem]">
				<div class="mb-2 flex justify-between space-x-2">
					<h3 class="font-bold">면적</h3>
					<span id="area-range" class="text-center"></span>
				</div>
				<div id="area-slider" class="w-full"></div>
			</div>
			<div class="bg-white px-4 py-2 flex-shrink-0 border border-gray-300 rounded-md">
				<label for="location" class="font-bold">지역</label>
				<select id="location" class="w-full px-1">
					<option value="">전체</option>
					<option value="서울">서울</option>
					<option value="부산">부산</option>
					<option value="대구">대구</option>
					<option value="인천">인천</option>
					<option value="광주">광주</option>
					<option value="대전">대전</option>
					<option value="울산">울산</option>
					<option value="세종">세종</option>
					<option value="경기">경기</option>
					<option value="충북">충북</option>
					<option value="충남">충남</option>
					<option value="전북">전북</option>
					<option value="전남">전남</option>
					<option value="경북">경북</option>
					<option value="경남">경남</option>
					<option value="강원">강원</option>
					<option value="제주">제주</option>
				</select>
			</div>
		</div>
		<div class="bg-white px-4 py-2 rounded-md border border-gray-300 min-w-72">
			<div class="mb-2 flex justify-between">
				<h3 class="font-bold">금액</h3>
				<span id="price-range" class="text-center"></span>
			</div>
			<div id="price-slider" class="w-full"></div>
		</div>
		<div class="bg-white min-w-60 border px-4 border-gray-300 rounded-md p-2">
			<label for="date-range" class="block font-bold">임대 기간</label>
			<input type="text" id="date-range" placeholder="기간을 선택하세요." class="w-full">
		</div>

		<button @click="findLand"
			class="py-2 bg-[#3FB8AF] text-white font-bold flex-shrink-0 flex-grow-0 px-4 rounded-md hover:bg-[#2c817c] transition-colors duration-150">
			조회
		</button>
	</div>
</template>

<style scoped></style>