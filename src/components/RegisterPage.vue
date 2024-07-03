<template>
  <div id="register-wrapper">
    <h2>Register</h2>
    <el-form @submit.prevent="register">
      <div class="form-group">
        <el-label for="username">Username:</el-label>
        <el-input maxlength="10" clearable native-type="text" v-model="username" placeholder="输入账号" id="username" required/>
      </div>
      <div class="form-group">
        <el-label for="email">Email:</el-label>
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
        <el-label for="password">Password:</el-label>
        <el-input maxlength="14" show-password native-type="password" v-model="password" placeholder="输入密码" id="password" @blur="validatePassword" :class="{ 'is-error': passwordError }" required/>
      </div>
      <el-button round type="success" native-type="submit" :disabled="passwordError || !password">Register</el-button>
    </el-form>
    <el-button round type="primary" @click="$router.push({ name: 'Login' })">Back to Login</el-button>
  </div>
</template>

<script>
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
    register() {
      this.validatePassword(); 
      if (this.passwordError) {
        this.$message.error('请修正表单错误后再提交');
        return;
      }
      const fullEmail = `${this.email}${this.emailSuffix}`;
      alert(`Register with ${this.username}, ${fullEmail} and ${this.password}`);
      this.$router.push({ name: 'Login' });
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
</style>
