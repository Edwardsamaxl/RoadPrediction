import { createRouter, createWebHistory } from 'vue-router'
import AuthContainer from './components/AuthContainer.vue'
import LoginPage from './components/LoginPage.vue'
import MyAuth from './components/MyAuth.vue'
import RegisterPage from './components/RegisterPage.vue'
import ForgotPassword from './components/ForgotPassword.vue'

const routes = [
  {
    path: '/',
    component: AuthContainer,
    children: [
      { path: '', name: 'Login', component: LoginPage },
      { path: 'register', name: 'Register', component: RegisterPage },
      { path: 'forgot-password', name: 'ForgotPassword', component: ForgotPassword },
      { path: 'myauth', name: 'MyAuth', component: MyAuth }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
