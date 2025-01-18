import { createRouter, createWebHistory } from 'vue-router';

const routes = [
	{ path: '/test', component: () => import('../views/HelloWorld.vue') },
	{ path: '/', component: () => import('../views/AppHome.vue') },
	{ path: '/land', component: () => import('../views/LandList.vue') },
	{ path: '/land/:id', component: () => import('../views/LandView.vue') },
	{ path: '/popup', component: () => import('../views/PopupList.vue') },
	{ path: '/popup/:id', component: () => import('../views/PopupView.vue') },
	{ path: '/payment', component: () => import('../views/AppPayment.vue') },
	{ path: '/payment/success', component: () => import('../views/PaymentSuccess.vue') },
	{ path: '/payment/failure', component: () => import('../views/PaymentFailure.vue') },
	{ path: '/user', component: () => import('../views/User.vue') },
];

const router = createRouter({
	history: createWebHistory(),
	routes
});

export default router;