import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/media/:id',
      name: 'view-media',
      component: () => import('@/views/MediaView.vue')
    },
    {
      path: '/media/create',
      name: 'create-media',
      component: () => import('@/views/CreateMediaView.vue')
    },
    {
      path: '/sign-up',
      name: 'signup',
      component: () => import('@/views/SignUpView.vue')
    },
    // New routes above this
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue')
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'not-found',
      component: () => import('@/views/NotFound.vue')
    }
  ]
});

export default router;
