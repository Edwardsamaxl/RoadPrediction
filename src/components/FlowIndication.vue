<template>
  <div class="flow-indication">
    <div class="search-box origin">
      <span class="input-label">出发地：</span>
      <el-input v-model="origin" clearable placeholder="输入出发地" />
      <el-icon><Search /></el-icon>
    </div>
    <div class="search-box destination">
      <span class="input-label">目的地：</span>
      <el-input v-model="destination" clearable placeholder="输入目的地" />
      <el-icon><Search /></el-icon>
    </div>
    <div class="time-selection">
      <span class="input-label">选择时间：</span>
      <el-select v-model="selectedTime" placeholder="选择时间" class="time-select">
        <el-option label="15分钟" value="15"></el-option>
        <el-option label="30分钟" value="30"></el-option>
        <el-option label="45分钟" value="45"></el-option>
        <el-option label="60分钟" value="60"></el-option>
      </el-select>
    </div>
    <el-button class="trigger-button" type="primary" @click="onTriggerClick">上传</el-button>
    <div id="mapContainer" class="map-container"></div>
  </div>
</template>

<script>
import { onMounted, nextTick, ref } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'
import { Search } from '@element-plus/icons-vue'
import { ElInput, ElIcon, ElButton, ElSelect, ElOption } from 'element-plus'

export default {
  name: 'FlowIndication',
  components: {
    Search,
    ElInput,
    ElIcon,
    ElButton,
    ElSelect,
    ElOption
  },
  setup() {
    const origin = ref('')
    const destination = ref('')
    const selectedTime = ref(null)

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

    const onTriggerClick = () => {
      console.log('触发按钮点击')
      console.log('出发地:', origin.value)
      console.log('目的地:', destination.value)
      console.log('选择的时间:', selectedTime.value)
    }

    return {
      origin,
      destination,
      selectedTime,
      onTriggerClick
    }
  }
}
</script>

<style scoped>
.map-container {
  width: 60%; /* 地图占据左侧五分之三 */
  height: calc(100vh - 60px); /* 确保地图容器有高度，减去导航栏高度 */
  margin-top: 0; /* 确保地图紧贴导航栏下方 */
  background-color: #f0f0f0; /* 添加背景色以便确认容器存在 */
  border: 2px solid #ccc; /* 添加边框 */
  box-sizing: border-box; /* 确保边框在容器内 */
}

.search-box {
  position: absolute;
  display: flex;
  align-items: center;
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
  padding: 5px 10px;
  z-index: 10; /* 确保搜索框在地图上方 */
}

.search-box .input-label {
  margin-right: 10px; /* 为标签添加一些右边距 */
  white-space: nowrap; /* 确保文本不换行 */
}

.search-box input {
  border: none;
  outline: none;
  padding: 5px;
  flex-grow: 1;
  font-size: 14px;
  color: #333;
}

.search-box .el-icon {
  color: #aaa;
  font-size: 18px;
  margin-left: 10px;
}

.search-box input::placeholder {
  color: #aaa;
}

.origin {
  top: 85px; /* 调整此值以确保搜索框在导航栏下方 */
  left: 20px;
}

.destination {
  top: 140px; /* 调整此值以确保搜索框在出发地搜索框下方 */
  left: 20px;
}

.time-selection {
  position: absolute;
  top: 200px; /* 调整此值以确保选择框在目的地搜索框下方 */
  left: 20px;
  display: flex;
  align-items: center;
  z-index: 10; /* 确保选择框在地图上方 */
}

.time-select {
  width: 120px; /* 增大选择框的宽度 */
}

.trigger-button {
  position: absolute;
  top: 260px; /* 调整此值以确保按钮在时间选择框下方 */
  left: 20px;
  z-index: 10; /* 确保按钮在地图上方 */
}
</style>
