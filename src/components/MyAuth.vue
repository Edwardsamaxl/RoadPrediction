<template>
    <div id="my-auth">
      <nav class="myauthbar">
        <h1 class="myauthclass">MyAuth</h1>
      </nav>
      
    </div>
  </template>
  
  <script>
  export default {
    name: 'MyAuth'
  }
  </script>
  
  <style scoped>
  .myauthclass{
    position: absolute;
  top: 20px;
  left: 20px;
  margin: 0;
  font-size:17px;
  }

  .myauthbar{
    position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #333; /* 任务栏背景色 */
  color: white; /* 任务栏字体颜色 */
  height: 60px; /* 任务栏高度 */
  display: flex;
  align-items: center;
  padding: 0 20px; /* 左右内边距 */
  box-sizing: border-box;
  }
  </style>
  