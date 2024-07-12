<template>
  <div class="myauth-container">
    <nav class="myauth-navbar">
      <div class="left-section">
        <h1 class="myauth-title">MyAuth</h1>
        <div class="nav-links">
          <router-link to="/myauth/road-predict" class="nav-item" active-class="active-link">出行预测</router-link>
          <router-link to="/myauth/flow-indication" class="nav-item" active-class="active-link">流量查询</router-link>
          <router-link to="/myauth/personal-central" class="nav-item" active-class="active-link">个人中心</router-link>
        </div>
      </div>
      <el-dropdown class="avatar-dropdown" @command="handleCommand">
        <span class="el-dropdown-link">
          <img src="/image.jpg" class="avatar" alt="Avatar">
        </span>
        <template v-slot:dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="road-predict">出行预测</el-dropdown-item>
            <el-dropdown-item command="flow-indication">流量查询</el-dropdown-item>
            <el-dropdown-item command="personal-central">个人中心</el-dropdown-item>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </nav>
    <div class="content-container">
      <div class="flow-indication">
        <div id="mapContainer" class="map-container"></div>
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
        <div class="info-container" v-if="showInfo">
          <table class="transparent-table">
            <thead>
              <tr>
                <th>地址</th>
                <th>拥挤程度</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in sortedTableData" :key="item.location">
                <td>{{ item.location }}</td>
                <td>{{ item.pred0 }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'
import { ref, computed, onMounted } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'
import axios from 'axios'
import { Search } from '@element-plus/icons-vue'
import { ElInput, ElIcon, ElButton, ElSelect, ElOption } from 'element-plus'

export default {
  name: 'MyAuth',
  components: {
    Search,
    ElInput,
    ElIcon,
    ElButton,
    ElSelect,
    ElOption
  },
  setup() {
    const router = useRouter();

    const handleCommand = (command) => {
      if (command === 'road-predict') {
        router.push({ name: 'RoadPredict' });
      } else if (command === 'flow-indication') {
        router.push({ name: 'FlowIndication' });
      } else if (command === 'personal-central') {
        router.push({ name: 'PersonalCentral' });
      } else if (command === 'logout') {
        router.push({ name: 'Login' });
      }
    }

    const origin = ref('')
    const destination = ref('')
    const selectedTime = ref(null)
    const map = ref(null)
    const heatmap = ref(null)
    const tableData = ref([])
    const showInfo = ref(false)

    onMounted(() => {
      window._AMapSecurityConfig = {
        securityJsCode: 'c4a180c973adc6a758a5bfaa4ed920d0',
      }

      AMapLoader.load({
        key: '0af83ad3683a68de9ffadc1ad63500d8',
        version: '2.0',
        plugins: ['AMap.HeatMap']
      })
      .then((AMap) => {
        map.value = new AMap.Map('mapContainer', {
          center: [116.39, 39.9],
          zoom: 11
        })
        if (AMap.HeatMap) {
          heatmap.value = new AMap.HeatMap(map.value, {
            radius: 25,
            opacity: [0, 0.8]
          });
        } else {
          console.error('热力图插件加载失败');
        }
      })
      .catch(e => {
        console.error('地图加载失败:', e)
      })
    })

    const onTriggerClick = () => {
      // 打印输入的数据以进行调试
      console.log('Origin:', origin.value);
      console.log('Destination:', destination.value);
      console.log('Prediction Step:', selectedTime.value);

      axios.post('http://192.168.43.229:8080/route', {
        start: origin.value,
        end: destination.value
      }, {
        headers: {
          'Content-Type': 'application/json'
        },
        params: {
          prediction_step: selectedTime.value
        }
      })
      .then(response => {
        const data = response.data.data;
        clearHeatmap();
        loadHeatmapData(data);
        tableData.value = data;
        showInfo.value = true;
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

      clearHeatmap();
      loadHeatmapData(testData);
      tableData.value = testData;
      showInfo.value = true;
    }

    const clearHeatmap = () => {
      if (heatmap.value && heatmap.value.setDataSet) {
        heatmap.value.setDataSet({ data: [], max: 1 });
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
        return (pred - 0.47) * (1000 / (0.515 - 0.47));
      }

      const heatmapData = data.map(point => ({
        lng: point.longitude,
        lat: point.latitude,
        count: scalePred(point.pred0)
      }));

      heatmap.value.setDataSet({
        data: heatmapData,
        max: 100
      });
    }

    const sortedTableData = computed(() => {
      return tableData.value.slice().sort((a, b) => b.pred0 - a.pred0);
    });

    return {
      handleCommand,
      origin,
      destination,
      selectedTime,
      onTriggerClick,
      loadTestData,
      tableData,
      clearHeatmap,
      loadHeatmapData,
      showInfo,
      sortedTableData
    }
  }
}
</script>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
  overflow: hidden;
}

.myauth-navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #333;
  color: white;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-sizing: border-box;
  z-index: 2;
}

.left-section {
  display: flex;
  align-items: center;
}

.nav-links {
  display: flex;
  gap: 20px;
  margin-left: 20px;
}

.nav-item {
  color: white;
  text-decoration: none;
  padding: 10px;
}

.active-link {
  background-color: #555;
  border-radius: 5px;
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

.content-container {
  position: absolute;
  top: 60px; /* 确保内容在导航栏下方显示 */
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
}

.flow-indication {
  position: relative; /* 确保子元素相对于此容器定位 */
  height: 100%;
}

.map-container {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  z-index: 1;
}

.search-box {
  position: absolute;
  display: flex;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.8); /* 背景颜色半透明 */
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
  padding: 5px 10px;
  z-index: 2; /* 确保搜索框在地图之上 */
}

.search-box .input-label {
  margin-right: 10px;
  white-space: nowrap;
}

.search-box input {
  border: none;
  outline: none;
  padding: 5px;
  flex-grow: 1;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.7); /* 文本颜色半透明 */
  background-color: rgba(255, 255, 255, 0.8); /* 背景颜色半透明 */
}

.search-box .el-icon {
  color: rgba(0, 0, 0, 0.7); /* 图标颜色半透明 */
  font-size: 18px;
  margin-left: 10px;
}

.search-box input::placeholder {
  color: rgba(0, 0, 0, 0.7); /* 占位符颜色半透明 */
}

.origin {
  top: 20px; /* 确保在导航栏下方 */
  left: 20px;
}

.destination {
  top: 70px; /* 确保在导航栏下方 */
  left: 20px;
}

.time-selection {
  position: absolute;
  top: 120px;
  left: 20px;
  display: flex;
  align-items: center;
  z-index: 2; /* 确保时间选择框在地图之上 */
}

.time-select {
  width: 120px;
  background-color: rgba(255, 255, 255, 0.8); /* 背景颜色半透明 */
}

.trigger-button {
  position: absolute;
  top: 170px;
  left: 20px;
  z-index: 2; /* 确保按钮在地图之上 */
}

.test-button {
  position: absolute;
  top: 220px;
  left: 10px;
  z-index: 2; /* 确保按钮在地图之上 */
}

.info-container {
  position: absolute;
  bottom: 20px;
  right: 20px;
  width: 300px;
  max-height: 200px; /* 设置最大高度 */
  overflow-y: auto; /* 添加垂直滚动条 */
  background-color: rgba(255, 255, 255, 0.4); /* 调整透明度 */
  border-radius: 10px;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  z-index: 2; /* 确保信息容器在地图之上 */
}

.transparent-table {
  width: 100%;
  border-collapse: collapse;
}

.transparent-table th, .transparent-table td {
  background-color: rgba(255, 255, 255, 0); /* 背景颜色透明 */
  color: rgba(0, 0, 0, 1); /* 文本颜色不透明 */
  padding: 8px;
  border: 1px solid rgba(255, 255, 255, 0); /* 边框颜色透明 */
}

.transparent-table th {
  font-weight: bold;
}

.el-select-dropdown__item {
  background-color: rgba(255, 255, 255, 0.8); /* 背景颜色半透明 */
}

.el-button--primary, .el-button--success {
  background-color: rgba(64, 158, 255, 0.8); /* 调整按钮背景颜色透明度 */
  border-color: rgba(64, 158, 255, 0.8); /* 调整按钮边框颜色透明度 */
}
</style>
