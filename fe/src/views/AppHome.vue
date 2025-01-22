<script setup>
import { onMounted, ref } from 'vue';
import LandCard from '../components/AppHome/LandCard.vue';
import LandFilter from '../components/AppHome/LandFilter.vue';
import PopupFilter from '../components/AppHome/PopupFilter.vue';
import PopupCard from '../components/AppHome/PopupCard.vue';

const land = [
	{ title: '임대지 1', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '100,000원', location: '서울', link: '/land/1' },
	{ title: '임대지 2', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/2' },
	{ title: '임대지 3', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/3' },
	{ title: '임대지 4', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/4' },
	{ title: '임대지 5', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/5' },
	{ title: '임대지 6', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/6' },
	{ title: '임대지 7', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/7' },
	{ title: '임대지 8', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/8' },
	{ title: '임대지 9', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', price: '200,000원', location: '부산', link: '/land/9' },
];

const popup = [
	{ title: '팝업스토어 1', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', type: '아이돌', location: '대구', link: '/popup/1' },
	{ title: '팝업스토어 2', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', type: '만화', location: '인천', link: '/popup/2' }
];

const selected = ref('land');

onMounted(() => {
	slideImage();
});

function slideImage() {
	const slides = document.getElementById('slide-container');
	const slideItems = document.querySelectorAll('#slide-container > div');
	const indicators = document.querySelectorAll('.indicator');

	let interval_3;

	let index = 0;

	const totalSlides = slideItems.length;

	const prevButton = document.getElementById('left-slide-arr');
	const nextButton = document.getElementById('right-slide-arr');

	prevButton.addEventListener('click', () => {
		index = (index - 1 + totalSlides) % totalSlides;
		moveSlide(slides, index);
		resetInterval();
	});

	nextButton.addEventListener('click', () => {
		index = (index + 1) % totalSlides;
		moveSlide(slides, index);
		resetInterval();
	});

	indicators.forEach((indicator, i) => {
		indicator.addEventListener('click', () => {
			index = i;
			moveSlide(slides, index);
			resetInterval();
		});
	});

	resetInterval();

	function resetInterval() {
		if (interval_3) clearInterval(interval_3);

		interval_3 = setInterval(() => {
			index = (index + 1) % totalSlides;
			moveSlide(slides, index);
		}, 5000);
	}

	function moveSlide(slides, index) {
		slides.style.transform = `translateX(-${index * 100}%)`;
	}
}


</script>

<template>
	<main class="flex-grow flex flex-col items-center">
		<article class="relative w-full min-h-96 max-h-[32rem] flex overflow-hidden bg-white">
			<!-- 슬라이드 배너 컨테이너 -->
			<div id="slide-container" class="flex w-full transition-transform duration-1000"
				style="transform: translateX(0%);">
				<!-- 슬라이드 항목 1 -->
				<div class="w-full h-full flex-shrink-0">
					<img src="../assets/images/slides/slide1.png" class="mx-auto h-full w-full object-contain" alt="">
				</div>
				<!-- 슬라이드 항목 2 -->
				<div class="w-full h-full flex-shrink-0">
					<img src="../assets/images/slides/slide2.png" class="mx-auto h-full w-full object-contain" alt="">
				</div>
				<!-- 슬라이드 항목 3 -->
				<div class="w-full h-full flex-shrink-0">
					<img src="../assets/images/slides/slide3.png" class="mx-auto h-full w-full object-contain" alt="">
				</div>
			</div>
			<div class="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-4 items-center">
				<button id="left-slide-arr"
					class="text-2xl text-white px-3 rounded-full hover:bg-gray-300 opacity-50 flex items-center justify-center">
					&#10094;
				</button>
				<button class="indicator hover:bg-blue-300 w-5 h-5 bg-gray-400 opacity-50 rounded-full"></button>
				<button class="indicator hover:bg-blue-300 w-5 h-5 bg-gray-400 opacity-50 rounded-full"></button>
				<button class="indicator hover:bg-blue-300 w-5 h-5 bg-gray-400 opacity-50 rounded-full"></button>
				<button id="right-slide-arr"
					class="text-2xl text-white px-3 rounded-full hover:bg-gray-300 opacity-50 flex items-center justify-center">
					&#10095;
				</button>
			</div>
		</article>
		<section class="flex w-full justify-center mt-10">
			<div class="flex max-w-7xl w-full px-4 flex-col">
				<div class="flex">
					<input type="radio" name="filter" id="filter-land" checked class="hidden" v-model="selected"
						value="land" />
					<label for="filter-land" :class="{
						'relative transform text-white transition-all duration-150 p-2 rounded-t-md font-bold bg-[#3FB8AF]': selected === 'land',
						'relative transform text-black translate-y-2 transition-all duration-150 p-2 rounded-t-md font-bold bg-[#d8d7d7]': selected !== 'land'
					}">
						장소 찾아요🙋
					</label>
					<input type="radio" name="filter" id="filter-popup" class="hidden" v-model="selected"
						value="popup" />
					<label for="filter-popup" :class="{
						'relative transform text-white transition-all duration-150 p-2 rounded-t-md font-bold bg-[#3FB8AF]': selected === 'popup',
						'relative transform text-black translate-y-2 transition-all duration-150 p-2 rounded-t-md font-bold bg-[#d8d7d7]': selected !== 'popup'
					}">
						팝업 찾아요🙋
					</label>
				</div>
				<div
					class="z-10 bg-white max-w-7xl border-t-2 border-b-2 border-[#3FB8AF] w-full p-4 flex items-center justify-center min-h-28">
					<LandFilter v-if="selected === 'land'" />
					<PopupFilter v-else />
				</div>
			</div>
		</section>

		<section class="mt-10 w-full px-4">
			<div class="border-2 max-w-7xl  mx-auto rounded-2xl ">
				<h3 class="pl-8 mt-4 text-2xl">추천 임대지</h3>
				<div class="relative rounded-xl overflow-auto my-4"><!-- Snap Point -->
					<div id="place-box" class="relative w-full flex gap-6 snap-x list_scrollbar overflow-x-auto pb-6">
						<LandCard v-for="(item, index) in land" :key="index" :image="item.image"
							:title="item.title" :price="item.price" :location="item.location" :link="item.link" />
					</div>
				</div>
			</div>
		</section>
		<section class="mt-10 w-full px-4">
			<div class="border-2 max-w-7xl mx-auto rounded-2xl ">
				<h3 class="pl-8 mt-4 text-2xl">추천 팝업스토어</h3>
				<div class="relative rounded-xl overflow-auto my-4"><!-- Snap Point -->
					<div id="popup-box" class="relative w-full flex gap-6 snap-x list_scrollbar overflow-x-auto pb-6">
						<PopupCard v-for="(item, index) in popup" :key="index" :image="item.image"
							:title="item.title" :type="item.type" :location="item.location" :link="item.link" />
					</div>
				</div>
			</div>
		</section>
	</main>
</template>

<style scoped></style>
