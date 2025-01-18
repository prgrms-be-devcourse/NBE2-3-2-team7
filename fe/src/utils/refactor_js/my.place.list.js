const box = document.getElementById("my-places-box");

document.addEventListener('DOMContentLoaded', () => {
    init()
})

function init() {
    fetch('/api/rental/user')
        .then(resp => resp.json())
        .then(res => {
            box.innerHTML = renderPlace(res)

            document.getElementsByName('status-switch').forEach(status => {
                status.addEventListener('change', (e) => {
                    let flag = e.target.checked ? 'ACTIVE' : 'INACTIVE';
                    fetch(`/api/rental/${status.dataset.seq}`, {
                        method: "PATCH",
                        headers: { 'content-type': 'text/plain' },
                        body: flag
                    })
                        .then(resp => resp.ok ? console.log('ok') : console.log('error'))
                        .catch(err => console.log(err))
                })
            })
        })
        .catch(err => console.log(err));
}

function renderPlace(data) {
    let result = '';
    if (data.length >= 1) {
        console.log(data);
        data.forEach((item) => {
            result += `<div class="relative p-4 border m-2 rounded-lg border-gray-300">
            <a class="sm:h-64 max-h-80 group mt-2 block relative overflow-hidden rounded-lg border border-gray-400 justify-center flex" href="/mypage/rental/view/${item.id}">
                <div class="absolute w-full h-full bg-gray-300 opacity-0 group-hover:opacity-50 transition left-0 top-0"></div>
                <img class="w-full object-contain bg-gray-100" src="/images/place_thumbnail/${item.thumbnail}" alt="">
                <div class="absolute bottom-1 end-1 opacity-0 group-hover:opacity-100 transition">
                    <div class="flex items-center z-10 gap-x-1 py-1 px-2 bg-white border border-gray-200 text-gray-800 rounded-lg">
                        <svg class="shrink-0 size-3" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
                        <span class="text-xs">상세보기</span>
                    </div>
                </div>
            </a>
            <div class="space-y-2 mt-4 flex flex-col">
                <div class="flex justify-between">
                    <h3 class="text-lg font-bold text-gray-900 hover:text-[#3FB8AF] transition-colors"><a href="/mypage/rental/view/${item.id}">${item.name}</a></h3>
                    <label class="switch-label">
                        <span class="font-bold text-xs">등록 상태</span>`
            if (item.status.toLowerCase() === 'active') {
                result += `<input role="switch" type="checkbox" name="status-switch" data-seq="${item.id}" class="transition-colors" checked/>`
            } else {
                result += `<input role="switch" type="checkbox" name="status-switch" data-seq="${item.id}" class="transition-colors"/>`
            }
                result += `</label>
                    </div>
                <span>[${item.zipcode}] ${item.address}, ${item.addrDetail}</span>
                <a href="/mypage/rental/reservation/${item.id}" class="hover:text-[#3FB8AF] transition-colors">예약 현황 보기</a>
            </div>
        </div>`
        })
    } else {
        result = `<div class="flex sm:col-span-2 justify-center border-b border-gray-300 pb-4 items-center">
            <div class="flex items-center gap-2 text-gray-700 font-bold">
                등록된 임대지가 없습니다.
            </div>
        </div>`
    }

    return result;
}

