<script setup>
import { useRouter } from 'vue-router';
import LandCard from '@/components/user/home/LandCard.vue';
import PopupCard from '@/components/user/home/PopupCard.vue';
import PaymentCard from '@/components/user/home/PaymentCard.vue';
import LandAnalytics from '@/components/user/home/LandAnalytics.vue';

const user = [
	{ name: "바쿠고", image: "https://image.zeta-ai.io/profile-image/1248e692-a5ea-4504-b25b-cafba0065925/264b67a1-887d-4959-aa04-43bf10935f4d.jpeg?w=3840&q=75&f=webp", email: "admin@test.com", tel: "010-1234-5678", role: "landlord", businessId: "123-456-789" }
];

const land = [
	{ id: 1, title: "임대지1", image: "https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80", date: "25-01-16 ~ 25-01-29", link: '/land/1' },
	{ id: 2, title: "임대지2", image: "https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80", date: "25-01-13 ~ 25-01-31", link: '/land/2' },
	{ id: 3, title: "임대지3", image: "https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80", date: "25-01-01 ~ 25-01-02", link: '/land/3' },
	{ id: 4, title: "임대지4", image: "https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80", date: "25-01-01 ~ 25-01-03", link: '/land/4' },
];

const popup = [
	{ title: "팝업1", image: "https://d8nffddmkwqeq.cloudfront.net/store/68b81a24%2C6d81%2C4d94%2Caf9d%2C2056bc7fb37a", location: "서울 마포구", date: "24-12-25 ~ 25-01-17" },
	{ title: "팝업2", image: "https://d8nffddmkwqeq.cloudfront.net/store/68b81a24%2C6d81%2C4d94%2Caf9d%2C2056bc7fb37a", location: "서울 용산구", date: "24-12-25 ~ 25-01-17" },
	{ title: "팝업3", image: "https://d8nffddmkwqeq.cloudfront.net/store/68b81a24%2C6d81%2C4d94%2Caf9d%2C2056bc7fb37a", location: "서울 성동구", date: "24-12-25 ~ 25-01-17" },
	{ title: "팝업4", image: "https://d8nffddmkwqeq.cloudfront.net/store/68b81a24%2C6d81%2C4d94%2Caf9d%2C2056bc7fb37a", location: "부산 해운대구", date: "24-12-25 ~ 25-01-17" },
]

const payment = [
	{ land: "임대지1", date: "25.01.16 ~ 25.01.31", amount: "2,300,000 원", status: "예약 확정" },
	{ land: "임대지2", date: "25.01.01 ~ 25.01.31", amount: "4,300,000 원", status: "예약 확정" },
	{ land: "임대지3", date: "25.01.02 ~ 25.01.04", amount: "230,000 원", status: "예약 확정" },
];

const router = useRouter();

const navigateToUserEdit = (userInfo) => {
	router.push({
		path: '/user/edit',
		query: {
			name: userInfo.name,
			image: userInfo.image,
			email: userInfo.email,
			tel: userInfo.tel,
			role: userInfo.role,
			businessId: userInfo.businessId,
		},
	});
};

const role = user[0].role;  // role 값 설정 (customer 또는 landlord)
</script>

<template>
	<main class="flex-grow flex flex-col items-center">
		<section class="flex mt-1 w-3/4 bg-white justify-center">
			<div class="bg-white max-w-2xl w-full">
				<form id="edit-user-form" class="min-w-5xl space-y-12 p-8 flex-col" enctype="multipart/form-data"
					onsubmit="return false;">
					<div class="flex sm:flex-row flex-col justify-between sm:space-y-0 space-y-4">
						<!-- 프로필 이미지 영역 -->
						<div class="flex flex-col justify-center items-center space-y-4">
							<img id="profile-image" :src="user[0].image"
								class="rounded-full w-40 h-40 border border-gray-300 object-cover bg-gray-300" alt="">
							<input type="file" id="profile-real-upload" accept="image/gif, image/png, image/jpeg"
								class="hidden">
						</div>
						<!-- 사용자 정보 영역 -->
						<div class="flex flex-col space-y-4">
							<span class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0">
								이름 : {{ user[0].name }}
							</span>
							<span class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0">
								이메일 : {{ user[0].email }}
							</span>
							<span class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0">
								전화번호 : {{ user[0].tel }}
							</span>
							<span v-if="role === 'landlord'" class="h-9 p-1 font-bold text-gray-700 flex-shrink-0">
								사업자등록번호 : {{ user[0].businessId }}
							</span>
						</div>
						<!-- 버튼 영역 -->
						<div class="flex justify-end items-end h-50">
							<button @click="navigateToUserEdit(user[0])"
								class="text-white bg-[#3FB8AF] hover:bg-[#2c817c] p-2 rounded-md transition-colors text-sm font-normal mt-35">
								회원정보 수정하기
							</button>
						</div>
					</div>
					<hr class="border-t-1 border-gray-300 my-0" />
					<section class="mt-4 w-full px-4">
						<!-- 테두리 추가 -->
						<div class="border-2 max-w-full mx-auto rounded-2xl">
							<div class="flex justify-between items-center pl-8 pr-4 mt-2">
								<!-- 제목 -->
								<h3 class="text-lg">
									{{ role === 'landlord' ? '임대 중인 목록' : '팝업 목록' }}
								</h3>
								<router-link :to="role === 'landlord' ? `/user/land` : `/user/popup/`"
									class="text-black font-bold p-1 hover:text-gray-700 transition-colors rounded-md hover:bg-gray-100">
									전체 보기
								</router-link>
							</div>
							<div id="place-box" class="grid grid-cols-2 gap-2" style="max-width: 100%;">
								<template v-if="role === 'landlord'">
									<LandCard v-for="(item, index) in land" :key="index" :item="item"/>
								</template>
								<template v-if="role === 'customer'">
									<PopupCard v-for="(item, index) in popup" :key="index" :item="item" />
								</template>
							</div>
						</div>
					</section>
					<section class="mt-2 w-full px-4">
						<div class="border-2 max-w-full mx-auto rounded-2xl">
							<!-- 제목과 버튼을 Flexbox로 정렬 -->
							<div class="flex justify-between items-center pl-8 pr-4 mt-1">
								<h3 class="text-lg">
									{{ role === 'customer' ? '/' : '최근 일주일간의 수익 현황' }}
								</h3>
								<!-- 추후 수정 -->
								<router-link :to=" role === 'customer' ? '/user/payment' : '/user/stats'"
									class="text-black font-bold p-1 hover:text-gray-700 transition-colors rounded-md hover:bg-gray-100">
									<button id="view-all-btn" type="button">
										전체 보기
									</button>
								</router-link>
							</div>
								<template v-if="role === 'customer'">
                  <div id="payment-box" class="grid grid-cols-1 gap-2" style="max-width: 100%;">
									<PaymentCard v-for="(item, index) in payment" :key="index" :land="item.land"
										:link="item.link" :date="item.date" :amount="item.amount"
										:status="item.status" />
                  </div>
								</template>
								<template v-else>
                  <main class="flex flex-col items-center w-full px-4 py-6 bg-gray-50">
                    <LandAnalytics/>
                  </main>
								</template>
							</div>
					</section>
				</form>
			</div>
		</section>
	</main>
</template>