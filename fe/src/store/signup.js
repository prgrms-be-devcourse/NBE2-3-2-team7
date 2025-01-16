import { defineStore } from 'pinia';

const ENUM_ROLE = {
	"customer" : "customer",
	"landlord" : "landlord"
}

export const useSignupStore = defineStore('signup', {
	state: () => ({
		role: null,
		social: false,
		name: null,
	}),
	actions: {
		setCustomer() {
			this.role = ENUM_ROLE.customer;
		},
		setLandlord() {
			this.role = ENUM_ROLE.landlord;
		},
		useSocial() {
			this.social = true;
		},
		useEmail() {
			this.social = false;
		},
		defaultSet() {
			this.role = null;
			this.social = false;
			this.name = null
		},
		setName(name) {
			this.name = name;
		}
	},
	getters: {
		isCustomer: (state) => state.role === ENUM_ROLE.customer,
		isLandlord: (state) => state.role === ENUM_ROLE.landlord,
	},
});