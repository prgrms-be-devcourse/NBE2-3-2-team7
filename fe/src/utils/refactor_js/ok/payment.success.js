const urlParams = new URLSearchParams(window.location.search);

const paymentKey = urlParams.get("paymentKey");
const orderId = urlParams.get("orderId");
const amount = urlParams.get("amount");

document.addEventListener("DOMContentLoaded", () => {
    init();
})

function init() {
    fetch('/api/payment/success', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            paymentKey,
            orderId,
            amount
        })
    })
        .then(resp => resp.json())
        .then(res => {
            res.data = JSON.parse(res.data);
            if (res.status !== 200) {
                window.location.href = '/main';
            }
        })
        .catch(err => console.log(err));
}