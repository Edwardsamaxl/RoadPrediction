<template>
  <div class="container">
    <nav class="navbar">
      <h1 class="header-title">Welcome to My Auth App</h1>
    </nav>
    <div class="auth-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AuthContainer'
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  box-sizing: border-box;
  padding-top: 60px;
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #333; /* 任务栏背景色 */
  color: white; /* 任务栏字体颜色 */
  height: 60px; /* 任务栏高度 */
  display: flex;
  align-items: center;
  justify-content: center; /* Center align the title */
  padding: 0 20px;
  box-sizing: border-box;
}

.header-title {
  margin: 0;
  font-size: 17px;
}

.auth-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  width: 100%;
  max-width: 400px;
  padding: 20px;
  box-sizing: border-box;
}
</style>
