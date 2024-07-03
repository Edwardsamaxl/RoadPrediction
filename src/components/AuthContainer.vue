<template>
  <div class="container">
    <nav class="navbar">
      <h1 class="header-title">Welcome to My Auth App</h1>
    </nav>
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
  padding-top: 60px;
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #333; /* 任务栏背景色 */
  color: white; /* 任务栏字体颜色 */
  height: 60px; /* 任务栏高度 */
  display: flex;
  align-items: center;
  padding: 0 20px; /* 左右内边距 */
  box-sizing: border-box;
}

.header-title {
  position: absolute;
  top: 20px;
  left: 20px;
  margin: 0;
  font-size:17px;
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
