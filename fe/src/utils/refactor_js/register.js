const urlParams = new URLSearchParams(window.location.search);

const emailParam = urlParams.get("email");
const nameParam = urlParams.get("name");

if (nameParam) {
    document.getElementById('name').value = decodeURIComponent(nameParam);
    document.getElementById('name').readOnly = true;
}

if (emailParam) {
    document.getElementById('email').value = decodeURIComponent(emailParam);
    document.getElementById('email').readOnly = true;
}

document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');

    form.addEventListener('submit', async function(e) {
        e.preventDefault();

        const formData = {
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
            name: document.getElementById('name').value,
            brand: document.getElementById('brand').value,
            tel: document.getElementById('tel').value
        };

        try {
            const response = await fetch('/api/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                const data = await response.json(); // JSON 응답을 파싱

                // 토큰 저장
                if (data.token) {
                    TokenUtil.setToken(data.token);
                }

                // 메인 페이지로 이동
                window.location.href = data.redirectUrl || '/main';
            } else {
                const error = await response.text();
                alert('회원가입 실패: ' + error);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('서버 오류가 발생했습니다.');
        }
    });
});

document.getElementById('tel').addEventListener('input', function(e) {
    let input = e.target.value;

    input = input.replace(/\D/g, '');

    if (input.length <= 3) {
        input = input.replace(/(\d{1,3})/, '$1');
    } else if (input.length <= 7) {
        input = input.replace(/(\d{3})(\d{1,4})/, '$1-$2');
    } else {
        input = input.replace(/(\d{3})(\d{4})(\d{1,4})/, '$1-$2-$3');
    }

    e.target.value = input;
})