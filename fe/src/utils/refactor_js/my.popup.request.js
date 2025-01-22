const popupSeq = window.location.pathname.split("/").pop();
const title = document.getElementById("popup-title");
const box = document.getElementById("box");

document.addEventListener('DOMContentLoaded', () => {
    init();
})

function init() {
    fetch(`/api/invitation/${popupSeq}`)
        .then(resp=> resp.json())
        .then(res => {
            box.innerHTML = renderInvitation(res.rentalPlaces);
            title.innerHTML = res.popupTitle;
        })
        .catch(err => console.log(err));
}

function renderInvitation(data) {
    let result = '';
    if (data.length >= 1) {
        data.forEach(item => {
            result += `<a href="/rental/detail/${item.id}" class="p-4 hover:bg-gray-200 transition-colors items-center space-y-4 sm:space-y-0 space-x-0 sm:space-x-4 bg-white flex sm:flex-row flex-col border border-gray-300 rounded-lg">
            <img class="border border-gray-300 min-h-40 max-h-40 w-full sm:max-w-60 object-contain bg-gray-100" src="/images/place_thumbnail/${item.thumbnail}" alt="">
                <div class="flex flex-col space-y-4 w-full">
                    <span class="font-bold text-lg">${item.name}</span>
                    <span class="font-bold text-sm">[${item.zipcode}] ${item.address}, ${item.addrDetail}</span>
                    <span class="font-bold">${item.price.toLocaleString()}원 / 일</span>
                    <span class="w-full text-right text-xs font-bold">클릭하시면 해당 임대지의 상세 페이지로 이동합니다.</span>
                </div>
        </a>`;
        })
    } else {
        result = `<div class="flex justify-center items-center border-b border-gray-300 pb-4">
            <div class="flex items-center gap-2 text-gray-700 font-bold">
                입점 요청이 없습니다.
            </div>
        </div>`
    }

    return result;
}