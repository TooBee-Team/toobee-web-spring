<!-- 本页面用vue3编写 练习 -->

<template>
  <h3>我的工程</h3>
  <div v-for="project in myProject" :key="project.id">
    工程名称:{{ project.name }}
    工程类型:{{ project.type }}
    工程介绍:{{ project.introduction }}
    工程创建时间:{{ project.projectCreateTime }}
    工程更新时间:{{ project.projectUpdateTime }}
    纬度:{{ project.world }}
    坐标:{{ project.x }} {{ project.y }} {{ project.z }}
    <button @click="editProject(project)">修改</button>
  </div>
  <div v-if="showEdit">
    工程名: <input v-model="editForm.name" placeholder="工程名">
    <br/>
    工程类型: <input v-model="editForm.type" placeholder="工程类型">
    <br/>
    工程纬度: <input v-model="editForm.world" placeholder="工程纬度">
    <br/>
    工程介绍: <input v-model="editForm.introduction" placeholder="工程介绍">
    <br/>
    工程创建时间: <input v-model="editForm.projectCreateTime" placeholder="工程创建时间" type="datetime-local">
    <br/>
    工程更新时间: <input v-model="editForm.projectUpdateTime" placeholder="工程更新时间" type="datetime-local">
    <br/>
    工程坐标-X轴: <input v-model="editForm.x" placeholder="工程坐标-X轴">
    <br/>
    工程坐标-Y轴: <input v-model="editForm.y" placeholder="工程坐标-Y轴">
    <br/>
    工程坐标-Z轴: <input v-model="editForm.z" placeholder="工程坐标-Z轴">
    <br/>
    <button @click="submitEdit">保存</button>
    <button @click="showEdit = false">取消</button>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref  } from 'vue';
import axios from 'axios';
const myProject = ref<any>({})
const showEdit = ref(false)
const editForm = ref<any>({})
onMounted(()=>{
  axios.get('/project/myProject',{withCredentials:true}).then(res =>{
    myProject.value = res.data.data
    console.log(myProject.value);
  })
})

function editProject(project:any) {
  editForm.value = {...project} //复制一份数据,一份数据，避免未保存时页面数据被污染
  showEdit.value = true
}
 function submitEdit() {
  axios.put('/project/update',editForm.value).then(res =>{
    const idx = myProject.value.findIndex(p => p.id === editForm.value.id)
    if (idx !==-1) {
      myProject.value[idx] = {...editForm.value}
    }
    showEdit.value=false
  })
 } 
</script>
<style scoped></style>