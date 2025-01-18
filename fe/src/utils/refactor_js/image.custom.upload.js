const realThumbnailUpload = document.getElementById('thumbnail-real-upload');
const realImagesUpload = document.getElementById('images-real-upload');
const imagesBox = document.getElementById('image-list-box');
const imagesCountLabel = document.getElementById('images-count');
let currentThumbnail = null;
let currentImages = [];

document.addEventListener('DOMContentLoaded', () => {
	realThumbnailUpload.addEventListener('change', (e) => getThumbnail(e));
	realImagesUpload.addEventListener('change', (e) => getImages(e));
});

function uploadThumbnail() {
	realThumbnailUpload.click();
}

function uploadImages() {
	realImagesUpload.click();
}

function getThumbnail(e) {
	const file = e.currentTarget.files[0];

	let limit = 1024 ** 2 * 2;

	if(file) {
		if(!file.type.match("image/.*")) {
			alert('이미지 파일만 업로드가 가능합니다.');
			return;
		}
		if(file.size > limit) {
			alert('이미지의 크기를 2MB 이하로 업로드 해주시기 바랍니다.');
			return;
		}
		currentThumbnail = file;

		const reader = new FileReader();
		reader.onload = (e) => {
			const imgBox = document.getElementById('thumbnail-image');

			imgBox.setAttribute('src', e.target.result);
			imgBox.setAttribute('data-file', file.name);
		};
		reader.readAsDataURL(file);
	}
}

function getImages(e) {
	const files = e.currentTarget.files;

	let limit = 1024 ** 2 * 2;

	if(files.length > 10) {
		alert('이미지는 최대 10개까지 등록할 수 있습니다.')
		return;
	}

	[...files].forEach(file => {
		if(!file.type.match("image/.*")) {
			alert('이미지 파일만 업로드가 가능합니다.');
			return;
		}

		if(file.size > limit) {
			alert('이미지의 크기를 2MB 이하로 업로드 해주시기 바랍니다.');
			return;
		}

		if(currentImages.length >= 10) {
			alert('이미지는 최대 10개까지 등록할 수 있습니다.')
			return;
		}
		currentImages.push(file);
	});

	renderImages();
	realImagesUpload.value = '';
}

function removeImage(index) {
	currentImages.splice(index, 1);
	renderImages();
}

function renderImages() {
	imagesBox.innerHTML = '';

	currentImages.forEach((file, index) => {
		const reader = new FileReader();

		reader.onload = e => {
			const li = document.createElement("li");
			li.className = "flex items-center space-x-4 px-8 py-2 hover:bg-gray-200";
			li.innerHTML = `<img src="${e.target.result}" class="w-20 h-20 border border-gray-300 object-contain bg-gray-300" alt="">
								<span class="text-xs font-bold flex-1">${file.name}</span>
								<button type="button" onclick="removeImage(${index})">&#10060;</button>`;
			imagesBox.appendChild(li);
			
		}
		reader.readAsDataURL(file);
	});
	imagesCountLabel.innerHTML = currentImages.length + '개';
}