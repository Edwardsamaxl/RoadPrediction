<template>
  <div>
    <h2>Register</h2>
    <form @submit.prevent="register">
      <div class="form-group">
        <label for="username">Username:</label>
        <el-input maxlength="10" clearable native-type="text" v-model="username" placeholder="输入账号" id="username" required/>
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
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
        <label for="password">Password:</label>
        <el-input maxlength="14" show-password native-type="password" v-model="password" placeholder="输入密码" id="password" @blur="validatePassword" :class="{ 'is-error': passwordError }" required/>
      </div>
      <el-button round type="success" native-type="submit" :disabled="passwordError || !password" @click="register">Register</el-button>
    </form>
    <el-button round type="primary" @click="$emit('changeView', 'Login')">Back to Login</el-button>
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
      this.$emit('changeView', 'Login');
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

.el-input {
  width: 100%;
}

.el-select {
  width: 100px;
}

button {
  padding: 10px 20px;
  margin-top: 20px;
  width: 100%;
}

.is-error .el-input__inner {
  border-color: red;
}
</style>
