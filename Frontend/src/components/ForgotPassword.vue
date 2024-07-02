<template>
  <div>
    <h2>Forgot Password</h2>
    <form @submit.prevent="forgotPassword">
      <div>
        <label for="email">Email:</label>
        <input type="email" v-model="email" id="email" required>
      </div>
      <button type="submit">Send Request</button>
    </form>
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
      // Placeholder for forgot password logic
      alert(`Password reset request sent to ${this.email}`);
    }
  }
}
</script>