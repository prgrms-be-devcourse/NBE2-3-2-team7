export const getKorDateRange = (startDate, endDate) => {
	const start = new Date(startDate);
	const end = new Date(endDate);

	const sYear = start.getFullYear();
	const sMonth = start.getMonth() + 1;
	const sDay = start.getDate();

	const eYear = end.getFullYear();
	const eMonth = end.getMonth() + 1;
	const eDay = end.getDate();

	return `${sYear}년 ${sMonth < 10 ? '0' + sMonth : sMonth}월 ${sDay < 10 ? '0' + sDay : sDay}일 ~ ${eYear}년 ${eMonth < 10 ? '0' + eMonth : eMonth}월 ${eDay < 10 ? '0' + eDay : eDay}일`;
}

export const getPeriod = (startDate, endDate) => {
	const start = new Date(startDate);
	const end = new Date(endDate);

	return (end - start) / (1000 * 3600 * 24) + 1;
}

export const truncateText = (text, length) => {
	if (text.length <= length) return text;

	return text.slice(0, length) + '...';
}

export const imageSlider = () => {
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
		num.innerHTML = index + 1;
		moveSlide(slides, index);
	});

	nextButton.addEventListener('click', () => {
		index = (index + 1) % totalSlides;
		num.innerHTML = index + 1;
		moveSlide(slides, index);
	});

	function moveSlide(slides, index) {
		slides.style.transform = `translateX(-${index * 100}%)`;
	}
}

// function daumPostcode() {
// 	const postcode = document.getElementById('postcode');
// 	const addr = document.getElementById('addr');

// 	new daum.Postcode({
// 		oncomplete: function(data) {
// 			var roadAddr = data.roadAddress; // 도로명 주소 변수

// 			postcode.value = data.zonecode;
// 			addr.value = roadAddr;
// 		}
// 	}).open();
// }


// function renderPagination(currentPage, totalPage) {
// 	const PAGE_GROUP_SIZE = 10;
// 	let pagination = '';

// 	const currentGroup = Math.floor(currentPage / PAGE_GROUP_SIZE);
// 	const startPage = currentGroup * PAGE_GROUP_SIZE;
// 	const nextGroupPage = Math.min(startPage + PAGE_GROUP_SIZE, totalPage);

// 	if (currentGroup > 0) {
// 		pagination = `<button onclick="setPage(${startPage})" class="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 hover:bg-gray-100">
// 				<span class="sr-only">이전</span>
// 				<svg class="size-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
// 					<path fill-rule="evenodd"
// 						d="M11.78 5.22a.75.75 0 0 1 0 1.06L8.06 10l3.72 3.72a.75.75 0 1 1-1.06 1.06l-4.25-4.25a.75.75 0 0 1 0-1.06l4.25-4.25a.75.75 0 0 1 1.06 0Z"
// 						clip-rule="evenodd"/>
// 				</svg>
// 			</button>`
// 	}

// 	for (let i = startPage; i < nextGroupPage; i++) {
// 		if (i === currentPage) {
// 			pagination += `<span class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-white bg-[#3FB8AF]">${i + 1}</span>`
// 		} else {
// 			pagination += `<button onclick="setPage(${i})" class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 hover:bg-gray-100">${i + 1}</button>`
// 		}
// 	}

// 	if (nextGroupPage < totalPage) {
// 		pagination += `<button onclick="setPage(${nextGroupPage})" class="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 hover:bg-gray-100">
// 				<span class="sr-only">다음</span>
// 				<svg class="size-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
// 					<path fill-rule="evenodd"
// 						d="M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z"
// 						clip-rule="evenodd"/>
// 				</svg>
// 			</button>`
// 	}

// 	return pagination;
// }