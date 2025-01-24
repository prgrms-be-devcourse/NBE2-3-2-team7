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
			{ path: '', component: () => import('../views/app/AppHome.vue') },
			{ path: '/signin', component: () => import('../views/sign/SignIn.vue') },
			{ path: '/signup/role', component: () => import('../views/sign/SignupRole.vue') },
			{ path: '/signup/account', component: () => import('../views/sign/SignupAccount.vue') },
			{ path: '/signup/info', component: () => import('../views/sign/SignupInfo.vue') },
			{ path: '/signup/success', component: () => import('../views/sign/SignupSuccess.vue') },
			{ path: '/land', component: () => import('../views/app/LandList.vue') },
			{ path: '/land/:id', component: () => import('../views/app/LandView.vue') },
			{ path: '/popup', component: () => import('../views/app/PopupList.vue') },
			{ path: '/popup/:id', component: () => import('../views/app/PopupView.vue') },
			{ path: '/payment', component: () => import('../views/payment/AppPayment.vue') },
			{ path: '/payment/success', component: () => import('../views/payment/PaymentSuccess.vue') },
			{ path: '/payment/failure', component: () => import('../views/payment/PaymentFailure.vue') },
			{ path: '/user', component: () => import('../views/user/UserHome.vue') },
			{ path: '/user/edit', component: () => import('../views/user/UserEdit.vue') },
			{ path: '/user/land', component: () => import('../views/user/land/LandList.vue') },
			{ path: '/user/land/:id', component: () => import('../views/user/land/LandView.vue') },
			{ path: '/user/land/add', component: () => import('../views/user/land/LandAdd.vue') },
			{ path: '/user/land/:id/reservation', component: () => import('../views/user/land/ReservationList.vue') },
			{ path: '/user/stats', component: () => import('../views/user/land/MyLandStats.vue') },
			{ path: '/user/popup', component: () => import('../views/user/popup/PopupList.vue') },
			{ path: '/user/popup/:id', component: () => import('../views/user/popup/PopupView.vue') },
			{ path: '/user/popup/add', component: () => import('../views/user/popup/PopupAdd.vue') },
			{ path: '/user/payment', component: () => import('../views/user/popup/ReceiptList.vue') },
		]
	},
];

const router = createRouter({
	history: createWebHistory(),
	routes
});

export default router;