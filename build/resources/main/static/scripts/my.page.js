const realUpload = document.getElementById('profile-real-upload');
let currentProfile = null;

document.addEventListener('DOMContentLoaded', () => {
	realUpload.addEventListener('change', (e) => getImageFiles(e));
});

function uploadImage() {
	realUpload.click();
}

function getImageFiles(e) {
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
		currentProfile = file;

		const reader = new FileReader();
		reader.onload = (e) => {
			const imgBox = document.getElementById('profile-image');

			imgBox.setAttribute('src', e.target.result);
			imgBox.setAttribute('data-file', file.name);
		};
		reader.readAsDataURL(file);
	}
}