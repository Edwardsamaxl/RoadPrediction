<template>
  <div class="road-predict">
    <div class="search-box origin">
      <el-input v-model="origin" clearable placeholder="出发地" />
      <el-icon><Search /></el-icon>
    </div>
    <div class="search-box destination">
      <el-input v-model="destination" clearable placeholder="目的地" />
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
    <div id="mapContainer" class="map-container"></div>

    <div v-if="showRoutes" class="routes-container">
      <el-card class="route-card" shadow="hover">
        <template #header>
          <div class="header-content">
            <span>规划路线</span>
            <el-button type="text" @click="minimizeRoutes">最小化</el-button>
          </div>
        </template>
        <el-radio-group v-model="selectedRoute" class="route-options">
          <el-radio :label="1">路线一：这是一条路线</el-radio>
          <el-radio :label="2">路线二：这是一条路线</el-radio>
          <el-radio :label="3">路线三：这是一条路线</el-radio>
        </el-radio-group>
        <div style="margin-top: 20px;">
          <el-button type="primary" @click="startJourney">出发</el-button>
        </div>
      </el-card>
    </div>

    <el-button v-if="!showRoutes" @click="maximizeRoutes" class="minimized-button" style="position: absolute; bottom: 20px; left: 20px;">
      展开
    </el-button>
  </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'
import { Search } from '@element-plus/icons-vue'
import { ElButton, ElCard, ElRadio, ElRadioGroup, ElInput, ElIcon, ElSelect, ElOption } from 'element-plus'

export default {
  name: 'RoadPredict',
  components: {
    Search,
    ElButton,
    ElCard,
    ElRadio,
    ElRadioGroup,
    ElInput,
    ElIcon,
    ElSelect,
    ElOption
  },
  setup() {
    const origin = ref('')
    const destination = ref('')
    const selectedTime = ref(null)
    const selectedRoute = ref(1)
    const showRoutes = ref(false)

    onMounted(() => {
      window._AMapSecurityConfig = {
        securityJsCode: '74af883e8262bd7d082b2b757d2cf43a',
      }

      AMapLoader.load({
        key: '0bf328a28e716a739487e5c91d005c90', // 新的 key
        version: '2.0',
        plugins: []
      })
      .then(AMap => {
        const map = new AMap.Map('mapContainer', {
          center: [116.39, 39.9], // 天安门的经纬度
          zoom: 11 // 地图级别
        })

        const marker = new AMap.Marker({
          position: [116.39, 39.9] // 标记的位置
        })
        map.add(marker)
      })
      .catch(e => {
        console.error('地图加载失败:', e) // 加载错误提示
      })
    })

    const checkInputs = () => {
      if (origin.value && destination.value) {
        showRoutes.value = true
      }
    }

    const startJourney = () => {
      console.log('出发地:', origin.value)
      console.log('目的地:', destination.value)
      console.log('选择的路线:', selectedRoute.value)
    }

    const minimizeRoutes = () => {
      showRoutes.value = false
    }

    const maximizeRoutes = () => {
      showRoutes.value = true
    }

    return {
      origin,
      destination,
      selectedTime,
      selectedRoute,
      showRoutes,
      checkInputs,
      startJourney,
      minimizeRoutes,
      maximizeRoutes
    }
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
  width: 120px; /* 墛大选择框的宽度 */
}

.routes-container {
  position: absolute;
  bottom: 20px; /* 固定在地图的左下角 */
  left: 20px;
  width: 300px; /* 固定宽度 */
  z-index: 10;
}

.route-card {
  width: 100%;
}

.route-options {
  display: flex;
  flex-direction: column;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.minimized-button {
  font-size: 14px; /* 根据需要调整按钮大小 */
}
</style>
