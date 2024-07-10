<template>
  <div id="my-auth">
    <nav class="myauthbar">
      <h1 class="myauthclass">MyAuth</h1>
    </nav>

    <div id="mapContainer" style="width: 100vw; height: 100vh"></div>
  </div>
</template>

<script>
import { onMounted } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'

export default {
  name: 'MyAuth',
  setup() {
    onMounted(() => {
      window._AMapSecurityConfig = {
        securityJsCode: 'dfbd343035c1950b9c4ed6d3853bddb4',
      }

      AMapLoader.load({
        key: 'fa0f5e8dd4a533d9870323a287d1831f',
        version: '2.0',
        plugins: []
      })
      .then(AMap => {
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
  }
}
</script>

<style scoped>
.myauthclass {
  position: absolute;
  top: 20px;
  left: 20px;
  margin: 0;
  font-size: 17px;
}

.myauthbar {
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
  