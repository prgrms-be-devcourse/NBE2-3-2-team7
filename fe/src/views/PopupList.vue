<script setup>
// 초반 import 구문
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { initFlatpickr } from '../utils/init.plugin';
import BasePaging from '../components/ItemList/BasePaging.vue';
import PopupCard from '../components/ItemList/PopupCard.vue';

// 예시 팝업 데이터
const popup = [
	{ title: '팝업스토어 1', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', type: '아이돌', location: '대구', link: '/popup/1', start: '2025-01-02', end: '2025-01-12' },
	{ title: '팝업스토어 2', image: 'https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80', type: '만화', location: '인천', link: '/popup/2', start: '2025-01-04', end: '2025-01-14' }
];

// 라우터 설정 및 초기화
const router = useRouter();

onMounted(() => {
	initFlatpickr();
});

// 사용자가 입력한 검색 조건 가져오기 -> URL 쿼리 파라미터 생성 -> 페이지 이동
const findPopup = () => {
	const loc = document.getElementById('location');
	const type = document.getElementById('type');
	const age = document.getElementById('age');
	const period = document.getElementById('date-range').value.split(' ~ ');
	const sort = document.getElementById('sort');

	const params = {
		location: loc.value || undefined,
		type: type.value || undefined,
		age: age.value || undefined,
		start: period[0] || undefined,
		end: period[1] || undefined,
		sort: sort.value || undefined,
	};

	// eslint-disable-next-line no-unused-vars
	const query = Object.fromEntries(Object.entries(params).filter(([_, v]) => v !== undefined));

	router.push({ path: '/popup', query });
};

// 사용자는 검색 필드 입력 -> 검색 버튼 -> findPopup 함수 호출
// -> 조건에 따라 URL 변경 ex. /popup?location=대구&type=애니메이션 ..
</script>

<template>
	<main class="flex-grow flex flex-col items-center">
		<div class="flex  w-full mt-2 justify-center px-4">
			<div class="max-w-7xl w-full">
				<span class="font-bold text-xl ">팝업 스토어 기획 목록</span>
			</div>
		</div>
		<section class="flex w-full px-4 mx-2 md:sticky md:top-0 pt-2 md:z-10 bg-white">
			<div
				class="flex w-full max-w-7xl space-x-0 md:space-x-2 border-t-2 border-b-2 border-[#3FB8AF] mx-auto py-2 justify-between items-center">
				<div class="flex flex-col md:flex-row space-y-2 space-x-0 md:space-y-0 md:space-x-2">
					<div class="flex space-y-2 space-x-0 xl:space-y-0 xl:space-x-2 flex-col xl:flex-row">
						<div class="bg-white px-4 py-2 flex-col flex-shrink-0 border flex border-gray-300 rounded-md">
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
						<div class="bg-white px-4 py-2 flex-grow border border-gray-300 rounded-md">
							<label for="age" class="font-bold">연령대</label>
							<select id="age" class="w-full px-1">
								<option value="">전체</option>
								<option value="10대">10대</option>
								<option value="20대">20대</option>
								<option value="30대">30대</option>
								<option value="40대">40대</option>
								<option value="50대">50대</option>
								<option value="60대 이상">60대 이상</option>
							</select>
						</div>
					</div>

					<div class="flex space-y-2 space-x-0 xl:space-y-0 xl:space-x-2 flex-col xl:flex-row">
						<div class="bg-white px-4 py-2 border border-gray-300 rounded-md">
							<label for="type" class="font-bold">유형</label>
							<select id="type" class="w-full px-1">
								<option value="">전체</option>
								<option value="식품">식품</option>
								<option value="화장품">화장품</option>
								<option value="패션">패션</option>
								<option value="애니">애니</option>
								<option value="아이돌">아이돌</option>
								<option value="스포츠">스포츠</option>
								<option value="음악">음악</option>
								<option value="테크">테크</option>
								<option value="인테리어">인테리어</option>
								<option value="문화/예술">문화/예술</option>
								<option value="게임">게임</option>
								<option value="헬스">헬스</option>
								<option value="음료">음료</option>
								<option value="책">책</option>
								<option value="친환경">친환경</option>
							</select>
						</div>
						<div class="bg-white min-w-60 border px-4 border-gray-300 rounded-md p-2">
							<label for="date-range" class="font-bold">임대 기간</label>
							<input type="text" id="date-range" placeholder="기간을 선택하세요." class="w-full">
						</div>
					</div>
				</div>
				<div class="flex flex-shrink-0 space-x-0 xl:space-x-2 flex-col xl:flex-row justify-between h-full">
					<div class="bg-white px-4 py-2 flex-col border flex border-gray-300 rounded-md">
						<label for="sort" class="font-bold">정렬</label>
						<select id="sort" class="w-full px-1">
							<option value="">정렬</option>
							<option value="registered_desc">최신순</option>
							<option value="registered_asc">등록순</option>
						</select>
					</div>
					<button @click="findPopup"
						class="py-2 bg-[#3FB8AF] text-white font-bold flex-grow-0 px-4 rounded-md hover:bg-[#2c817c] transition-colors duration-150">
						조회
					</button>
				</div>
			</div>
		</section>

		<section class="mt-4 w-full px-4">
			<div class="max-w-7xl mx-auto">
				<div id="item-box" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-2">
					<PopupCard v-for="(item, index) in popup" :key="index" :link="item.link" :image="item.image"
						:title="item.title" :type="item.type" :location="item.location" :start="item.start"
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
