document.addEventListener('DOMContentLoaded', () => {
	const realUpload = document.getElementById('profile-real-upload');
	if (realUpload) {
		realUpload.addEventListener('change', (e) => getImageFiles(e));
	}

	const form = document.getElementById('edit-user-form');
	if (form) {
		form.addEventListener('submit', handleSubmit);
	}

	const profileImage = document.getElementById('profile-image');
	if (profileImage) {
		profileImage.onerror = function() {
			console.log('Initial image load failed, using default image');
			this.src = '/images/user_brand/brand_default.png';
		};
	}

	loadUserInfo();

	const deleteAccountBtn = document.getElementById('delete-account-btn');
	if (deleteAccountBtn) {
		deleteAccountBtn.addEventListener('click', handleDeleteAccount);
	}
});

async function loadUserInfo() {
	const token = TokenUtil.getToken();
	if (!token) {
		window.location.href = '/login';
		return;
	}

	try {
		const response = await fetch('/api/user', {
			headers: {
				'Authorization': `Bearer ${token}`
			}
		});

		if (!response.ok) {
			throw new Error('Failed to load user info');
		}

		const user = await response.json();
		console.log('User info loaded:', user); // 디버깅용

		// 사용자 정보 표시
		document.getElementById('user-email').textContent = user.email || '';
		document.getElementById('user-tel').textContent = user.tel || '';
		document.getElementById('user-name').textContent = user.name || '';
		document.getElementById('brand').value = user.brand || '';

		// 프로필 이미지 설정
		const profileImage = document.getElementById('profile-image');
		if (profileImage) {
			console.log('Current profile image:', user.profileImage); // 디버깅용

			if (user.profileImage && user.profileImage !== 'brand_default.png') {
				const imagePath = `/images/user_brand/${user.profileImage}`;
				console.log('Setting image path to:', imagePath); // 디버깅용
				profileImage.src = imagePath;
			} else {
				console.log('Using default image'); // 디버깅용
				profileImage.src = '/images/user_brand/brand_default.png';
			}
		}
	} catch (error) {
		console.error('Error loading user info:', error);
		window.location.href = '/login';
	}
}

function uploadImage() {
	const realUpload = document.getElementById('profile-real-upload');
	realUpload.click();
}

function getImageFiles(e) {
	const file = e.target.files[0];
	const profileImage = document.getElementById('profile-image');

	if (file) {
		if (file.size > 2 * 1024 * 1024) {
			alert('2MB 이하의 이미지만 업로드 가능합니다.');
			e.target.value = '';
			return;
		}

		const reader = new FileReader();
		reader.onload = (e) => {
			profileImage.src = e.target.result;
		};
		reader.readAsDataURL(file);
	}
}

async function handleSubmit(e) {
	e.preventDefault();

	const token = TokenUtil.getToken();
	if (!token) {
		window.location.href = '/login';
		return;
	}

	const password = document.getElementById('password').value;
	const passwordCheck = document.getElementById('password-check').value;
	const brand = document.getElementById('brand').value;

	if (password && password !== passwordCheck) {
		alert('비밀번호가 일치하지 않습니다.');
		return;
	}

	const formData = new FormData();
	const updateRequest = {
		password: password || null,
		brand: brand || null
	};

	formData.append('userUpdateRequest', new Blob([JSON.stringify(updateRequest)], {
		type: 'application/json'
	}));

	const profileImage = document.getElementById('profile-real-upload').files[0];
	if (profileImage) {
		formData.append('profileImage', profileImage);
	}

	try {
		console.log('Submitting update request...'); // 디버깅용
		const response = await fetch('/api/updateUser', {
			method: 'PUT',
			headers: {
				'Authorization': `Bearer ${token}`
			},
			body: formData
		});

		if (!response.ok) {
			const errorData = await response.text();
			throw new Error(errorData);
		}

		console.log('Update successful, reloading...'); // 디버깅용
		alert('회원 정보가 수정되었습니다.');
		await loadUserInfo(); // 페이지 새로고침 대신 정보만 다시 로드
	} catch (error) {
		console.error('Error updating user info:', error);
		alert('회원 정보 수정에 실패했습니다: ' + error.message);
	}
}

async function handleDeleteAccount() {
	// 사용자 확인
	const isConfirmed = confirm('정말로 회원을 탈퇴 하시겠습니까?');
	if (!isConfirmed) return;

	const token = TokenUtil.getToken();
	if (!token) {
		window.location.href = '/login';
		return;
	}

	try {
		const response = await fetch('/api/deleteUser', {
			method: 'DELETE',
			headers: {
				'Authorization': `Bearer ${token}`
			}
		});

		if (!response.ok) {
			throw new Error('회원 탈퇴에 실패했습니다.');
		}

		alert('회원 탈퇴가 완료되었습니다.');
		// 로컬 스토리지의 토큰 삭제
		TokenUtil.removeToken();
		// 로그인 페이지로 리다이렉트
		window.location.href = '/login';
	} catch (error) {
		console.error('Error deleting account:', error);
		alert(error.message);
	}
}
