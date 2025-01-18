<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getPeriod } from '../utils/global.util';

const { VITE_TOSS_API_KEY } = import.meta.env;

const info = {
	userName: "홍길동",
	userEmail: "test@naver.com",
	userTel: "010-1234-5678",
	placeName: "테스트 임대지",
	price: 10000,
	customerKey: crypto.randomUUID(),
}

const route = useRoute();

// const landId = route.query.land;
const start = route.query.start;
const end = route.query.end;
const handlePayment = ref(null);

const script = document.createElement('script');
onMounted(async () => {
	script.src = `https://js.tosspayments.com/v2/standard`;
	document.head.appendChild(script);
});

script.onload = () => {
	handlePayment.value = async () => {
		const customerKey = info.customerKey;
		// eslint-disable-next-line no-undef
		const tossPayments = TossPayments(VITE_TOSS_API_KEY);
		const payment = tossPayments.payment({
			customerKey,
		});

		try {
			const amount = {
				currency: "KRW",
				value: getPeriod(start, end) * info.price,
			};
			let orderId = window.btoa(Math.random()).slice(0, 20);
			const tossInfo = {
				method: "CARD", // 카드 및 간편결제
				amount,
				orderId: orderId,
				orderName: info.placeName,
				successUrl: window.location.origin + "/payment/success", // 결제 요청이 성공하면 리다이렉트되는 URL
				failUrl: window.location.origin + "/payment/failure", // 결제 요청이 실패하면 리다이렉트되는 URL
				customerEmail: info.userEmail,
				customerName: info.userName,
				card: {
					useEscrow: false,
					flowMode: "DEFAULT",
					useCardPoint: false,
					useAppCardOnly: false,
				},
			}

			await payment.requestPayment(tossInfo);
		} catch (error) {
			console.error(error);
		}
	};
}
</script>

<template>
	<main class="flex-grow flex flex-col items-center">
		<section class="flex mt-10 px-4 w-full justify-center">
			<div class="max-w-5xl w-full border p-6 bg-white rounded-lg shadow-lg">
				<h1 class="text-2xl w-full font-bold mb-8 text-center text-gray-700">결제 페이지</h1>
				<div class="mb-8 w-full border-b pb-6">
					<h2 class="text-2xl font-semibold mb-4 text-gray-700">예약자 정보</h2>
					<div class="grid grid-cols-1 gap-6">
						<div>
							<p class="text-gray-600">예약자</p>
							<p id="user-name" class="font-semibold text-gray-800 text-lg">{{ }}</p>
						</div>
						<div>
							<p class="text-gray-600">이메일</p>
							<p id="user-email" class="font-semibold text-gray-800 text-lg">{{ }}</p>
						</div>
						<div>
							<p class="text-gray-600">전화번호</p>
							<p id="user-tel" class="font-semibold text-gray-800 text-lg">{{ }}</p>
						</div>
					</div>
				</div>
				<div class="mb-2 border-b pb-6">
					<h2 class="text-2xl font-semibold mb-4 text-gray-700">임대지 정보</h2>
					<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
						<div>
							<p class="text-gray-600">임대지</p>
							<p id="place-name" class="font-semibold text-gray-800 text-lg">{{ }}</p>
						</div>
						<div>
							<p class="text-gray-600">대여 기간</p>
							<p id="rental-period" class="font-semibold flex-shrink-0 text-gray-800 text-lg">{{ }}</p>
						</div>
						<div>
							<p class="text-gray-600">일일 금액</p>
							<p id="day-price" class="font-semibold text-gray-800 text-lg">{{ }}</p>
						</div>
						<div>
							<p class="text-gray-600">총 결제 금액</p>
							<p id="total-price" class="text-xl font-bold text-[#3FB8AF]">{{ }}</p>
						</div>
						<div class="md:col-span-2">
							<p class="text-gray-600">주소</p>
							<p id="full-address" class="font-semibold text-gray-800 text-base">{{ }}</p>
						</div>
					</div>
				</div>

				<div class="mb-4 pb-2 w-full">
					<span class="font-bold text-xs text-[#3FB8AF]">* 현재 카드 결제만 지원합니다.</span>
				</div>

				<div class="text-center">
					<button @click="handlePayment" id="payment-button" type="button"
						class="w-full md:w-1/3 bg-[#3FB8AF] text-white font-semibold py-3 rounded-lg hover:bg-[#2C817C] transition-colors">
						결제하기
					</button>
				</div>
			</div>
		</section>
	</main>
</template>

<style scoped></style>
