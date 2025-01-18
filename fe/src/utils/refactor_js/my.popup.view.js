const popupSeq = window.location.pathname.split('/').pop();

const thumbnailBox = document.getElementById('thumbnail-image');
const titleInput = document.getElementById('popup-title');
const categoryInput = document.getElementById('category');
const locationInput = document.getElementById('location');
const periodInput = document.getElementById('quick-calendar');
const ageInput = document.getElementById('age');
const areaRangeInput = document.getElementById('place-range');
const descriptionInput = document.getElementById('detail-text');
const editForm = document.getElementById('edit-popup-form');

document.addEventListener('DOMContentLoaded', () => {
	init();
});

editForm.addEventListener('submit', (e) => {
	e.preventDefault();

	if(currentImages.length < 1){
		alert('이미지를 1개 이상 선택해주세요.');
		return;
	}

	let formData = new FormData();

	let dateSplit = periodInput.value.split(' ~ ');

	let popupStore = {
		"title": titleInput.value,
		"type": categoryInput.value,
		"targetAgeGroup": ageInput.value,
		"targetLocation": locationInput.value,
		"wishArea": areaRangeInput.innerHTML,
		"description": descriptionInput.value,
		"startDate": dateSplit[0],
		"endDate": dateSplit[1],
	}

	formData.append("popupStore", new Blob([JSON.stringify(popupStore)], { type: "application/json" }));
	if (currentThumbnail) formData.append("thumbnail", currentThumbnail);
	currentImages.forEach(image => formData.append("images", image))

	fetch(`/api/popup/${popupSeq}`, {
		method: 'PUT',
		body: formData
	})
		.then(resp => {
			if (resp.ok) {
				window.location.href = '/mypage/popup';
			}
		})
		.catch(err => {
			console.log(err);
			window.location.href = '/mypage/popup';
		});
})

function init() {
	createPickCalendar();

	fetch(`/api/popup/${popupSeq}`)
		.then(resp=> resp.json())
		.then(res => {
			console.log(res);
			renderPopup(res.popupStore);
			getOriginalImages(res.images).then(() => renderImages())
		})
		.catch(err => console.log(err));
}

function renderPopup(data) {
	thumbnailBox.src = `/images/popup_thumbnail/${data.thumbnail}`;
	titleInput.value = data.title;
	categoryInput.value = data.type;
	locationInput.value = data.targetLocation;
	periodInput.value = `${data.startDate} ~ ${data.endDate}`
	ageInput.value = data.targetAgeGroup;
	let placeArray = data.wishArea.split(' ~ ');
	placeArray = placeArray.map(item => Number(item.replace(/평\s?(이상)?/, '')));
	createPlaceSlider(placeArray[0], placeArray[1]);
	descriptionInput.value = data.description;

	fetch(`/images/popup_thumbnail/${data.thumbnail}`)
		.then(resp => resp.blob())
		.then(blob => currentThumbnail = new File([blob], data.thumbnail, { type: blob.type }))
		.catch(err => console.log(err));
}

async function getOriginalImages(images) {
	// 모든 이미지 로드가 완료될 때까지 기다림
	const imagePromises = images.map(async item => {
		const resp = await fetch(`/images/popup_detail/${item}`);
		const blob = await resp.blob();
		const file = new File([blob], item, { type: blob.type });
		currentImages.push(file);
	});

	await Promise.all(imagePromises);
}

function deletePopupStore() {
	fetch(`/api/popup/${popupSeq}`, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json',
		}
	})
		.then(resp=> {
			if (resp.ok) {
				window.location.href = '/mypage/popup';
			}
		})
		.catch(err => {
			console.log(err)
			window.location.href = '/mypage/popup';
		});
}