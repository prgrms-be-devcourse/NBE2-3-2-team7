
document.addEventListener('DOMContentLoaded', () => {

});

let infraList = [];

function addInfra(btn) {
	const val = btn.dataset.val;
	if (!infraList.includes(val))	{
		infraList.push(val);
		console.log(infraList);
		renderInfra();
	} else {
		console.log('no add : ' , val)
		return
	}
}

function renderInfra() {
	const infraBox = document.getElementById('my-infra-box');

	let inner = '';
	infraList.forEach((val, index)=> {
		inner += `<li class="text-xs px-3 py-2 flex hover:border-red-500 transition-colors items-center border-2 border-[#3fb8af] rounded-full space-x-2">
									<span class="font-bold">${val}</span>
									<button type="button" onclick="removeInfra(${index})">&#10060;</button>
								</li>`;
	})
	infraBox.innerHTML = inner;
}

function removeInfra(index) {
	infraList.splice(index, 1);
	renderInfra();
}