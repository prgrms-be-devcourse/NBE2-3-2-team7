const box = document.getElementById('item-box');

document.addEventListener('DOMContentLoaded', () => {
    init();
})

function init() {
    fetch('/api/popup/user', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${1234}`,
        }
    })
        .then(resp => resp.json())
        .then(res => {
            let result = '';

            if (res.length >= 1) {
                res.forEach(item => {
                    result += `<div class="relative p-4 border m-2 rounded-lg border-gray-300">
                        <a class="sm:h-64 max-h-80 group mt-2 block relative overflow-hidden rounded-lg border border-gray-400 justify-center flex"
                           href="/mypage/popup/view/${item.id}">
                            <div class="absolute w-full h-full bg-gray-300 opacity-0 group-hover:opacity-50 transition left-0 top-0"></div>
                            <img class="w-full object-contain bg-gray-100"
                                 src="/images/popup_thumbnail/${item.thumbnail}" alt=""/>
                            <div class="absolute bottom-1 end-1 opacity-0 group-hover:opacity-100 transition">
                                <div class="flex items-center z-10 gap-x-1 py-1 px-2 bg-white border border-gray-200 text-gray-800 rounded-lg">
                                    <svg class="shrink-0 size-3" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <circle cx="11" cy="11" r="8"/>
                                        <path d="m21 21-4.3-4.3"/>
                                    </svg>
                                    <span class="text-xs">상세보기</span>
                                </div>
                            </div>
                        </a>
                        <div class="space-y-2 mt-4 flex flex-col">
                            <h3 class="text-lg font-bold text-gray-900 hover:text-[#3FB8AF] transition-colors"><a
                                href="/mypage/popup/view/${item.id}">${item.title}</a></h3>
                            <span>${item.type}</span>
                            <a href="/mypage/popup/invitation/${item.id}" class="hover:text-[#3FB8AF] transition-colors">입점 요청 확인</a>
                        </div>
                    </div>`
                })
                box.innerHTML = result;
            } else {
                box.innerHTML = `<div class="sm:col-span-2 flex justify-center items-center border-b border-gray-300 pb-4">
                    <div class="flex items-center gap-2 text-gray-700 font-bold">
                        등록된 팝업이 없습니다.
                    </div>
                </div>`;
            }

        })
}