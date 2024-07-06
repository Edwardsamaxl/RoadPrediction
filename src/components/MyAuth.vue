<template>
  <div class="myauth-container">
    <nav class="myauth-navbar">
      <div class="left-section">
        <h1 class="myauth-title">MyAuth</h1>
        <div class="nav-links">
          <el-button type="text" @click="navigateTo('RoadPredict')">出行预测</el-button>
          <el-button type="text" @click="navigateTo('FlowIndication')">流量查询</el-button>
          <el-button type="text" @click="navigateTo('PersonalCentral')">个人中心</el-button>
        </div>
      </div>
      <el-dropdown class="avatar-dropdown" @command="handleCommand">
        <span class="el-dropdown-link">
          <img src="/image.jpg" class="avatar" alt="Avatar">
        </span>
        <template v-slot:dropdown>
          <el-dropdown-menu>
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
    const navigateTo = (route) => {
  router.push({ name: route });
};

    return {
      navigateTo,
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

.left-section {
  display: flex;
  align-items: center;
}

.nav-links {
  display: flex;
  gap: 20px;
  margin-left: 20px;
}

.nav-links .el-button {
  color: white;
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
