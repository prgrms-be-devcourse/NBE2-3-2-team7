import { defineStore } from 'pinia';

export const countStore = defineStore('count', {
	state: () => ({
		count: 0
	}),
	actions: {
		increment() {
			this.count++;
		}
	},
	getters: {
		get() {
			return this.count;
		}
	}
});