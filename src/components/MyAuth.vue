<template>
  <div class="myauth-container">
    <nav class="myauth-navbar">
      <h1 class="myauth-title">MyAuth</h1>
      <el-dropdown class="avatar-dropdown" @command="handleCommand">
        <span class="el-dropdown-link">
          <img src="/image.jpg" class="avatar" alt="Avatar">
        </span>
        <template v-slot:dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="road-predict">出行预测</el-dropdown-item>
            <el-dropdown-item command="flow-indication">流量查询</el-dropdown-item>
            <el-dropdown-item command="personal-central">个人中心</el-dropdown-item>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </nav>
    <div class="content-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'

export default {
  name: 'MyAuth',
  setup() {
    const router = useRouter();

    const handleCommand = (command) => {
      if (command === 'road-predict') {
        router.push({ name: 'RoadPredict' });
      } else if (command === 'flow-indication') {
        router.push({ name: 'FlowIndication' });
      } else if (command === 'personal-central') {
        router.push({ name: 'PersonalCentral' });
      } else if (command === 'logout') {
        router.push({ name: 'Login' });
      }
    }

    return {
      handleCommand
    }
  }
}
</script>

<style scoped>
.myauth-navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #333;
  color: white;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-sizing: border-box;
  z-index: 1;
}

.avatar-dropdown {
  cursor: pointer;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.content-container {
  margin-top: 60px; /* 确保内容在导航栏下方显示 */
}
</style>
