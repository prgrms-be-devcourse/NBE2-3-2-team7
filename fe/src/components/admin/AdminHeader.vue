<script setup>
const handleUserSignout = () => {
	console.log('sign-out');
}

</script>

<template>
	<header class="bg-white h-16 p-4 flex justify-between items-center">
		<div class="flex relative justify-between w-full">
			<div class="items-center space-x-4 flex">
				<img src="../../assets/images/default/default_profile.png" alt="profile" class="rounded-full w-10 h-10">
				<span class="font-bold">홍길동</span>
			</div>

			<div class="space-x-2">
				<router-link to="/"
					class="bg-white text-[#3FB8AF] font-bold px-4 py-2 rounded-lg hover:text-[#2c817c] transition-colors">
					<i class="fas fa-external-link-alt"></i>
					Go to Service
				</router-link>
				<button @click="handleUserSignout"
					class="bg-[#3FB8AF] font-bold text-white px-4 py-2 rounded-lg hover:bg-[#2c817c] transition-colors">
					Sign out
				</button>
			</div>
		</div>

	</header>
</template>

<style scoped></style>
