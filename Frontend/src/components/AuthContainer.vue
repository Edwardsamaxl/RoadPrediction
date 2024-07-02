<template>
  <div>
    <h1>Welcome to My Auth App</h1>
    <div>
      <button @click="currentView = 'Login'">Login</button>
      <button @click="currentView = 'Register'">Register</button>
      <button @click="currentView = 'ForgotPassword'">Forgot Password</button>
    </div>
    <component :is="currentViewComponent" v-if="currentViewComponent"></component>
  </div>
</template>

<script>
import Login from './LoginPage.vue'
import Register from './RegisterPage.vue'
import ForgotPassword from './ForgotPassword.vue'

export default {
  data() {
    return {
      currentView: 'Login'
    }
  },
  computed: {
    currentViewComponent() {
      if (this.currentView === 'Login') return Login
      if (this.currentView === 'Register') return Register
      if (this.currentView === 'ForgotPassword') return ForgotPassword
      return null
    }
  },
  components: {
    Login,
    Register,
    ForgotPassword
  }
}
</script>