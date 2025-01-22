const titleInput = document.getElementById('popup-title');
const categoryInput = document.getElementById('category');
const locationInput = document.getElementById('location');
const periodInput = document.getElementById('quick-calendar');
const ageInput = document.getElementById('age');
const areaRangeInput = document.getElementById('place-range');
const descriptionInput = document.getElementById('detail-text');
const addForm = document.getElementById('add-popup-form');

document.addEventListener('DOMContentLoaded', () => {
	createPickCalendar();
	createPlaceSlider();
});

addForm.addEventListener('submit', (e) => {
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

	fetch(`/api/popup`, {
		method: 'POST',
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