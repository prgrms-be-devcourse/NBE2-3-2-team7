const box = document.getElementById('item-box');
const paging = document.getElementById('paging');

const urlParams = new URLSearchParams(window.location.search);

const loc = urlParams.get("targetLocation");
const type = urlParams.get("type");
const age = urlParams.get("targetAgeGroup");
const start = urlParams.get("startDate");
const end = urlParams.get("endDate");
const sorting = urlParams.get("sorting");
const page = urlParams.get("page");

document.addEventListener('DOMContentLoaded', () => {
	init()
})

function init() {
	const params = new URLSearchParams();

	if (loc) params.append("targetLocation", loc);
	if (type) params.append("type", type);
	if (age) params.append("targetAgeGroup", age);
	if (start) params.append("startDate", start);
	if (end) params.append("endDate", end);
	if (sorting) params.append("sorting", sorting);
	if (page) params.append("page", page);

	createPickCalendar();
	setDefault();

	fetch(`/api/popup/list?${params.toString()}`)
		.then(resp => resp.json())
		.then(res => {
			let popup = '';
			if (res.content.length > 0) {
				res.content.forEach(item => {
					popup += `<a class="group drop-shadow-lg relative p-4 border m-2 rounded-lg border-gray-300" href="/popup/detail/${item.id}">
					<div class="absolute w-full h-full bg-gray-300 opacity-0 group-hover:opacity-50 transition left-0 top-0"></div>
					<div class="mt-2 block relative overflow-hidden rounded-lg border border-gray-400">
						<img class="w-full h-46 object-contain bg-gray-100" src="/images/popup_thumbnail/${item.thumbnail}" alt="Project">
						<div class="absolute bottom-1 end-1 opacity-0 group-hover:opacity-100 transition">
							<div class="flex items-center z-10 gap-x-1 py-1 px-2 bg-white border border-gray-200 text-gray-800 rounded-lg">
								<svg class="shrink-0 size-3" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
								<span class="text-xs">상세보기</span>
							</div>
						</div>
					</div>
					<div class="space-y-2 mt-4">
						<h3 class="text-lg font-semibold text-gray-900">${item.title}</h3>
						<h3 class="font-semibold text-gray-900">${item.startDate} ~ ${item.endDate}</h3>
						<div class="flex justify-between">
							<span>${item.wishArea}</span>
							<span>${item.type}</span>
						</div>
					</div>
				</a>`
				})
			} else {
				popup = `<div class="col-span-1 sm:col-span-2 lg:col-span-3 flex justify-center items-center">
                    <div class="flex items-center gap-2 text-gray-700 font-bold">
                        조회된 팝업이 없습니다.
                    </div>
                </div>`;
			}
				

			box.innerHTML = popup;
			paging.innerHTML = renderPagination(res.page.number, res.page.totalPages);
		})
}

function setDefault() {
	const placeList = document.getElementById('location');
	const ageList = document.getElementById('age');
	const categoryList = document.getElementById('category');
	const dateInput = document.getElementById('quick-calendar');
	const sorted = document.getElementById('sort');

	if(loc) placeList.value = loc;
	if(age) ageList.value = age;
	if(type) categoryList.value = type;
	if(start && end) dateInput.value = `${start} ~ ${end}`;
	if(sorting) sorted.value = sorting;
}

function findStore() {
	const placeList = document.getElementById('location').value;
	const ageList = document.getElementById('age').value;
	const categoryList = document.getElementById('category').value;
	const dateInput = document.getElementById('quick-calendar').value.split(' ~ ');
	const sorted = document.getElementById('sort').value;

	const startD = dateInput[0];
	const endD = dateInput[1];

	const findParams = new URLSearchParams();

	if (placeList) findParams.append("targetLocation", placeList);
	if (categoryList) findParams.append("type", categoryList);
	if (ageList) findParams.append("targetAgeGroup", ageList);
	if (startD) findParams.append("startDate", startD);
	if (endD) findParams.append("endDate", endD);
	if (sorted) findParams.append("sorting", sorted);

	window.location.href = '/popup/list?' + findParams;
}

function setPage(page) {
	if (urlParams.has("page")) {
		urlParams.set("page", page);
	} else {
		urlParams.append("page", page);
	}
	window.location.href = `/popup/list?${urlParams.toString()}`
}