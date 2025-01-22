import { createRouter, createWebHistory } from 'vue-router';

const routes = [
	{ path: '/test', component: () => import('../views/HelloWorld.vue') },

	{
		path: '/admin', component: () => import('../layout/AdminLayout.vue'),
		children: [
			{ path: '', component: () => import('../views/admin/AdminHome.vue') },
			{ path: '/admin/land', component: () => import('../views/admin/AdminLand.vue') },
			{ path: '/admin/popup', component: () => import('../views/admin/AdminPopup.vue') },
			{ path: '/admin/user', component: () => import('../views/admin/AdminUsers.vue') },
			{ path: '/admin/orders', component: () => import('../views/admin/AdminOrders.vue') },
			{ path: '/admin/analytics', component: () => import('../views/admin/AdminAnalytics.vue') },
		],
	},

	{
		path: '/', component: () => import('../layout/DefaultLayout.vue'),
		children: [
			{ path: '', component: () => import('../views/AppHome.vue') },
			{ path: '/signin', component: () => import('../views/sign/SignIn.vue') },
			{ path: '/signup/role', component: () => import('../views/sign/SignupRole.vue') },
			{ path: '/signup/account', component: () => import('../views/sign/SignupAccount.vue') },
			{ path: '/signup/info', component: () => import('../views/sign/SignupInfo.vue') },
			{ path: '/signup/success', component: () => import('../views/sign/SignupSuccess.vue') },
			{ path: '/land', component: () => import('../views/LandList.vue') },
			{ path: '/land/:id', component: () => import('../views/LandView.vue') },
			{ path: '/popup', component: () => import('../views/PopupList.vue') },
			{ path: '/popup/:id', component: () => import('../views/PopupView.vue') },
			{ path: '/payment', component: () => import('../views/AppPayment.vue') },
			{ path: '/payment/success', component: () => import('../views/PaymentSuccess.vue') },
			{ path: '/payment/failure', component: () => import('../views/PaymentFailure.vue') },
		]
	},


];

const router = createRouter({
	history: createWebHistory(),
	routes
});

export default router;