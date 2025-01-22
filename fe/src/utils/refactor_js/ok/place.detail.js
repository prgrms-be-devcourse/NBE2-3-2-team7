const placeSeq =  window.location.pathname.split('/').pop();

const placeTitle = document.getElementById('place-title');
const imagesBox = document.getElementById('image-container');
const infraBox = document.getElementById('infra-box');
const ageGroup = document.getElementById('age-group');
const placeArea = document.getElementById('place-area');
const placeDescription = document.getElementById('place-description');
const placeAddress = document.getElementById('place-full-address');
const placePrice = document.getElementById('place-price');
const totalPrice = document.getElementById('total-price');
const periodInput = document.getElementById('quick-calendar');

window.addEventListener('DOMContentLoaded', ()=> {
	init()
});

periodInput.addEventListener('change', (e)=> {
	let rentalDate = e.target.value.split(' ~ ');

	if (rentalDate[0] !== '' && rentalDate[1] !== '' &&
		rentalDate[0] !== null && rentalDate[1] !== null &&
		rentalDate[0] !== undefined && rentalDate[1] !== undefined) {
		
		let period = getPeriod(rentalDate[0], rentalDate[1]);
		let price = placePrice.innerHTML.replace('원 / 일', '').replace(',','');

		totalPrice.innerHTML = `${(Number(price) * period).toLocaleString()}원`;
	} 
})

function init() {
	fetch(`/api/rental/view/${placeSeq}`)
		.then(resp=> resp.json())
		.then(res => {
			console.log(res);

			renderPlaceInfo(res.data);
			createPickCalendar(convertPeriodArray(res.reservationPeriod));
			slideImage();
		})
		.catch(err => console.log(err));
}

function reserve() {
	const rentalDate = periodInput.value.split(' ~ ');

	if (rentalDate[0] === '' || rentalDate[1] === '' ||
		rentalDate[0] === null || rentalDate[1] === null ||
		rentalDate[0] === undefined || rentalDate[1] === undefined) {
		alert('올바른 기간을 선택해주세요.');
	} else {
		window.location.href=`/payment?place=${placeSeq}&start=${rentalDate[0]}&end=${rentalDate[1]}`;
	}
}

function slideImage() {
	const slides = document.getElementById('image-container');
	const slideItems = document.querySelectorAll('#image-container > div');

	let index = 0;

	const totalSlides = slideItems.length;

	const prevButton = document.getElementById('prev-img-btn');
	const nextButton = document.getElementById('next-img-btn');

	const num = document.getElementById('slide-img-num');
	const total = document.getElementById('slide-img-total');
	num.innerHTML = 1;
	total.innerHTML = totalSlides;

	prevButton.addEventListener('click', () => {
			index = (index - 1 + totalSlides) % totalSlides;
			num.innerHTML = index+1;
			moveSlide(slides, index);
	});

	nextButton.addEventListener('click', () => {
			index = (index + 1) % totalSlides;
			num.innerHTML = index+1;
			moveSlide(slides, index);
	});

	function moveSlide(slides, index) {
		slides.style.transform = `translateX(-${index * 100}%)`;
	}
}

function renderPlaceInfo(data) {
	const info = data.rentalPlace;

	if (data.images.length > 0) {
		let imageInsert = '';
		data.images.forEach((item) => {
			imageInsert += `<div class="w-full h-full flex-shrink-0">
					<img src="/images/place_detail/${item.image}" class="mx-auto h-full w-full object-cover" alt="">
				</div>`
		})
		imagesBox.innerHTML = imageInsert;
	}

	let infraList = info.infra.split(',');

	let infraInsert = '';
	infraList.forEach(item => {
		infraInsert += `<span class="inline-flex items-center rounded-xl bg-white shadow-md px-4 py-2 text-xs text-black font-bold ring-2 ring-inset ring-[#3FB8AF] cursor-default">${item}</span>`
	})

	infraBox.innerHTML = infraInsert;

	placeTitle.innerHTML = info.name;
	ageGroup.innerHTML = info.nearbyAgeGroup;
	placeArea.innerHTML = `${info.area}평`;
	placeDescription.innerHTML = info.description;
	placeAddress.innerHTML = `[${info.zipcode}] ${info.address}, ${info.addrDetail}`;
	placePrice.innerHTML = `${info.price.toLocaleString()}원 / 일`
	totalPrice.innerHTML = `0원`;

	createMap(info.address, info.name);
}

function createMap(addr, placeName) {
	let container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	let options = { //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
		level: 3 //지도의 레벨(확대, 축소 정도)
	};

	let map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

	let geocoder = new kakao.maps.services.Geocoder();

	// 주소로 좌표를 검색합니다
	geocoder.addressSearch(addr, function(result, status) {

		// 정상적으로 검색이 완료됐으면
		if (status === kakao.maps.services.Status.OK) {

			var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

			// 결과값으로 받은 위치를 마커로 표시합니다
			var marker = new kakao.maps.Marker({
				map: map,
				position: coords
			});

			if (placeName) {
				// 인포윈도우로 장소에 대한 설명을 표시합니다
				var infowindow = new kakao.maps.InfoWindow({
					content: `<div style="width:150px;text-align:center;padding:6px 0;">${placeName}</div>`
				});
				infowindow.open(map, marker);
			}
			// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
			map.setCenter(coords);
		}
	});
}

function convertPeriodArray(data) {
	let period = [];
	data.forEach(item => {
		period.push({
			"from": item.startDate,
			"to": item.endDate,
		});
	})

	return period;
}