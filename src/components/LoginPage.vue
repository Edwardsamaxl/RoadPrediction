<template>
  <div id="login-wrapper">
    <h2>Login</h2>
    <el-form @submit.prevent="login">
      <div class="form-group">
        <el-label for="username">Username:</el-label>
        <el-input id="username" clearable type="text" v-model="username" placeholder="请输入username" required />
      </div>

      <div class="form-group">
        <el-label for="password">Password:</el-label>
        <el-input id="password" show-password native-type="password" v-model="password" placeholder="请输入password" required />
      </div>

      <el-button type="success" native-type="submit">Login</el-button>
    </el-form>

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
    if (this.username && this.password) {
      setTimeout(() => {
        if (this.username === '123' && this.password === '123') {
          const fakeToken = '1234567890abcdef';
          localStorage.setItem("user-token", fakeToken);
          this.$router.push({ name: 'MyAuth' });
        } else {
          alert('账号密码错误，请重新输入');
        }
      }, 1000);
    } else {
      alert('请输入用户名和密码');
    }
  }
  }
}
</script>

<style scoped>
#login-wrapper {
  border: 1px solid #000; /* 边框颜色 */
  border-radius: 5px; /* 边框圆角 */
  padding: 20px; /* 内边距 */
  box-shadow: 0px 0px 10px rgba(0,0,0,0.1); /* 阴影效果 */
  width: 340px; /* 固定宽度 */
  height: 370px; /* 固定高度 */
  margin: auto; /* 自动调整外边距，使其水平居中 */
  background-color: #fff; /* 背景颜色 */
  display: flex;
  flex-direction: column;
  justify-content: center; /* 垂直居中 */
  align-items: center; /* 水平居中 */
}

.form-group {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  margin-bottom: 10px;
}

label {
  margin-bottom: 5px;
  width: 100%;
}

.el-input {
  width: 100%; /* 确保输入框宽度全填充 */
}

.button-container {
  margin-top: 20px;
  display: flex;
  justify-content: center; /* 改为center以使按钮居中 */
  gap: 10px; /* 使用flex-gap来控制按钮之间的距离 */
  width: 100%;
}

button {
  padding: 10px 20px;
}

.loginbutton {
  width: 100px;
  height: 35px;
}
</style>
