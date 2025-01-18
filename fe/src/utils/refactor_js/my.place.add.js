const titleInput = document.getElementById('place-title');
const priceInput = document.getElementById('price');
const zipcodeInput = document.getElementById('postcode');
const addrInput = document.getElementById('addr');
const addrDetailInput = document.getElementById('addr-detail');
const ageInput = document.getElementById('age');
const areaInput = document.getElementById('area');
const descriptionInput = document.getElementById('detail-text');
const addForm = document.getElementById('add-popup-form');

document.addEventListener('DOMContentLoaded', () => {

});

let infraList = [];

function addInfra(btn) {
	const val = btn.dataset.val;
	if (!infraList.includes(val))	{
		infraList.push(val);
		console.log(infraList);
		renderInfra();
	}
}

function renderInfra() {
	const infraBox = document.getElementById('my-infra-box');

	let inner = '';
	infraList.forEach((val, index)=> {
		inner += `<li class="text-xs px-3 py-2 flex hover:border-red-500 transition-colors items-center border-2 border-[#3fb8af] rounded-full space-x-2">
				<span class="font-bold">${val}</span>
				<button type="button" onclick="removeInfra(${index})">&#10060;</button>
			</li>`;
	})
	infraBox.innerHTML = inner;
}

function removeInfra(index) {
	infraList.splice(index, 1);
	renderInfra();
}

addForm.addEventListener('submit', function(e) {
	e.preventDefault();

	if(currentImages.length < 1){
		alert('이미지를 1개 이상 선택해주세요.');
		return;
	}

	if (Number(priceInput.value) < 10000) {
		alert('10,000원 이상으로 등록해주세요.');
	} else if (Number(priceInput.value) > 10000000) {
		alert('10,000,000원 이하로 등록해주세요.');
	}

	if (Number(areaInput.value) > 500) {
		alert('500평 이하로 등록해주세요.');
	}


	let formData = new FormData();

	let infra = infraList.join(',');

	let rentalPlace = {
		"name": titleInput.value,
		"price": priceInput.value,
		"zipcode": zipcodeInput.value,
		"address": addrInput.value,
		"addrDetail": addrDetailInput.value,
		"infra": infra,
		"nearbyAgeGroup": ageInput.value,
		"area": areaInput.value,
		"description": descriptionInput.value,
	}

	formData.append("rentalPlace", new Blob([JSON.stringify(rentalPlace)], { type: "application/json" }));
	if (currentThumbnail) formData.append("thumbnail", currentThumbnail);
	currentImages.forEach(image => formData.append("images", image))

	fetch('/api/rental', {
		method: 'POST',
		body: formData
	})
		.then(resp => {
			if (resp.ok) {
				window.location.href = '/mypage/rental'
			}
		})
		.catch(err => {
			console.log(err)
			window.location.href = '/mypage/rental'
		});
});

priceInput.addEventListener('input', function(e) {
	// 숫자 외의 문자가 입력되면 자동으로 삭제
	if (Number(e.target.value) > 10000000) {
		e.target.value = "10000000";
	}
	e.target.value = e.target.value.replace(/[^0-9]/g, "");
});

areaInput.addEventListener('input', function(e) {
	if (Number(e.target.value) > 500) {
		e.target.value = "500";
	}
	e.target.value = e.target.value.replace(/[^0-9]/g, "");
})