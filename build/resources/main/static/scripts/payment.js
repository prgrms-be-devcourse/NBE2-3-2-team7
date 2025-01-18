document.addEventListener('DOMContentLoaded' ,() => {
	main();
})

async function main() {
	const button = document.getElementById("payment-button");
	// ------  결제위젯 초기화  ------
	const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
	const tossPayments = TossPayments(clientKey);
	// 회원 결제 - 테스트 키
	const customerKey = "5MYfWyBKczHDQVF1F6xvm";
	const widgets = tossPayments.widgets({
		customerKey,
	});

	// ------ 주문의 결제 금액 설정 ------
	await widgets.setAmount({
		currency: "KRW",
		value: 50000,
	});

	await Promise.all([
		// ------  결제 UI 렌더링 ------
		widgets.renderPaymentMethods({
			selector: "#payment-method",
			variantKey: "DEFAULT",
		}),
		// ------  이용약관 UI 렌더링 ------
		widgets.renderAgreement({ selector: "#agreement", variantKey: "AGREEMENT" }),
	]);


	// ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
	button.addEventListener("click", async function () {
		await widgets.requestPayment({
			orderId: "6qDE_OK0FduW7NtJBeYPl",
			orderName: "토스 티셔츠 외 2건",
			successUrl: window.location.origin + "/success.html",
			failUrl: window.location.origin + "/fail.html",
			customerEmail: "customer123@gmail.com",
			customerName: "김토스",
			customerMobilePhone: "01012341234",
		});
	});
}