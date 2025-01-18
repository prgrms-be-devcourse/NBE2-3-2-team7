const urlParams = new URLSearchParams(window.location.search);

const placeSeq = urlParams.get("place");
const start = urlParams.get("start");
const end = urlParams.get("end");

const orderId = generateRandomString();

let infoVal = {};

document.addEventListener('DOMContentLoaded' ,() => {
	init();
});

function init() {
	checkParam()
	fetch(`/api/payment?seq=${placeSeq}&start=${start}&end=${end}`)
		.then(resp => {
			if (!resp.ok) {
				throw new Error(err.message || '이미 예약된 날짜입니다.');
			}
			return resp.json();
		})
		.then(res => {
				setData(res);
		})
		.catch(err => {
			alert('[에러] : ', err.message);
			window.history.back();
		});
}

async function tossApi() {
	stagingPayment();
	const clientKey = 'test_ck_LlDJaYngroyq7XvpoONv3ezGdRpX'
	const customerKey = infoVal.customerKey;
	const tossPayments =  TossPayments(clientKey);
	const payment = tossPayments.payment({
		customerKey,
	});

	const amount = {
		currency: "KRW",
		value: getPeriod(start, end) * infoVal.price,
	};

	const tossInfo = {
		method: "CARD", // 카드 및 간편결제
		amount,
		orderId: orderId,
		orderName: infoVal.placeName,
		successUrl: window.location.origin + "/payment/success", // 결제 요청이 성공하면 리다이렉트되는 URL
		failUrl: window.location.origin + "/payment/fail", // 결제 요청이 실패하면 리다이렉트되는 URL
		customerEmail: infoVal.userEmail,
		customerName: infoVal.userName,
		card: {
			useEscrow: false,
			flowMode: "DEFAULT",
			useCardPoint: false,
			useAppCardOnly: false,
		},
	}

	await payment.requestPayment(tossInfo);
}

function stagingPayment(){
	fetch(`/api/payment`, {
		method: 'POST',
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			"orderId":orderId,
			"rentalPlaceSeq":placeSeq,
			"startDate":start,
			"endDate":end,
			"amount":getPeriod(start, end) * infoVal.price,
		})
	})
		.then(resp => resp.json())
		.then(res => {
			if (res.status === 200) {
				console.log(res.status, ' Ok!');
			} else {
				console.log(res.status, ' No..')
			}
		})
		.catch(err => console.log(err));
}

function checkParam() {
	if (start === '' || end === '' ||
		start === null || end === null ||
		start === undefined || end === undefined) {
		alert('잘못된 접근입니다.');
		window.history.back();
	}
}

function setData(info) {
	document.getElementById('user-name').innerText = info.userName;
	document.getElementById('user-email').innerText = info.userEmail;
	document.getElementById('user-tel').innerText = info.userTel;
	document.getElementById('place-name').innerText = info.placeName;
	document.getElementById('rental-period').innerText = getRangeDate(start, end);
	document.getElementById('day-price').innerText = info.price.toLocaleString() + '원 / 일';
	document.getElementById('total-price').innerText = (getPeriod(start, end) * info.price).toLocaleString() + '원';
	document.getElementById('full-address').innerText = `[${info.area}] ${info.address}, ${info.addrDetail}`;

	infoVal = info;
}

function generateRandomString() {
	return window.btoa(Math.random()).slice(0, 20);
}