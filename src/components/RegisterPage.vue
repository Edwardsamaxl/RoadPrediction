<template>
  <div id="register-wrapper">
    <h2>注册</h2>
    <el-form @submit.prevent="register">
      <div class="form-group">
        <el-label for="username">用户名:</el-label>
        <el-input maxlength="10" clearable native-type="text" v-model="username" placeholder="输入账号" id="username" required/>
      </div>
      <div class="form-group">
        <el-label for="email">邮箱:</el-label>
        <el-input v-model="email" placeholder="输入邮箱" id="email" required>
          <template #append>
            <el-select v-model="emailSuffix" placeholder="选择后缀">
              <el-option label=".com" value=".com"></el-option>
              <el-option label=".cn" value=".cn"></el-option>
            </el-select>
          </template>
        </el-input>
      </div>
      <div class="form-group">
        <el-label for="password">密码:</el-label>
        <el-input maxlength="14" show-password native-type="password" v-model="password" placeholder="输入密码" id="password" @blur="validatePassword" :class="{ 'is-error': passwordError }" required/>
      </div>
      <el-button round type="success" native-type="submit" :disabled="passwordError || !password" class="registerbutton">注册</el-button>
    </el-form>
    <el-button round type="primary" @click="$router.push({ name: 'Login' })">返回登录</el-button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      email: '',
      emailSuffix: '.com',
      password: '',
      passwordError: true,
      showError: false
    }
  },
  watch: {
    password: 'validatePassword'
  },
  methods: {
    validatePassword() {
      const password = this.password;
      if (password.length < 6 || password.length > 14) {
        this.passwordError = true;
        if (!this.showError) {
          this.$message.error('密码长度应在6到14个字符之间');
          this.showError = true;
        }
      } else {
        this.passwordError = false;
        this.showError = false;
      }
    },
    async register() {
      this.validatePassword();
      if (this.passwordError) {
        this.$message.error('请修正表单错误后再提交');
        return;
      }
      const fullEmail = `${this.email}${this.emailSuffix}`;
      try {
        const response = await axios.post('http://192.168.31.153:8080/User/register', {
          username: this.username,
          email: fullEmail,
          password: this.password
        });
        if (response.data.status === 200) {
          this.$message.success('注册成功，请登录');
          this.$router.push({ name: 'Login' });
        } else {
          this.$message.error(response.data.message);
        }
      } catch (error) {
        this.$message.error('请求失败，请稍后再试');
      }
    }
  }
}
</script>

<style scoped>
#register-wrapper {
  border: 1px solid #000;
  border-radius: 5px;
  padding: 20px;
  box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
  width: 340px;
  height: 370px;
  margin: auto;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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
}

.el-input {
  width: 100%;
}

.el-select {
  width: 100px;
}

button {
  padding: 10px 20px;
  margin-top: 20px;
  width: 150px;
}

.is-error .el-input__inner {
  border-color: red;
}
.registerbutton{
  width: 150px;
  height: 32px;
  display: block; /* 独占一行 */
  margin: 0 auto; /* 水平居中 */
}
</style>
