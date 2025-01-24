window.addEventListener('DOMContentLoaded', ()=> {
	slideImage();
})

function slideImage() {
	const slides = document.getElementById('image-container');
	const slideItems = document.querySelectorAll('#image-container > div');

	let index = 0;

	const totalSlides = slideItems.length;

	const prevButton = document.getElementById('prev-img-btn');
	const nextButton = document.getElementById('next-img-btn');

	const num = document.getElementById('slide-img-num');
	const total = document.getElementById('slide-img-total');
	num.innerHTML = 1;
	total.innerHTML = totalSlides;

	prevButton.addEventListener('click', () => {
			index = (index - 1 + totalSlides) % totalSlides;
			num.innerHTML = index+1;
			moveSlide(slides, index);
	});

	nextButton.addEventListener('click', () => {
			index = (index + 1) % totalSlides;
			num.innerHTML = index+1;
			moveSlide(slides, index);
	});

	function moveSlide(slides, index) {
		slides.style.transform = `translateX(-${index * 100}%)`;
	}
}
