const popupSeq = window.location.pathname.split('/').pop();

const title = document.getElementById('popup-title');
const imagesBox = document.getElementById('image-container');
const place = document.getElementById('target-place');
const type = document.getElementById('popup-type');
const rentalDate = document.getElementById('rental-date');
const age = document.getElementById('target-age');
const area = document.getElementById('target-area');
const description = document.getElementById('popup-description');

const myPlace = document.getElementById('my-place');

window.addEventListener('DOMContentLoaded', ()=> {
	init();
})

function init() {
	fetch(`/api/popup/view/${popupSeq}`)
		.then(resp=> resp.json())
		.then(res => {
			console.log(res);
			if (res.userRentalPlace.length >= 1) {
				let placeInsert = '';
				res.userRentalPlace.forEach((place) => {
					placeInsert += `<option value="${place.id}">${place.name}</option>`
				})

				myPlace.innerHTML = placeInsert;
			} else {
				myPlace.innerHTML = '<option value="">없음</option>'
			}

			renderPopupInfo(res.data);
			slideImage();
		})
		.catch(err => console.log(err));
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

function renderPopupInfo(data) {
	let result = '';
	if (data.images.length > 0) {
		data.images.forEach((image) => {
			result += `<div class="w-full h-full flex-shrink-0">
						<img src="/images/popup_detail/${image}" class="mx-auto h-full w-full object-cover" alt="">
					</div>`
		})
		imagesBox.innerHTML = result;
	} else {
		imagesBox.innerHTML = `<div class="w-full h-full flex-shrink-0">
						<img src="" class="mx-auto h-full w-full object-cover" alt="">
					</div>`;
	}

	title.innerHTML = data.popupStore.title;
	place.innerHTML = data.popupStore.targetLocation;
	type.innerHTML = data.popupStore.type;
	rentalDate.innerHTML = `${data.popupStore.startDate} ~ ${data.popupStore.endDate}`;
	age.innerHTML = data.popupStore.targetAgeGroup;
	area.innerHTML = data.popupStore.wishArea;
	description.innerHTML = data.popupStore.description;
}

function invitation() {
	if (myPlace.value) {
		fetch(`/api/invitation`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				"popupStoreSeq": popupSeq,
				"rentalPlaceSeq": myPlace.value
			})
		})
			.then(resp => {
				if (resp.ok) {
					alert("입점 요청을 했습니다.")
					window.location.reload();
				}
			})
			.catch(err => console.log(err));
	} else {
		alert('잘못된 요청입니다.')
	}
}