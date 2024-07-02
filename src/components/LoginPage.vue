<template>
  <div>
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="username">Username:</label>
        <el-input  clearable type="text" v-model="username" placeholder="请输入username" id="username" required/>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <el-input show-password native-type="password" v-model="password" placeholder="请输入password" id="password" required/>
      </div>
      <el-button type="success" native-type="submit" style="width:100px; height:35px;">Login</el-button>
    </form>
    <div class="button-container">
      <el-button size="small" round type="primary" class="loginbutton" @click="$emit('changeView', 'Register')">Register</el-button>
      <el-button size="small" round type="info" class="loginbutton" @click="$emit('changeView', 'ForgotPassword')">Forgot</el-button>
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
.form-group {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  margin-bottom: 10px;
}

label {
  margin-bottom: 5px;
}

input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

.button-container {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  width: 100%;
}

button {
  padding: 10px 20px;
}

.loginbutton{
  width:100px;
  height:35px;
}
</style>
