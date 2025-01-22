let filterBox;
let placeBox;
let eventBox;

window.addEventListener('DOMContentLoaded', () => {
    filterBox = document.getElementById('main-filter-box');
    placeBox = document.getElementById('main-filter-place');
    eventBox = document.getElementById('main-filter-event');
    slideImage();

    createPriceSlider();
    createPlaceSlider();
    createPickCalendar();

    init();
});

function slideImage() {
    const slides = document.getElementById('slide-container');
    const slideItems = document.querySelectorAll('#slide-container > div');
    const indicators = document.querySelectorAll('.indicator');

    let interval_3;

    let index = 0;

    const totalSlides = slideItems.length;

    const prevButton = document.getElementById('left-slide-arr');
    const nextButton = document.getElementById('right-slide-arr');

    prevButton.addEventListener('click', () => {
        index = (index - 1 + totalSlides) % totalSlides;
        moveSlide(slides, index);
        resetInterval();
    });

    nextButton.addEventListener('click', () => {
        index = (index + 1) % totalSlides;
        moveSlide(slides, index);
        resetInterval();
    });

    indicators.forEach((indicator, i) => {
        indicator.addEventListener('click', () => {
            index = i;
            moveSlide(slides, index);
            resetInterval();
        });
    });

    resetInterval();

    function resetInterval() {
        if (interval_3) clearInterval(interval_3);

        interval_3 = setInterval(() => {
            index = (index + 1) % totalSlides;
            moveSlide(slides, index);
        }, 5000);
    }

    function moveSlide(slides, index) {
        slides.style.transform = `translateX(-${index * 100}%)`;
    }
}

let placeSt = true;
let eventSt = false;

function findPlace() {
    let insert = `<div class="flex space-x-4">
                        <div class="bg-white px-4 py-2 flex-grow rounded-md border border-gray-300">
                            <div class="mb-2 flex justify-between space-x-2">
                                <h3 class="font-bold">면적</h3>
                                <label id="place-range" class="text-center"></label>
                            </div>
                            <div id="place-slider" class="w-full"></div>
                        </div>
                        <div class="bg-white px-4 py-2 flex-shrink-0 border border-gray-300 rounded-md">
                            <label for="location" class="font-bold">지역</label>
                            <select id="location" class="w-full px-1">
                                <option value="">지역</option>
                                <option value="서울">서울</option>
                                <option value="부산">부산</option>
                                <option value="대구">대구</option>
                                <option value="인천">인천</option>
                                <option value="광주">광주</option>
                                <option value="대전">대전</option>
                                <option value="울산">울산</option>
                                <option value="세종">세종</option>
                                <option value="경기">경기</option>
                                <option value="충북">충북</option>
                                <option value="충남">충남</option>
                                <option value="전북">전북</option>
                                <option value="전남">전남</option>
                                <option value="경북">경북</option>
                                <option value="경남">경남</option>
                                <option value="강원">강원</option>
                                <option value="제주">제주</option>
                            </select>
                        </div>
                    </div>
                    <div class="bg-white px-4 py-2 rounded-md border border-gray-300 min-w-72">
                        <div class="mb-2 flex justify-between">
                            <h3 class="font-bold">금액</h3>
                            <label id="price-range" class="text-center"></label>
                        </div>
                        <div id="price-slider" class="w-full"></div>
                    </div>
                    <div class="bg-white min-w-60 border px-4 border-gray-300 rounded-md p-2">
                        <label for="quick-calendar" class="block font-bold">임대 기간</label>
                        <input type="text" id="quick-calendar" placeholder="기간을 선택하세요." class="w-full">
                    </div>

                    <button onclick="locationPlace()" id="find-item" class="py-2 bg-[#3FB8AF] text-white font-bold flex-shrink-0 flex-grow-0 px-4 rounded-md hover:bg-[#2c817c] transition-colors duration-150">
                        조회
                    </button>`;

    if (!placeSt) {
        filterBox.innerHTML = insert;
        createPriceSlider();
        createPlaceSlider();
        createPickCalendar();
        eventSt = false;
        placeSt = true;
        toggle();
    }
}

function findEvent() {
    let insert = `<div class="flex space-x-4">
                            <div class="bg-white px-4 py-2 flex-grow border border-gray-300 rounded-md">
                                <h3 class="font-bold">연령대</h3>
                                <select id="age" class="w-full px-1">
                                    <option value="">전체</option>
                                    <option value="10대">10대</option>
                                    <option value="20대">20대</option>
                                    <option value="30대">30대</option>
                                    <option value="40대">40대</option>
                                    <option value="50대">50대</option>
                                    <option value="60대 이상">60대 이상</option>
                                </select>
                            </div>
                            <div class="bg-white px-4 py-2 flex-shrink-0 border border-gray-300 rounded-md">
                                <label for="location" class="font-bold">지역</label>
                                <select id="location" class="w-full px-1">
                                    <option value="">전체</option>
                                    <option value="서울">서울</option>
                                    <option value="부산">부산</option>
                                    <option value="대구">대구</option>
                                    <option value="인천">인천</option>
                                    <option value="광주">광주</option>
                                    <option value="대전">대전</option>
                                    <option value="울산">울산</option>
                                    <option value="세종">세종</option>
                                    <option value="경기">경기</option>
                                    <option value="충북">충북</option>
                                    <option value="충남">충남</option>
                                    <option value="전북">전북</option>
                                    <option value="전남">전남</option>
                                    <option value="경북">경북</option>
                                    <option value="경남">경남</option>
                                    <option value="강원">강원</option>
                                    <option value="제주">제주</option>
                                </select>
                            </div>
                        </div>
                        <div class="bg-white px-4 py-2 border border-gray-300 rounded-md">
                            <h3 class="font-bold">유형</h3>
                            <select id="category" class="w-full px-1">
                                <option value="">전체</option>
                                <option value="식품">식품</option>
                                <option value="화장품">화장품</option>
                                <option value="패션">패션</option>
                                <option value="애니">애니</option>
                                <option value="아이돌">아이돌</option>
                                <option value="스포츠">스포츠</option>
                                <option value="음악">음악</option>
                                <option value="테크">테크</option>
                                <option value="인테리어">인테리어</option>
                                <option value="문화/예술">문화/예술</option>
                                <option value="게임">게임</option>
                                <option value="헬스">헬스</option>
                                <option value="음료">음료</option>
                                <option value="책">책</option>
                                <option value="친환경">친환경</option>
                            </select>
                        </div>
                        <div class="bg-white min-w-60 border px-4 border-gray-300 rounded-md p-2">
                            <h3 class="font-bold">임대 기간</h3>
                            <input type="text" id="quick-calendar" placeholder="기간을 선택하세요." class="w-full">
                        </div>
                        
                        <button onclick="locationPopup()" id="find-item" class="py-2 bg-[#3FB8AF] text-white font-bold flex-shrink-0 flex-grow-0 px-4 rounded-md hover:bg-[#2c817c] transition-colors duration-150">
                            조회
                        </button>`;
    if(!eventSt) {
        filterBox.innerHTML = insert;
        createPickCalendar();
        placeSt = false;
        eventSt = true;
        toggle();
    }
}

function locationPlace() {
    const loc = document.getElementById('location');
    const period = document.getElementById('quick-calendar').value.split(' ~ ');

    const params = new URLSearchParams();
    if(startArea || startArea === 0) params.append("minCapacity", startArea);
    if(endArea || endArea === 0) params.append("maxCapacity", endArea);
    if(loc.value) params.append("location", loc.value);
    if(startPrice) params.append("minPrice", startPrice);
    if(endPrice) params.append("maxPrice", endPrice);
    if(period[0]) params.append("startDate", period[0]);
    if(period[1]) params.append("endDate", period[1]);

    window.location.href =
        `/rental/list?&${params.toString()}`;
}

function locationPopup() {
    const loc = document.getElementById('location');
    const type = document.getElementById('category');
    const age = document.getElementById('age');
    const period = document.getElementById('quick-calendar').value.split(' ~ ');

    const params = new URLSearchParams();
    if (loc.value) params.append("targetLocation", loc.value);
    if (type.value) params.append("type", type.value);
    if (age.value) params.append("targetAgeGroup", age.value);
    if (period[0]) params.append("startDate", period[0]);
    if (period[1]) params.append("endDate", period[1]);

    window.location.href =
        `/popup/list?${params.toString()}`;

}

function toggle() {
    placeBox.classList.toggle('translate-y-2');
    placeBox.classList.toggle('text-black');
    placeBox.classList.toggle('text-white');
    placeBox.classList.toggle('bg-[#d8d7d7]');
    placeBox.classList.toggle('bg-[#3FB8AF]');
    eventBox.classList.toggle('translate-y-2');
    eventBox.classList.toggle('text-black');
    eventBox.classList.toggle('text-white');
    eventBox.classList.toggle('bg-[#d8d7d7]');
    eventBox.classList.toggle('bg-[#3FB8AF]');
}

function init() {
    const placeBox = document.getElementById('place-box');
    const popupBox = document.getElementById('popup-box');

    fetch('/api/main/new')
        .then(resp => resp.json())
        .then(res => {
            let place = '';

            res.rentalPlace.forEach(item => {
                place += `<div class="snap-center shrink-0 first:pl-8 last:pr-8">
                    <a href="/rental/detail/${item.id}" class="rounded-md flex flex-col p-4 shadow-xl bg-white border hover:transform hover:translate-y-[-10px] transition-transform duration-200">
                        <img class="w-80 h-46 rounded-lg object-contain shadow-xl bg-white" src="/images/place_thumbnail/${item.thumbnail}" alt="">
                        <span class="my-4 font-bold">${truncateString(item.name)}</span>
                        <div class="flex justify-between">
                            <p>${item.price.toLocaleString()}원</p>
                            <p>${item.address.slice(0,2)}</p>
                        </div>
                    </a>
                </div>`
            });

            let popup = '';

            res.popupStore.forEach(item => {
                popup += `<div class="snap-center shrink-0 first:pl-8 last:pr-8">
                    <a href="/popup/detail/${item.id}" class="rounded-md flex flex-col p-4 shadow-xl bg-white border hover:transform hover:translate-y-[-10px] transition-transform duration-200">
                        <img class="w-80 h-46 rounded-lg object-contain shadow-xl bg-white" src="/images/popup_thumbnail/${item.thumbnail}" alt="">
                        <span class="my-4 font-bold">${truncateString(item.title)}</span>
                        <div class="flex justify-between">
                            <p>${item.type}</p>
                            <p>${item.targetLocation}</p>
                        </div>
                    </a>
                </div>`
            });

            placeBox.innerHTML = place;
            popupBox.innerHTML = popup;
        })
}