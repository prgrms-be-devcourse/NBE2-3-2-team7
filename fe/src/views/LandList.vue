<script setup>
import { onMounted, ref } from 'vue';
import LandCard from '../components/ItemList/LandCard.vue';
import BasePaging from '../components/ItemList/BasePaging.vue';
import { useAreaSliderStore } from '../store/area.slider';
import { usePriceSliderStore } from '../store/price.slider';
import { useRouter } from 'vue-router';
import { initFlatpickr, initPriceSlider, initAreaSlider } from '../utils/init.plugin';

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

const land = [
	{ title: '임대지 1', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '100,000원', location: '서울', link: '/land/1', start: '2025-01-02', end: '2025-01-12' },
	{ title: '임대지 2', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/2', start: '2025-01-02', end: '2025-01-12' },
	{ title: '임대지 3', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/3', start: '2025-01-02', end: '2025-01-12' },
	{ title: '임대지 4', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/4', start: '2025-01-02', end: '2025-01-12' },
	{ title: '임대지 5', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/5', start: '2025-01-02', end: '2025-01-12' },
	{ title: '임대지 6', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/6', start: '2025-01-02', end: '2025-01-12' },
	{ title: '임대지 7', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/7', start: '2025-01-02', end: '2025-01-12' },
	{ title: '임대지 8', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/8', start: '2025-01-02', end: '2025-01-12' },
	{ title: '임대지 9', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/9', start: '2025-01-02', end: '2025-01-12' },
];

const findLand = () => {
	const loc = document.getElementById('location');
	const period = document.getElementById('date-range').value.split(' ~ ');
	const sort = document.getElementById('sort');

	const params = {
		minArea: minArea.value || minArea.value === 0 ? minArea.value : undefined,
		maxArea: maxArea.value || maxArea.value === 0 ? maxArea.value : undefined,
		location: loc.value || undefined,
		minPrice: minPrice.value || minPrice.value === 0 ? minPrice.value : undefined,
		maxPrice: maxPrice.value || maxPrice.value === 0 ? maxPrice.value : undefined,
		start: period[0] || undefined,
		end: period[1] || undefined,
		sort: sort.value || undefined,
	};

	// eslint-disable-next-line no-unused-vars
	const query = Object.fromEntries(Object.entries(params).filter(([_, v]) => v !== undefined));

	router.push({ path: '/land', query });
};

</script>

<template>
	<main class="flex-grow flex flex-col items-center">
		<div class="flex  w-full mt-2 justify-center px-4">
			<div class="max-w-7xl w-full">
				<span class="font-bold text-xl ">임대지 목록</span>
			</div>
		</div>
		<section class="flex w-full px-4 mx-2 md:sticky md:top-0 pt-2 md:z-10 bg-white">
			<div
				class="flex w-full max-w-7xl space-x-0 md:space-x-2 border-t-2 border-b-2 border-[#3FB8AF] mx-auto py-2 justify-between items-center">
				<div class="flex flex-col md:flex-row space-y-2 space-x-0 md:space-y-0 md:space-x-2">
					<div class="flex space-y-2 space-x-0 xl:space-y-0 xl:space-x-2 flex-col xl:flex-row">
						<div
							class="bg-white px-4 py-2 flex-col flex-shrink-0 border flex space-x-2 border-gray-300 rounded-md">
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
						<div class="bg-white px-4 py-2 rounded-md border border-gray-300 min-w-60">
							<div class="mb-2 flex justify-between">
								<h3 class="font-bold">금액</h3>
								<label id="price-range" class="text-center"></label>
							</div>
							<div id="price-slider" class="w-full"></div>
						</div>
					</div>

					<div class="flex space-y-2 space-x-0 xl:space-y-0 xl:space-x-2 flex-col xl:flex-row">
						<div class="bg-white min-w-60 border px-4 border-gray-300 rounded-md p-2">
							<label for="date-range" class="font-bold">임대 기간</label>
							<input type="text" id="date-range" placeholder="기간을 선택하세요." class="w-full">
						</div>
						<div class="bg-white px-4 py-2 min-w-60 flex-shrink-0 rounded-md border border-gray-300">
							<div class="mb-2 flex justify-between space-x-2">
								<h3 class="font-bold">면적</h3>
								<label id="area-range" class="text-center"></label>
							</div>
							<div id="area-slider" class="w-full"></div>
						</div>
					</div>
				</div>
				<div class="flex flex-shrink-0 space-x-0 xl:space-x-2 flex-col xl:flex-row h-full justify-between">
					<div class="bg-white px-4 py-2 flex-col border flex space-x-2 border-gray-300 rounded-md">
						<label for="sort" class="font-bold">정렬</label>
						<select id="sort" class="w-full px-1">
							<option value="">정렬</option>
							<option value="registered_desc">최신순</option>
							<option value="registered_asc">등록순</option>
							<option value="price_desc">가격-내림차</option>
							<option value="price_asc">가격-오름차</option>
							<option value="area_desc">면적-내림차</option>
							<option value="area_asc">면적-오름차</option>
						</select>
					</div>
					<button @click="findLand"
						class="py-2 bg-[#3FB8AF] text-white font-bold flex-grow-0 px-4 rounded-md hover:bg-[#2c817c] transition-colors duration-150">
						조회
					</button>
				</div>
			</div>
		</section>

		<section class="mt-4 w-full px-4">
			<div class="max-w-7xl mx-auto">
				<div id="item-box" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-2">
					<LandCard v-for="(item, index) in land" :key="index" :link="item.link" :image="item.image"
						:title="item.title" :price="item.price" :location="item.location" :start="item.start"
						:end="item.end" />
				</div>
			</div>
		</section>
		<section class="mt-10 w-full px-4" aria-label="pagination">
			<div
				class="flex items-center flex-col space-y-2 justify-center border-t border-gray-200 bg-white px-4 py-3">
				<BasePaging />
			</div>
		</section>
	</main>
</template>

<style scoped></style>
