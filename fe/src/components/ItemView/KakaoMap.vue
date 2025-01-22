<!-- eslint-disable no-undef -->
<script setup>
import { onMounted, ref } from 'vue';
const { VITE_KAKAO_MAP_KEY } = import.meta.env;

const mapContainer = ref(null);

const props = defineProps({
	addr: String,
});

onMounted(() => {
	loadKakaoMap(mapContainer.value, props.addr);
});

const loadKakaoMap = (container, addr) => {
	const script = document.createElement('script');
	script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_MAP_KEY}&libraries=services&autoload=false`;
	document.head.appendChild(script);

	script.onload = () => {
		window.kakao.maps.load(() => {
			const options = {
				center: new window.kakao.maps.LatLng(33.450701, 126.570667),
				level: 3,
			}

			const map = new window.kakao.maps.Map(container, options)

			let geocoder = new kakao.maps.services.Geocoder();

	geocoder.addressSearch(addr, function (result, status) {
		if (status === window.kakao.maps.services.Status.OK) {
			let coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
			// let marker = new window.kakao.maps.Marker({
			new window.kakao.maps.Marker({
				map: map,
				position: coords
			});

			// let info = new window.kakao.maps.InfoWindow({
			// 	content: `<div style="width:150px;text-align:center;padding:6px 0;">&#8595;</div>`
			// });
			// info.open(map, marker);
			map.setCenter(coords);
		}
	});
		})
	}
	

};

</script>

<template>
	<div ref="mapContainer" class="w-full h-full">

	</div>
</template>

<style scoped></style>
