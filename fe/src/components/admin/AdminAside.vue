<script setup>
import { ref } from 'vue';
import AdminFooter from './AdminFooter.vue';

const expanded = ref(true);

const toggleSidebar = () => {
	expanded.value = !expanded.value; // 사이드바 토글
};

</script>

<template>
	<aside :class="expanded ? 'w-48' : 'w-16'"
		class="bg-[#3FB8AF] text-white flex flex-col justify-between transition-all duration-300">
		<section>
			<button class="flex items-center justify-center w-full h-16" @click="toggleSidebar">
				<img v-if="expanded" src="../../assets/logo-long.png" alt="logo">
				<i v-else class="fas fa-bars text-2xl"></i>
			</button>
			<div class="border-b border-[#2c817c]"></div>
			<nav>
				<ul class="font-bold">
					<li class="px-4 py-2 hover:bg-[#2c817c] transition-colors">
						<router-link to="/admin" :class="[expanded ? 'justify-between' : 'justify-center', 'flex space-x-4']">
							<i class="fas fa-home text-lg"></i>
							<span v-if="expanded" class="whitespace-nowrap">대시보드</span>
						</router-link>
					</li>
					<li class="px-4 py-2 hover:bg-[#2c817c] transition-colors">
						<router-link to="/admin/user" :class="[expanded ? 'justify-between' : 'justify-center', 'flex space-x-4']">
							<i class="fas fa-users text-lg"></i>
							<span v-if="expanded" class="whitespace-nowrap">회원 관리</span>
						</router-link>
					</li>
					<li class="px-4 py-2 hover:bg-[#2c817c] transition-colors">
						<router-link to="/admin/orders" :class="[expanded ? 'justify-between' : 'justify-center', 'flex space-x-4']">
							<i class="fas fa-money-bill-wave text-lg"></i>
							<span v-if="expanded" class="whitespace-nowrap">거래 내역 관리</span>
						</router-link>
					</li>
					<li class="px-4 py-2 hover:bg-[#2c817c] transition-colors">
						<router-link to="/admin/land" :class="[expanded ? 'justify-between' : 'justify-center', 'flex space-x-4']">
							<i class="fas fa-building text-lg"></i>
							<span v-if="expanded" class="whitespace-nowrap">임대지 관리</span>
						</router-link>
					</li>
					<li class="px-4 py-2 hover:bg-[#2c817c] transition-colors">
						<router-link to="/admin/popup" :class="[expanded ? 'justify-between' : 'justify-center', 'flex space-x-4']">
							<i class="fas fa-store text-lg"></i>
							<span v-if="expanded" class="whitespace-nowrap">팝업 관리</span>
						</router-link>
					</li>
					<li class="px-4 py-2 hover:bg-[#2c817c] transition-colors">
						<router-link to="/admin/analytics" :class="[expanded ? 'justify-between' : 'justify-center', 'flex space-x-4']">
							<i class="fas fa-chart-line text-lg"></i>
							<span v-if="expanded" class="whitespace-nowrap">데이터 통계</span>
						</router-link>
					</li>
					<!-- <li class="px-4 py-2 hover:bg-[#2c817c] transition-colors">
						<router-link to="/admin/system" :class="[expanded ? 'justify-between' : 'justify-center', 'flex space-x-4']">
							<i class="fas fa-cogs text-lg"></i>
							<span v-if="expanded" class="whitespace-nowrap">시스템</span>
						</router-link>
					</li> -->
				</ul>
			</nav>
		</section>

		<AdminFooter v-if="expanded" class="whitespace-nowrap"/>
	</aside>
</template>

<style scoped></style>
