import { defineStore } from 'pinia';

export const useAreaSliderStore = defineStore('areaSlider', {
	state: () => ({
		min: 0,
		max: 100,
	}),
	actions: {
		setMin(area) {
			this.min = area;
		},
		setMax(area) {
			this.max = area;
		},
	},
});