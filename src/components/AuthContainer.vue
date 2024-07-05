<template>
  <div class="container">
    <nav class="navbar">
      <h1 class="header-title">Welcome to My Auth App</h1>
    </nav>
    <div class="auth-content">
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
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center; /* 确保内容在垂直方向居中 */
  height: 100vh;
  width: 100vw;
  box-sizing: border-box;
  background-image: url("/public/background.png"); /* 使用绝对路径 */
  background-size: cover;
  background-attachment: fixed; /* 背景图片固定 */
  background-position: center;
  overflow: hidden; /* 防止滚动条显示 */
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
  justify-content: center; /* 居中对齐标题 */
  padding: 0 20px;
  box-sizing: border-box;
  z-index: 2; /* 确保导航栏在背景图片之上 */
}

.header-title {
  margin: 0;
  font-size: 17px;
}

.auth-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center; /* 确保内容在垂直方向居中 */
  width: 100%;
  height: calc(100vh - 60px); /* 减去导航栏的高度 */
  padding-top: 60px; /* 确保内容不会被导航栏遮挡 */
  box-sizing: border-box;
  z-index: 1; /* 确保内容在背景图片之上 */
  position: relative;
}
</style>
