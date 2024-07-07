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
    <el-button class="test-button" type="success" @click="loadTestData">加载测试数据</el-button>
  </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'
import axios from 'axios'
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
    const map = ref(null)

    onMounted(() => {
      window._AMapSecurityConfig = {
        securityJsCode: '74af883e8262bd7d082b2b757d2cf43a',
      }

      AMapLoader.load({
        key: '0bf328a28e716a739487e5c91d005c90', // 新的 key
        version: '2.0',
      })
      .then(() => {
        map.value = new window.AMap.Map('mapContainer', {
          center: [116.39, 39.9], // 天安门的经纬度
          zoom: 11 // 地图级别
        })
      })
      .catch(e => {
        console.error('地图加载失败:', e) // 加载错误提示
      })
    })

    const onTriggerClick = () => {
      console.log('触发按钮点击')
      console.log('出发地:', origin.value)
      console.log('目的地:', destination.value)
      console.log('选择的时间:', selectedTime.value)
      
      // 发送请求获取热力图数据
      axios.post('http://192.168.43.229:8080/route', {
        origin: origin.value,
        destination: destination.value
      }, {
        params: {
          prediction_step: selectedTime.value
        }
      })
      .then(response => {
        const data = response.data.data; // 这里假设返回的数据格式正确
        console.log('收到的数据:', data);
        loadHeatmapData(data);
      })
      .catch(error => {
        console.error('获取热力图数据失败:', error);
      });
    }

    const loadTestData = () => {
      const testData = [
        { latitude: 39.921984, longitude: 116.418261, pred0: 0.5 },
        { latitude: 39.9075, longitude: 116.39723, pred0: 0.6 },
        { latitude: 39.909, longitude: 116.392, pred0: 0.3 },
        { latitude: 39.914, longitude: 116.404, pred0: 0.8 },
        { latitude: 39.924, longitude: 116.414, pred0: 0.9 }
      ]

      loadHeatmapData(testData);
    }

    const loadHeatmapData = (data) => {
      if (!map.value) {
        console.error('地图实例未初始化');
        return;
      }

      const getColor = (pred0) => {
        if (pred0 <= 0.4782545) return '#00FF00'; // 绿色
        if (pred0 <= 0.495) return '#FFFF00'; // 黄色
        return '#FF0000'; // 红色
      }

      data.forEach((point, index) => {
        try {
          console.log(`添加点 ${index + 1}:`, point)
          const circle = new window.AMap.Circle({
            center: new window.AMap.LngLat(point.longitude, point.latitude),
            radius: point.pred0 * 1000, // 根据拥挤程度设置圆的半径
            fillColor: getColor(point.pred0),
            fillOpacity: 0.5,
            strokeColor: getColor(point.pred0),
            strokeWeight: 1,
            strokeOpacity: 0.5
          })
          map.value.add(circle)
        } catch (error) {
          console.error(`添加点 ${index + 1} 失败:`, error)
        }
      })

      console.log(`${data.length} 个点已添加到地图`)
    }

    return {
      origin,
      destination,
      selectedTime,
      onTriggerClick,
      loadTestData
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

.test-button {
  position: absolute;
  top: 320px; /* 调整此值以确保按钮在上传按钮下方 */
  left: 20px;
  z-index: 10; /* 确保按钮在地图上方 */
}
</style>
