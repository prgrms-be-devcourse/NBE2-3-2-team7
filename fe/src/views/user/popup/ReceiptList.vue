<script setup>
import PaymentCard from "@/components/user/home/PaymentCard.vue";

const land = [
	{ title: "임대지1", id: "1", reserve: "1234", image: "https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80", date: "25-01-16 ~ 25-01-29", status: "결제 완료", price: "10,000", totalPrice: "50,000", },
	{ title: "임대지1", id: "1", reserve: "1234", image: "https://images.unsplash.com/photo-1604999565976-8913ad2ddb7c?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=320&amp;h=160&amp;q=80", date: "25-01-16 ~ 25-01-29", status: "결제 완료", price: "10,000", totalPrice: "50,000", },
];

const title = "임시 제목";

</script>

<template>
	<main class="flex-grow flex flex-col items-center relative">
		<div class="absolute top-4 flex items-center justify-between w-full max-w-3xl mx-auto px-4">
			<!-- 임대지 이름 -->
			<router-link to="/user" class="text-xl font-semibold flex-shrink-0">
				<i class="fas fa-angles-left"></i> {{ title }}
			</router-link>
			<!-- 임대지 예약 내역 -->
			<h3 class="text-base font-semibold text-right flex-grow">임대지 예약 내역</h3>
		</div>
		<div class="container mx-auto p-4 mt-12">
			<hr class="border-t border-gray-400 my-0.5 max-w-2xl mx-auto" />
			<br>
			<div class="space-y-4">
				<PaymentCard v-for="(item, index) in land" :key="index" :image="item.image" :title="item.title"
					:id="item.id" :reserve="item.reserve" :date="item.date" :status="item.status" :price="item.price"
					:totalPrice="item.totalPrice" :link="item.link" />
			</div>
		</div>
		<section class="mt-4 w-full px-4">
			<div class="max-w-3xl mx-auto">
				<div id="my-places-box" class="grid grid-cols-1 sm:grid-cols-2 gap-2 pt-4">
				</div>
			</div>
		</section>
	</main>
</template>