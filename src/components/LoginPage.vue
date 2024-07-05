<template>
  <div id="login-wrapper">
    <h2>登录</h2>
    <el-form @submit.prevent="login">
      <div class="form-group">
        <el-label for="username">用户名:</el-label>
        <el-input id="username" clearable type="text" v-model="username" placeholder="请输入用户名" required />
      </div>

      <div class="form-group">
        <el-label for="password">密码:</el-label>
        <el-input id="password" show-password native-type="password" v-model="password" placeholder="请输入密码" required />
      </div>

      <div class="button-container">
        <el-button round type="success" native-type="submit" class="loginbutton">登录</el-button>
      </div>
    </el-form>

    <div class="button-container">
      <el-button size="small" round type="primary" class="loginbutton" @click="$router.push({ name: 'Register' })">注册</el-button>
      <el-button size="small" round type="info" class="loginbutton" @click="$router.push({ name: 'ForgotPassword' })">忘记密码</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    async login() {
      if (this.username && this.password) {
        try {
          const response = await axios.post('http://192.168.31.153:8080/User/login', {
            username: this.username,
            password: this.password
          });
          if (response.data.status === 200) {
            // 登录成功，处理成功逻辑
            this.$router.push('/myauth');
          } else {
            this.$message.error(response.data.message);
          }
        } catch (error) {
          console.log(error);
          this.$message.error('请求失败，请稍后再试');
        }
      } else {
        this.$message.error('请输入用户名和密码');
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
  margin: 0 auto; /* 使其水平居中 */
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
  justify-content: center; /* 使按钮容器的子元素居中 */
  gap: 10px; /* 使用flex-gap来控制按钮之间的距离 */
  width: 100%;
}

button {
  padding: 10px 20px;
}

.loginbutton {
  width: 100px;
  height: 35px;
  display: block; /* 独占一行 */
  margin: 0 auto; /* 水平居中 */
}
</style>
