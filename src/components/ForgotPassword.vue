<template>
  <div id="forgot-password-wrapper">
    <h2>忘记密码</h2>
    <el-form @submit.prevent="stage === 1 ? sendCode() : resetPassword()">
      <div class="form-group">
        <el-label for="username">用户名:</el-label>
        <el-input v-model="username" placeholder="输入用户名" id="username" required></el-input>
      </div>
      <div class="form-group">
        <el-label for="email">邮箱:</el-label>
        <el-input v-model="email" placeholder="输入邮箱" id="email" required>
        </el-input>
      </div>
      <!-- 新增验证码输入 -->
      <div class="form-group" v-if="stage === 2">
        <el-label for="code">验证码:</el-label>
        <el-input v-model="code" placeholder="输入验证码" id="code" required></el-input>
      </div>
      <!-- 新增密码输入 -->
      <div class="form-group" v-if="stage === 2">
        <el-label for="newPassword">新密码:</el-label>
        <el-input type="password" v-model="newPassword" placeholder="设置新密码" id="newPassword" required></el-input>
      </div>
      <div class="button-container">
        <el-button round type="success" native-type="submit" class="actionbutton">
          {{ stage === 1 ? '发送验证码' : '重置密码' }}
        </el-button>
      </div>
    </el-form>
    <div class="button-container">
      <el-button round type="primary" @click="$router.push({ name: 'Login' })">返回登录</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      email: '',
      code: '',
      newPassword: '',
      stage: 1 // 用于控制显示第一步或第二步
    }
  },
  methods: {
    async sendCode() {
      const fullEmail = `${this.email}`;
      try {
        const response = await axios.post('http://116.205.111.140:9090/User/mail-sent', {
          username: this.username,
          mail: fullEmail
        }, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (response.data.status === 200) {
          this.$message.success('验证码已发送，请检查您的邮件');
          this.stage = 2; // 进入下一步
        } else {
          this.$message.error(response.data.message);
        }
      } catch (error) {
        this.$message.error('请求失败，请稍后再试');
      }
    },
    async resetPassword() {
      const fullEmail = `${this.email}`;
      try {
        const response = await axios.post('http://116.205.111.140:9090/User/reset-password', {
          mail: fullEmail,
          code: this.code,
          newpassword: this.newPassword
        }, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (response.data.status === 200) {
          this.$message.success('密码重置成功，请登录');
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
#forgot-password-wrapper {
  border: 1px solid #000; /* 边框颜色 */
  border-radius: 5px; /* 边框圆角 */
  padding: 20px; /* 内边距 */
  box-shadow: 0px 0px 10px rgba(0,0,0,0.1); /* 阴影效果 */
  width: 340px; /* 固定宽度 */
  height: 370px; /* 固定高度 */
  margin: 0 auto; /* 使其水平居中 */
  /* background: rgba(0,0,0,0.1); */
  background-color: #fff;
  /* 背景颜色 */
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

.actionbutton {
  width: 100px;
  height: 35px;
  display: block; /* 独占一行 */
  margin: 0 auto; /* 水平居中 */
}
</style>
