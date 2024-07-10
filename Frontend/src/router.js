import { createRouter, createWebHistory } from 'vue-router'
import LoginPage from './components/LoginPage.vue'
import MyAuth from './components/MyAuth.vue'
import RegisterPage from './components/RegisterPage.vue'
import ForgotPassword from './components/ForgotPassword.vue'

const routes = [
  { path: '/', name: 'Login', component: LoginPage },
  { path: '/myauth', name: 'MyAuth', component: MyAuth },
  { path: '/register', name: 'Register', component: RegisterPage },
  { path: '/forgot-password', name: 'ForgotPassword', component: ForgotPassword }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
