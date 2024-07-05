import { createRouter, createWebHistory } from 'vue-router'
import AuthContainer from './components/AuthContainer.vue'
import LoginPage from './components/LoginPage.vue'
import MyAuth from './components/MyAuth.vue'
import RegisterPage from './components/RegisterPage.vue'
import ForgotPassword from './components/ForgotPassword.vue'
import PersonalCentral from './components/PersonalCentral.vue'
import RoadPredict from './components/RoadPredict.vue'
import FlowIndication from './components/FlowIndication.vue'

const routes = [
  {
    path: '/',
    component: AuthContainer,
    children: [
      { path: '', name: 'Login', component: LoginPage },
      { path: 'register', name: 'Register', component: RegisterPage },
      { path: 'forgot-password', name: 'ForgotPassword', component: ForgotPassword }
    ]
  },
  {
    path: '/myauth',
    component: MyAuth,
    children: [
      { path: '', redirect: { name: 'RoadPredict' } },  // 默认子路由重定向到 RoadPredict
      { path: 'personal-central', name: 'PersonalCentral', component: PersonalCentral },
      { path: 'road-predict', name: 'RoadPredict', component: RoadPredict },
      { path: 'flow-indication', name: 'FlowIndication', component: FlowIndication }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
