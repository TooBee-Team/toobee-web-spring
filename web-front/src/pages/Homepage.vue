<script>
import axios from 'axios'
export default {
  name: "Homepage",
  data() {
    return {
      username:''
    }
  },
  mounted() {
    axios.get('/user/getUserInfo', { withCredentials: true }).then(res => {
      this.username = res.data.data.username
    })
  },
  methods: {
    information() {
      this.$router.push({
        name:'userinfo'
      });
    },
    logout() {
      axios.get('/logout',{},{withCredentials:true}).then(res =>{
        this.$router.push({
        name:'login'
      });
      }).catch(error =>{
        this.$router.push({
        name:'userinfo'
      });
      });
    },
    createProject() {
      this.$router.push({
        name:'project'
      })
    },
    myProject() {
     this.$router.push({
      name:'myProject'
     }) 
    }
  }
}
</script>

<template>
  <div>
    <h1>欢迎回来！</h1>
    <p>用户:{{ username }}</p>
    <button @click="information">详细信息</button>
    <button @click="logout">退出登录</button>
    <button @click="createProject">创建项目</button>
    <button @click="myProject">查看我的项目</button>
  </div>
</template>

<style scoped>
</style>