<template>
  <div>
    <h2>Register</h2>
    <form @submit.prevent="register">
      <div>
        <label for="username">Username:</label>
        <input type="text" v-model="username" placeholder="输入账号" id="username" required>
      </div>
      <div>
        <label for="email">Email:</label>
        <input type="email" v-model="email" placeholder="输入邮箱" id="email" required>
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="password" placeholder="输入密码" id="password" required>
      </div>
      <button type="submit">Register</button>
    </form>
    <button @click="$emit('changeView', 'Login')">Back to Login</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      email: '',
      password: ''
    }
  },
  methods: {
    register() {
      alert(`Register with ${this.username}, ${this.email} and ${this.password}`);
      this.$emit('changeView', 'Login');
    }
  }
}
</script>

<style scoped>
button {
  margin-top: 20px;
}
</style>
