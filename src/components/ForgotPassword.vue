<template>
  <div id="forgot-password-wrapper">
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
#forgot-password-wrapper {
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
}

.el-input {
  width: 100%;
}

.el-select {
  width: 100px;
}

button {
  padding: 10px 20px;
  margin-top: 30px;
  width: 150px;
}
</style>
