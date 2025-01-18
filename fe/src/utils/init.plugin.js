import flatpickr from 'flatpickr';
import 'flatpickr/dist/flatpickr.min.css';

import noUiSlider from 'nouislider';
import 'nouislider/dist/nouislider.min.css';

const getTomorrow = () => {
	const today = new Date();

	const tomorrow = new Date(today);
	tomorrow.setDate(today.getDate() + 1);

	const year = tomorrow.getFullYear();
	const month = tomorrow.getMonth() + 1;
	const day = tomorrow.getDate();

	return `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;
}

let calendarInstance = null;

export const initFlatpickr = (range = []) => {
	if (calendarInstance) calendarInstance.destroy();
	
	calendarInstance = flatpickr('#date-range', {
		mode: "range",
		dateFormat: "Y-m-d",
		minDate: getTomorrow(),
		disable: range,
		locale: {
			rangeSeparator: " ~ ",
			weekdays: {
				shorthand: ["일", "월", "화", "수", "목", "금", "토"],
				longhand: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
			},
			months: {
				shorthand: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
				longhand: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
			},
		}
	});
	
};

export function initPriceSlider(minPrice, maxPrice, store) {
	const slider = document.getElementById('price-slider');
	noUiSlider.create(slider, {
		start: [minPrice.value, maxPrice.value],
		connect: true,
		range: {
			min: 0,
			max: 10000000
		},
		format: {
			to: function (value) {
				if (value >= 10000000) return Math.round(value / 10000) + '만원 이상';
				return Math.round(value / 10000) + '만원';
			},
			from: function (value) {
				return Number(value.replace(/만원\s?(이상)?/, '')) * 10000;
			}
		}
	});

	const priceRange = document.getElementById('price-range');
	slider.noUiSlider.on('update', function (values) {
		store.setMin(Number(values[0].replace(/만원\s?(이상)?/, '0000')));
		store.setMax(Number(values[1].replace(/만원\s?(이상)?/, '0000')));
		priceRange.innerText = values.join(' ~ ');
	});
}

export function initAreaSlider(minArea, maxArea, store) {
	const slider = document.getElementById('area-slider');
	noUiSlider.create(slider, {
		start: [minArea.value, maxArea.value],
		connect: true,
		range: {
			min: 0,
			max: 100
		},
		format: {
			to: function (value) {
				if (value >= 100) return Math.round(value) + '평 이상';
				return Math.round(value) + '평';
			},
			from: function (value) {
				return Number(value.replace(/평\s?(이상)?/, ''));
			}
		}
	});

	const areaRange = document.getElementById('area-range');
	slider.noUiSlider.on('update', function (values) {
		store.setMin(Number(values[0].replace(/평\s?(이상)?/, '')));
		store.setMax(Number(values[1].replace(/평\s?(이상)?/, '')));
		areaRange.innerText = values.join(' ~ ');
	});
}
