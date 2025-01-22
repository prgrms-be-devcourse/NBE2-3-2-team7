const urlParams = new URLSearchParams(window.location.search);

const orderId = urlParams.get("orderId");

document.addEventListener("DOMContentLoaded", () => {
    init()
})

function init() {
    fetch('/api/payment/fail', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            orderId,
        })
    })
        .then(resp => resp.json())
        .then(res => {
            res.data = JSON.parse(res.data);
        })
        .catch(err => console.log(err));
}