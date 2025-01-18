const box = document.getElementById('item-box');
const paging = document.getElementById('paging');

const urlParams = new URLSearchParams(window.location.search);

const loc = urlParams.get("location");
const minArea = urlParams.get("minCapacity");
const maxArea = urlParams.get("maxCapacity");
const minPrice = urlParams.get("minPrice");
const maxPrice = urlParams.get("maxPrice");
const startDate = urlParams.get("startDate");
const endDate = urlParams.get("endDate");
const sorting = urlParams.get("sorting");
const page = urlParams.get("page");

document.addEventListener('DOMContentLoaded', () => {
	init();
})

function init() {
	const params = new URLSearchParams();

	if (loc) params.append("location", loc);
	if (minArea) params.append("minCapacity", minArea);
	if (maxArea) params.append("maxCapacity", maxArea);
	if (minPrice) params.append("minPrice", minPrice);
	if (maxPrice) params.append("maxPrice", maxPrice);
	if (startDate) params.append("startDate", startDate);
	if (endDate) params.append("endDate", endDate);
	if (sorting) params.append("sorting", sorting);
	if (page) params.append("page", page);

	createPlaceSlider(minArea, maxArea);

	if (minPrice) startPrice = minPrice / 10000;
	if (maxPrice) endPrice = maxPrice / 10000;

	createPriceSlider(startPrice, endPrice);
	createPickCalendar();

	setDefault()

	fetch(`/api/rental/list?${params.toString()}`)
		.then(resp => resp.json())
		.then(res => {
			let insert = '';
			console.log(res);
			if (res.content.length > 0) {
				res.content.forEach(item => {
					insert += `<a class="group drop-shadow-lg relative p-4 border m-2 rounded-lg border-gray-300" href="/rental/detail/${item.id}">
							<div class="absolute w-full h-full bg-gray-300 opacity-0 group-hover:opacity-50 transition left-0 top-0"></div>
							<div class="mt-2 block relative overflow-hidden rounded-lg border border-gray-400">
								<img class="w-full h-46 object-contain bg-gray-100" src="/images/place_thumbnail/${item.thumbnail}" alt="Project">
									<div class="absolute bottom-1 end-1 opacity-0 group-hover:opacity-100 transition">
										<div class="flex items-center z-10 gap-x-1 py-1 px-2 bg-white border border-gray-200 text-gray-800 rounded-lg">
											<svg class="shrink-0 size-3" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
											<span class="text-xs">상세보기</span>
										</div>
									</div>
							</div>
							<div class="space-y-2 mt-4">
								<h3 class="text-lg font-semibold text-gray-900">${truncateString(item.name)}</h3>
								<div class="flex justify-between">
									<span>${item.area}평</span>
									<span>${item.price.toLocaleString()}원 / 일</span>
								</div>
							</div>
						</a>`
				})
			} else {
				insert = `<div class="col-span-1 sm:col-span-2 lg:col-span-3 flex justify-center items-center">
                    <div class="flex items-center gap-2 text-gray-700 font-bold">
                        조회된 임대지가 없습니다.
                    </div>
                </div>`;
			}
			box.innerHTML = insert;
			paging.innerHTML = renderPagination(res.page.number, res.page.totalPages);
		})
}

function findPlace() {
	const placeList = document.getElementById('location').value;
	const dateInput = document.getElementById('quick-calendar').value.split(' ~ ');
	const sorted = document.getElementById('sort').value;

	const startD = dateInput[0];
	const endD = dateInput[1];

	const findParams = new URLSearchParams();

	if (placeList) findParams.append("location", placeList);
	if (startArea || startArea === 0) findParams.append("minCapacity", startArea);
	if (endArea || endArea === 0) findParams.append("maxCapacity", endArea);
	if (startPrice) findParams.append("minPrice", startPrice);
	if (endPrice) findParams.append("maxPrice", endPrice);
	if (startD) findParams.append("startDate", startD);
	if (endD) findParams.append("endDate", endD);
	if (sorted) findParams.append("sorting", sorted);
	if (sorted) findParams.append("sorting", sorted);
	if (page) findParams.append("page", page);

	window.location.href = '/rental/list?' + findParams;
}

function setDefault() {
	const placeList = document.getElementById('location');
	const dateInput = document.getElementById('quick-calendar');
	const sorted = document.getElementById('sort');

	if (loc) placeList.value = loc;
	if (startDate && endDate) dateInput.value = `${startDate} ~ ${endDate}`;
	if (sorting) sorted.value = sorting;
}

function setPage(page) {
	if (urlParams.has("page")) {
		urlParams.set("page", page);
	} else {
		urlParams.append("page", page);
	}
	window.location.href = `/rental/list?${urlParams.toString()}`
}