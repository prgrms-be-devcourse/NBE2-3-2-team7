<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { initFlatpickr } from '../../utils/init.plugin';

const router = useRouter();

onMounted(() => {
	initFlatpickr();
});

const findPopup = () => {
	const loc = document.getElementById('location');
	const type = document.getElementById('type');
	const age = document.getElementById('age');
	const period = document.getElementById('date-range').value.split(' ~ ');

	const params = {
		location: loc.value || undefined,
		type: type.value || undefined,
		age: age.value || undefined,
		start: period[0] || undefined,
		end: period[1] || undefined,
	};

	// eslint-disable-next-line no-unused-vars
	const query = Object.fromEntries(Object.entries(params).filter(([_, v]) => v !== undefined));

	router.push({ path: '/popup', query });
};
</script>

<template>
	<div id="filter-box" class="flex flex-col min-w-80 lg:flex-row lg:space-y-0 lg:space-x-4 space-y-4">
		<div class="flex space-x-4">
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

		<button @click="findPopup"
			class="py-2 bg-[#3FB8AF] text-white font-bold flex-shrink-0 flex-grow-0 px-4 rounded-md hover:bg-[#2c817c] transition-colors duration-150">
			조회
		</button>
	</div>
</template>

<style scoped></style>
