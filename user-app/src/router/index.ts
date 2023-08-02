import {createRouter, createWebHistory} from 'vue-router'

const HomePage = () => import('@/views/Home.vue');
const ApiPage = () => import('@/views/Api.vue');

const routes = [
  {
    path: '/',
    children: [
      { path: '', name: 'Home', component: HomePage },
      { path: '/api',  name: 'Api',  component: ApiPage }
    ],
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
