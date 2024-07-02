<template>
  <div>
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div>
        <label for="username">Username:</label>
        <input type="text" v-model="username" placeholder="请输入username" id="username" required>
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="password" placeholder="请输入password" id="password" required>
      </div>
      <button type="submit">Login</button>
    </form>
    <div class="button-container">
      <button @click="$emit('changeView', 'Register')">Register</button>
      <button @click="$emit('changeView', 'ForgotPassword')">Forgot Password</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    login() {
      alert(`Login with ${this.username} and ${this.password}`);
    }
  }
}
</script>

<style scoped>
.button-container {
  margin-top: 20px;
}

button {
  margin: 0 10px;
}
</style>
