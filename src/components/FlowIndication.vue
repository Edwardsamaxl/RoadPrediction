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
    <div class="buttons">
      <el-button class="trigger-button" type="primary" @click="onTriggerClick">上传</el-button>
      <el-button class="test-button" type="success" @click="loadTestData">加载测试数据</el-button>
    </div>
    <div class="content">
      <div id="mapContainer" class="map-container"></div>
      <div class="info-container">
        <img :src="heatmapImageUrl" alt="热力图" class="heatmap-image" />
        <div class="data-table-container">
          <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="location" label="地址" width="150"></el-table-column>
            <el-table-column prop="pred0" label="拥挤程度" width="100"></el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'
import axios from 'axios'
import { Search } from '@element-plus/icons-vue'
import { ElInput, ElIcon, ElButton, ElSelect, ElOption, ElTable, ElTableColumn } from 'element-plus'

export default {
  name: 'FlowIndication',
  components: {
    Search,
    ElInput,
    ElIcon,
    ElButton,
    ElSelect,
    ElOption,
    ElTable,
    ElTableColumn
  },
  setup() {
    const origin = ref('')
    const destination = ref('')
    const selectedTime = ref(null)
    const map = ref(null)
    const heatmapImageUrl = ref('') // 热力图 URL
    const tableData = ref([]) // 表单数据
    const overlays = ref([]) // 存储覆盖物的数组

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
        clearHeatmapData();
        loadHeatmapData(data);

        // 假设热力图 URL 和表单数据也在响应中返回
        heatmapImageUrl.value = response.data.heatmapImageUrl;
        tableData.value = data; // 将数据分配给 tableData 变量
      })
      .catch(error => {
        console.error('获取热力图数据失败:', error);
      });
    }

    const loadTestData = () => {
      const testData = [
        { latitude: 39.921984, longitude: 116.418261, pred0: 0.5, location: '地点A' },
        { latitude: 39.9075, longitude: 116.39723, pred0: 0.6, location: '地点B' },
        { latitude: 39.909, longitude: 116.392, pred0: 0.3, location: '地点C' },
        { latitude: 39.914, longitude: 116.404, pred0: 0.8, location: '地点D' },
        { latitude: 39.924, longitude: 116.414, pred0: 0.9, location: '地点E' }
      ]

      clearHeatmapData();
      loadHeatmapData(testData);

      // 测试数据
      heatmapImageUrl.value = '/mnt/data/image.png';
      tableData.value = testData;
    }

    const clearHeatmapData = () => {
      overlays.value.forEach(overlay => {
        map.value.remove(overlay)
      })
      overlays.value = []
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

      const getRadius = (pred0) => {
        const standard = 0.47;
        if (pred0 <= 0.4782545) {
          return Math.abs(pred0 - 0.4782545) * 400000; // 绿色
        } else if (pred0 <= 0.495) {
          return Math.abs(pred0 - 0.495) * 20000; // 黄色
        } else {
          return Math.abs(pred0 - standard) * 9000; // 红色
        }
      }

      data.forEach((point, index) => {
        try {
          console.log(`添加点 ${index + 1}:`, point)
          const radius = getRadius(point.pred0); // 根据与阈值的差异设置圆的半径
          const circle = new window.AMap.Circle({
            center: new window.AMap.LngLat(point.longitude, point.latitude),
            radius: radius, 
            fillColor: getColor(point.pred0),
            fillOpacity: 0.5,
            strokeColor: getColor(point.pred0),
            strokeWeight: 1,
            strokeOpacity: 0.5
          })
          map.value.add(circle)
          overlays.value.push(circle)
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
      loadTestData,
      heatmapImageUrl,
      tableData
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

.content {
  display: flex;
  width: 100%;
  height: calc(100vh - 60px);
}

.info-container {
  width: 40%; /* 确保 info-container 占据右侧的两份 */
  height: calc(100vh - 60px); /* 确保 info-container 有高度，减去导航栏高度 */
  overflow-y: auto; /* 确保 info-container 具有滚动功能 */
}

.heatmap-image {
  width: 100%; /* 确保热力图图片宽度填满 info-container */
}

.data-table-container {
  padding: 10px; /* 为表格容器添加一些内边距 */
}
</style>