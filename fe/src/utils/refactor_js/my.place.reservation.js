const placeSeq = window.location.pathname.split('/').pop();

const placeName = document.getElementById('rental-title');
const box = document.getElementById('place-box');

document.addEventListener("DOMContentLoaded", () => {
    init();
})

function init() {
    fetch(`/api/reservation/${placeSeq}`)
        .then(res => res.json())
        .then(res => {
            placeName.innerHTML = res.rentalPlaceName
            box.innerHTML = renderReservation(res.reservation)
        })
        .catch(err => console.log(err));
}

function renderReservation(data) {
    let result = '';

    if (data.length >= 1) {
        data.forEach((item) => {
            switch (item.reservationStatus) {
                case '결제 완료':
                    result += `<div class="p-4 justify-between space-y-2 transition-colors hover:border-gray-700 bg-white flex flex-col border border-gray-300 rounded-lg">
                            <div class="flex justify-start">
                                <span class="font-bold text-sm">${item.reservationStatus}</span>
                            </div>
                            <div class="flex justify-between sm:flex-row flex-col">
                                <span class="font-bold sm:mt-0 mt-2">예약자 이름 : ${item.reservationUserName}</span>
                                <span class="font-bold text-sm  sm:mt-0 mt-4">${item.price.toLocaleString()}원 / 일</span>
                            </div>
                            <div class="flex justify-between flex-col-reverse sm:flex-row">
                                <span class="font-bold text-lg mt-2 sm:mt-0">${getRangeDate(item.startDate,item.endDate)} (${getPeriod(item.startDate,item.endDate)}일)</span>
                                <span class="font-bold text-lg">총 금액 ${item.totalAmount.toLocaleString()}원</span>
                            </div>
                        </div>`
                    break;
                case '임대 완료':
                    result += `<div class="p-4 justify-between space-y-2 transition-colors hover:bg-blue-300 bg-white flex flex-col border border-gray-300 rounded-lg">
                            <div class="flex justify-start">
                                <span class="font-bold text-sm">${item.reservationStatus}</span>
                            </div>
                            <div class="flex justify-between sm:flex-row flex-col">
                                <span class="font-bold sm:mt-0 mt-2">예약자 이름 : ${item.reservationUserName}</span>
                                <span class="font-bold text-sm  sm:mt-0 mt-4">${item.price.toLocaleString()}원 / 일</span>
                            </div>
                            <div class="flex justify-between flex-col-reverse sm:flex-row">
                                <span class="font-bold text-lg mt-2 sm:mt-0">${getRangeDate(item.startDate,item.endDate)} (${getPeriod(item.startDate,item.endDate)}일)</span>
                                <span class="font-bold text-lg">총 금액 ${item.totalAmount.toLocaleString()}원</span>
                            </div>
                        </div>`
                    break;
                case '환불 완료':
                    result += `<div class="p-4 justify-between space-y-2 transition-colors hover:border-red-500 bg-gray-200 flex flex-col border border-gray-300 rounded-lg">
                            <div class="flex justify-start">
                                <span class="font-bold text-sm">${item.reservationStatus}</span>
                            </div>
                            <div class="flex justify-between sm:flex-row flex-col">
                                <span class="font-bold sm:mt-0 mt-2">예약자 이름 : ${item.reservationUserName}</span>
                                <span class="font-bold text-sm  sm:mt-0 mt-4">${item.price.toLocaleString()}원 / 일</span>
                            </div>
                            <div class="flex justify-between flex-col-reverse sm:flex-row">
                                <span class="font-bold text-lg mt-2 sm:mt-0">${getRangeDate(item.startDate,item.endDate)} (${getPeriod(item.startDate,item.endDate)}일)</span>
                                <span class="font-bold text-lg">총 금액 ${item.totalAmount.toLocaleString()}원</span>
                            </div>
                        </div>`
                    break;
            }
        })
    } else {
        result = `<div class="flex justify-center items-center border-b border-gray-300 pb-4">
            <div class="flex items-center gap-2 text-gray-700 font-bold">
                예약 내역이 없습니다.
            </div>
        </div>`
    }

    return result;
}