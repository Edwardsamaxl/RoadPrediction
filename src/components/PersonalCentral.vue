<template>
    <div id="person-central">
      <el-card class="box-card">
        <template #header>
          <div class="clearfix">
            <span>个人中心</span>
          </div>
        </template>
        <el-form :model="user" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="user.username" readonly></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="user.email" readonly></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="user.gender">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
              <el-radio label="其他">其他</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="个人简介">
            <el-input type="textarea" v-model="user.bio"></el-input>
          </el-form-item>
          <el-form-item label="所在地区">
            <el-cascader v-model="user.region" :options="regionOptions"></el-cascader>
          </el-form-item>
          <el-form-item label="地址管理">
            <el-input v-model="user.address"></el-input>
          </el-form-item>
          <el-form-item label="出生日期">
            <el-date-picker v-model="user.birthdate" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveUser">保存</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        user: {
          username: '张三',
          email: 'zhangsan@example.com',
          gender: '男',
          bio: '这是一个个人简介。',
          region: [],
          address: '某某街道，某某小区',
          birthdate: ''
        },
        regionOptions: [
          {
            value: '中国',
            label: '中国',
            children: [
              {
                value: '北京',
                label: '北京',
                children: [
                  { value: '朝阳区', label: '朝阳区' },
                  { value: '海淀区', label: '海淀区' }
                ]
              },
              {
                value: '上海',
                label: '上海',
                children: [
                  { value: '浦东新区', label: '浦东新区' },
                  { value: '徐汇区', label: '徐汇区' }
                ]
              }
            ]
          }
        ]
      };
    },
    methods: {
      saveUser() {
        // 模拟保存用户信息的操作
        this.$message({
          message: '用户信息保存成功',
          type: 'success'
        });
      }
    }
  };
  </script>
  
  <style scoped>
  #person-central {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .box-card {
    margin-bottom: 20px;
  }
  </style>
  