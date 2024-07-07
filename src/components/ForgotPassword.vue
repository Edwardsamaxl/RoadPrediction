<template>
  <div id="forgot-password-wrapper">
    <h2>忘记密码</h2>
    <el-form @submit.prevent="forgotPassword">
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
      <el-button round type="success" native-type="submit" class="forgotbutton">发送请求</el-button>
    </el-form>
    <el-button round type="primary" @click="$router.push({ name: 'Login' })">返回登录</el-button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: '',
      emailSuffix: '.com'
    }
  },
  methods: {
    async forgotPassword() {
      if (!this.email) {
        this.$message.error('请输入邮箱');
        return;
      }
      const fullEmail = `${this.email}${this.emailSuffix}`;
      try {
        const response = await axios.post('http://192.168.43.84:8080/User/update', {
          email: fullEmail
        });
        if (response.data.status === 200) {
          this.$message.success('密码重置请求已发送，请查收邮件');
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
  margin-top: 30px;
  width: 150px;
}
.forgotbutton{
  width: 150px;
  height: 32px;
  display: block; /* 独占一行 */
  margin: 0 auto; /* 水平居中 */
  margin: 40px auto 0; /* 上方20px, 水平居中 */
}
</style>
