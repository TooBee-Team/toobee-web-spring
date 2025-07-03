<template>
  <div>
    <input v-model="username" placeholder="用户名" />
    <input v-model="password" type="password" placeholder="密码" />
    <button @click="login">登录</button>
    <p>{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      username:'',
      password:'',
      message:''
    }
  },
  methods: {
    login() {
      axios.post('/login',`username=${this.username}&password=${this.password}`,
      {
        headers:{'Content-Type':'application/x-www-form-urlencoded'},
        withCredentials:true
      }).then(res=> { //withCredentials:true 表示允许跨域请求携带cookie
        if (res.data.msg ==='登录成功') {
          this.$router.push('/')
        } else {
          this.message = res.data.msg
        }
      }).catch(err => {
        this.message = '登录失败'
      })
    }
  }
}
</script>