import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
	state: () => ({
		id: 0,
		role: null,
		auth: false,
	}),
	actions: {
		setUser(user) {
			this.id = user.id;
			this.role = user.role;
			this.auth = !!user;
		},
		clearUser() {
			this.id = 0;
			this.role = null;
			this.auth = false;
		}
	},
});