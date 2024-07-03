<template>
  <div id="my-auth">
    <nav class="myauthbar">
      <h1 class="myauthclass">MyAuth</h1>
      <el-dropdown class="avatar-dropdown" @command="handleCommand">
        <span class="el-dropdown-link">
          <img src="/image.jpg" class="avatar" alt="Avatar">
        </span>
        <template v-slot:dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="personal-central">个人中心</el-dropdown-item>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </nav>

    <div id="mapContainer" style="width: 100vw; height: 100vh"></div>
  </div>
</template>

<script>
import { onMounted, nextTick } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'
import { useRouter } from 'vue-router'

export default {
  name: 'MyAuth',
  setup() {
    const router = useRouter();

    onMounted(() => {
      nextTick(() => {
        window._AMapSecurityConfig = {
          securityJsCode: 'dfbd343035c1950b9c4ed6d3853bddb4',
        }

        AMapLoader.load({
          key: 'fa0f5e8dd4a533d9870323a287d1831f',
          version: '2.0',
          plugins: []
        })
        .then(AMap => {
          console.log('高德地图 API 加载成功')
          const map = new AMap.Map('mapContainer', {
            center: [116.39, 39.9], // 天安门的经纬度
            zoom: 11, // 地图级别
          })

          const marker = new AMap.Marker({
            position: [116.39, 39.9], // 标记的位置
          })
          map.add(marker)
        })
        .catch(e => {
          console.error('地图加载失败:', e) // 加载错误提示
        })
      })
    });

    const handleCommand = (command) => {
      if (command === 'personal-central') {
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
.myauthclass {
  margin: 0;
  font-size: 17px;
}

.myauthbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #333;
  color: white;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between; /* Space between title and avatar */
  padding: 0 20px;
  box-sizing: border-box;
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

.map-container {
  width: 100%;
  height: calc(100vh - 80px); /* 确保地图容器有高度，减去导航栏高度 */
  margin-top: 60px; /* 防止导航栏遮挡 */
  background-color: #f0f0f0; /* 添加背景色以便确认容器存在 */
}
</style>
  