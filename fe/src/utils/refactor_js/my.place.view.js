const placeSeq = window.location.pathname.split('/').pop();

const thumbnailBox = document.getElementById('thumbnail-image');
const nameInput = document.getElementById('place-title');
const priceInput = document.getElementById('price');
const zipcodeInput = document.getElementById('postcode');
const addressInput = document.getElementById('addr');
const addrDetailInput = document.getElementById('addr-detail');
const infraBox = document.getElementById('my-infra-box');
const ageInput = document.getElementById('age');
const areaInput = document.getElementById('area');
const descriptionInput = document.getElementById('detail-text');

const editForm = document.getElementById('edit-place-form');

document.addEventListener('DOMContentLoaded', () => {
	init();
});

editForm.addEventListener('submit', (e) => {
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
		"name": nameInput.value,
		"price": priceInput.value,
		"zipcode": zipcodeInput.value,
		"address": addressInput.value,
		"addrDetail": addrDetailInput.value,
		"infra": infra,
		"nearbyAgeGroup": ageInput.value,
		"area": areaInput.value,
		"description": descriptionInput.value,
	}

	formData.append("rentalPlace", new Blob([JSON.stringify(rentalPlace)], { type: "application/json" }));
	if (currentThumbnail) formData.append("thumbnail", currentThumbnail);
	currentImages.forEach(image => formData.append("images", image))

	fetch(`/api/rental/${placeSeq}`, {
		method: 'PUT',
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
})

function init() {
	fetch(`/api/rental/${placeSeq}`)
		.then(resp => resp.json())
		.then(res => {
			renderPlace(res.rentalPlace);
			getOriginalImages(res.images).then(() => renderImages());
		})
		.catch (err => console.log(err));
}

function renderPlace(data) {
	thumbnailBox.src = `/images/place_thumbnail/${data.thumbnail}`;
	nameInput.value = data.name;
	priceInput.value = data.price;
	zipcodeInput.value = data.zipcode;
	addressInput.value = data.address;
	addrDetailInput.value = data.addrDetail;
	ageInput.value = data.nearbyAgeGroup;
	areaInput.value = data.area;
	descriptionInput.value = data.description;
	infraList = data.infra.split(',');
	fetch(`/images/place_thumbnail/${data.thumbnail}`)
		.then(resp => resp.blob())
		.then(blob => currentThumbnail = new File([blob], data.thumbnail, { type: blob.type }))
		.catch(err => console.log(err));
	renderInfra();
}

async function getOriginalImages(images) {
	// 모든 이미지 로드가 완료될 때까지 기다림
	const imagePromises = images.map(async item => {
		const resp = await fetch(`/images/place_detail/${item.image}`);
		const blob = await resp.blob();
		const file = new File([blob], item.image, { type: blob.type });
		currentImages.push(file);
	});

	await Promise.all(imagePromises);
}

let infraList = [];

function addInfra(btn) {
	const val = btn.dataset.val;
	if (!infraList.includes(val))	{
		infraList.push(val);
		renderInfra();
	}
}

function renderInfra() {
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

function deleteRentalPlace() {
	fetch(`/api/rental/${placeSeq}`, {
		method: 'DELETE'
	})
		.catch(err => {
			alert('삭제중 에러가 발생했습니다.');
			console.log(err)
		})
		.finally(() => {
			window.location.href = '/mypage/rental';
		});
}

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