<template>
  <div class="container">
    <h1 class="header-title">Welcome to My Auth App</h1>
    <div class="auth-container">
      <component :is="currentViewComponent" @changeView="changeView" @actionCompleted="handleActionCompleted" v-if="currentViewComponent"></component>
    </div>
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
.container {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: center;
  height: 100vh;
  padding-right: 10%;
  box-sizing: border-box;
}

.header-title {
  position: absolute;
  top: 20px;
  left: 20px;
  margin: 0;
}

.auth-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  width: 100%;
  max-width: 400px; /* Limit the width of the auth-container */
  padding: 20px;
  box-sizing: border-box;
}
</style>
