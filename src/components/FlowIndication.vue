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
      <div class="info-container" v-if="showInfo">
        <div class="image-container">
          <img :src="heatmapImageUrl" alt="热力图" class="heatmap-image" />
        </div>
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
    const heatmap = ref(null) // 热力图对象
    const heatmapImageUrl = ref('') // 热力图 URL
    const tableData = ref([]) // 表单数据
    const showInfo = ref(false) // 控制是否显示右侧信息

    onMounted(() => {
      window._AMapSecurityConfig = {
        securityJsCode: 'c4a180c973adc6a758a5bfaa4ed920d0',
      }

      AMapLoader.load({
        key: '0af83ad3683a68de9ffadc1ad63500d8', // 新的 key
        version: '2.0',
        plugins: ['AMap.HeatMap'] // 加载热力图插件
      })
      .then((AMap) => {
        map.value = new AMap.Map('mapContainer', {
          center: [116.39, 39.9], // 天安门的经纬度
          zoom: 11 // 地图级别
        })
        // 检查是否正确加载了HeatMap插件
        if (AMap.HeatMap) {
          heatmap.value = new AMap.HeatMap(map.value, {
            radius: 25, // 热力图的半径
            opacity: [0, 0.8] // 热力图透明度
          });
          console.log('热力图插件加载成功');
        } else {
          console.error('热力图插件加载失败');
        }
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
        clearHeatmap(); // 清除已有的热力图数据
        loadHeatmapData(data); // 加载新的热力图数据

        heatmapImageUrl.value = response.data.heatmapImageUrl;
        tableData.value = data; // 将数据分配给 tableData 变量
        showInfo.value = true; // 显示右侧信息
      })
      .catch(error => {
        console.error('获取热力图数据失败:', error);
      });
    }

    const loadTestData = () => {
      const testData = [
        { latitude: 39.921984, longitude: 116.418261, pred0: 0.48, location: '地点A' },
        { latitude: 39.9075, longitude: 116.39723, pred0: 0.49, location: '地点B' },
        { latitude: 39.909, longitude: 116.392, pred0: 0.47, location: '地点C' },
        { latitude: 39.914, longitude: 116.404, pred0: 0.50, location: '地点D' },
        { latitude: 39.924, longitude: 116.414, pred0: 0.51, location: '地点E' }
      ]

      clearHeatmap(); // 清除已有的热力图数据
      loadHeatmapData(testData); // 加载新的热力图数据

      heatmapImageUrl.value = '/mnt/data/image.png';
      tableData.value = testData;
      showInfo.value = true; // 显示右侧信息
    }

    const clearHeatmap = () => {
      if (heatmap.value && heatmap.value.setDataSet) {
        heatmap.value.setDataSet({ data: [], max: 1 });
        console.log('热力图数据已清除');
      } else {
        console.error('热力图实例未初始化或 setDataSet 方法不存在');
      }
    }

    const loadHeatmapData = (data) => {
      if (!map.value || !heatmap.value || !heatmap.value.setDataSet) {
        console.error('地图或热力图实例未初始化或 setDataSet 方法不存在');
        return;
      }

      const scalePred = (pred) => {
        // 将pred从0.47-0.515缩放到0-100
        return (pred - 0.47) * (1000 / (0.515 - 0.47));
      }

      const heatmapData = data.map(point => ({
        lng: point.longitude,
        lat: point.latitude,
        count: scalePred(point.pred0) // 缩放后的count
      }));

      heatmap.value.setDataSet({
        data: heatmapData,
        max: 100
      });
      console.log('热力图数据已加载', heatmapData);
    }

    return {
      origin,
      destination,
      selectedTime,
      onTriggerClick,
      loadTestData,
      heatmapImageUrl,
      tableData,
      clearHeatmap,
      loadHeatmapData,
      showInfo
    }
  }
}
</script>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
  overflow: hidden; /* 禁止页面滚动 */
}

.flow-indication {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
}

.map-container {
  flex: 1; /* 地图占据剩余空间 */
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
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
  top: 65px; /* 调整此值以确保搜索框在导航栏下方 */
  left: 20px;
}

.destination {
  top: 120px; /* 调整此值以确保搜索框在出发地搜索框下方 */
  left: 20px;
}

.time-selection {
  position: absolute;
  top: 180px; /* 调整此值以确保选择框在目的地搜索框下方 */
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
  top: 240px; /* 调整此值以确保按钮在时间选择框下方 */
  left: 20px;
  z-index: 10; /* 确保按钮在地图上方 */
}

.test-button {
  position: absolute;
  top: 300px; /* 调整此值以确保按钮在上传按钮下方 */
  left: 20px;
  z-index: 10; /* 确保按钮在地图上方 */
}

.content {
  display: flex;
  width: 100%;
  height: 100%;
}

.info-container {
  display: flex;
  flex-direction: column; /* 使 info-container 内部垂直排列 */
  width: 40%; /* 确保 info-container 占据右侧的两份 */
  height: 100%; /* 确保 info-container 有高度 */
  overflow: hidden; /* 隐藏超出部分 */
}

.image-container {
  height: 50%; /* 图片容器占 info-container 的一半 */
  padding: 10px; /* 为图片容器添加一些内边距 */
}

.heatmap-image {
  width: 100%; /* 确保热力图图片宽度填满 image-container */
  height: 100%; /* 确保热力图图片高度填满 image-container */
  object-fit: cover; /* 保持图片比例 */
}

.data-table-container {
  height: 50%; /* 表格容器占 info-container 的一半 */
  padding: 10px; /* 为表格容器添加一些内边距 */
  overflow-y: auto; /* 表格内滚动 */
}
</style>