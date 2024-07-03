import { createRouter, createWebHistory } from 'vue-router'
import LoginPage from './components/LoginPage.vue'
import MyAuth from './components/MyAuth.vue'

const routes = [
  { path: '/', name: 'Login', component: LoginPage },
  { path: '/myauth', name: 'MyAuth', component: MyAuth }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
