import { defineStore } from 'pinia';

export const usePriceSliderStore = defineStore('priceSlider', {
	state: () => ({
		min: 0,
		max: 10000000,
	}),
	actions: {
		setMin(price) {
			this.min = price;
		},
		setMax(price) {
			this.max = price;
		},
	},
});