<template>
    <div class="road-predect">
      <div id="mapContainer" class="map-container"></div>
    </div>
  </template>
  
  <script>
  import { onMounted, nextTick } from 'vue'
  import AMapLoader from '@amap/amap-jsapi-loader'
  
  export default {
    name: 'RoadPredict',
    setup() {
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
  
      return {}
    }
  }
  </script>
  
  <style scoped>
  .map-container {
    width: 100%;
    height: calc(100vh - 60px); /* 确保地图容器有高度，减去导航栏高度 */
    margin-top: 0; /* 确保地图紧贴导航栏下方 */
    background-color: #f0f0f0; /* 添加背景色以便确认容器存在 */
  }
  </style>
  