<template>
  <div>
    <h2>Forgot Password</h2>
    <form @submit.prevent="forgotPassword">
      <div>
        <label for="email">Email:</label>
        <input type="email" v-model="email" placeholder="输入邮箱" id="email" required>
      </div>
      <button type="submit">Send Request</button>
    </form>
    <button @click="$emit('changeView', 'Login')">Back to Login</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: ''
    }
  },
  methods: {
    forgotPassword() {
      alert(`Password reset request sent to ${this.email}`);
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
