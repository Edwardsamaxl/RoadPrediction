<template>
  <div>
    <h2>Forgot Password</h2>
    <form @submit.prevent="forgotPassword">
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
      <el-button round type="success" native-type="submit">Send Request</el-button>
    </form>
    <el-button round type="primary" @click="$emit('changeView', 'Login')">Back to Login</el-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      emailSuffix: '.com' 
    }
  },
  methods: {
    forgotPassword() {
      if (!this.email) {
        this.$message.error('请输入邮箱');
        return;
      }
      const fullEmail = `${this.email}${this.emailSuffix}`;
      alert(`Password reset request sent to ${fullEmail}`);
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
</style>
