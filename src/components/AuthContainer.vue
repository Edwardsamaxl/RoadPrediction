<template>
  <div class="auth-container">
    <h1>Welcome to My Auth App</h1>
    <component :is="currentViewComponent" @changeView="changeView" @actionCompleted="handleActionCompleted" v-if="currentViewComponent"></component>
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
  methods: {
    changeView(view) {
      this.currentView = view;
    },
    handleActionCompleted() {
      this.currentView = 'Login';
    }
  },
  components: {
    Login,
    Register,
    ForgotPassword
  }
}
</script>

<style scoped>
.auth-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  text-align: center;
}
</style>
