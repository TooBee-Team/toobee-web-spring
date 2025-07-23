<script>
  import axios from 'axios';
  export default {
    name:"userInfo",
    data() {
      return {
        userData: null, //存储从后端获取的用户信息
        editing:false, //是否为编辑模式
        newUserData:{
          id:"",
          uuid:"",
          username:"",
          qq:"",
          email:"",
          wechat:"",
          telegram:"",
        },
        errors: { //用于储存每个字段的错误信息
          username:"",
          qq:"",
          email:"",
          wechat:"",
          telegram:""
        }
      }
    },
    mounted() {
      axios.get('/user/getUserInfo',{withCredentials:true}).then(res =>{
        this.userData=res.data.data;
        console.log("从后端获取到的用户数据：userData",this.userData);
      }).catch(error =>{
        console.log("获取失败",error);
      });
    },
    methods: {
      editUserInfo() {
        this.editing=true;
        this.newUserData.id=this.userData.id;
        this.newUserData.uuid=this.userData.uuid;
        this.newUserData.username=this.userData.username;
        this.newUserData.qq=this.userData.qq;
        this.newUserData.email=this.userData.email;
        this.newUserData.wechat=this.userData.wechat;
        this.newUserData.telegram=this.userData.telegram;
      },
      cancelEdit() {
        this.editing=false;
        this.newUserData=this.userData;
      },
      validateUsername() {
        if (!/^[a-zA-Z0-9_.]{2,20}/.test(this.newUserData.username)) {
          this.errors.username="用户名只能包含字母、数字、下划线和点，长度为2-20";
        } else {
          this.errors.username="";
        }
      },
      validateQq() {
        if (!/^\d{5,12}$/.test(this.newUserData.qq)) {
          this.errors.qq="QQ号只能包含5-12位数字";
        } else {
          this.errors.qq="";
        }
      },
      validateEmail() {
        if (!/^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/.test(this.newUserData.email)) {
          this.errors.email="请输入正确的邮箱地址";
        } else {
          this.errors.email="";
        }
      },
      validateWechat() {
        if (!/^[a-zA-Z0-9_.]{2,20}/.test(this.newUserData.wechat)) {
          this.errors.wechat="微信号只能包含字母、数字、下划线和点，长度为2-20";
        } else {
          this.errors.wechat="";
        }
      },
      validateTelegram() {
        if (!/^[a-zA-Z0-9_.]{2,20}/.test(this.newUserData.telegram)) {
          this.errors.telegram="TG只能包含字母、数字、下划线和点，长度为2-20";
        } else {
          this.errors.telegram="";
        }
      },
      saveUserInfo() {
        this.validateEmail();
        this.validateQq();
        this.validateUsername();
        this.validateTelegram();
        this.validateWechat();
        const hasErrors = Object.values(this.errors).some(error => error !=="");
        if (hasErrors) {
          console.log("存在格式错误");
          return;
        }
        axios.put('/user/updateUserInfo',this.newUserData,{withCredentials:true}).then(res=>{
          if(res.data.success) {
            this.userData=this.newUserData;
            this.editing=false;
            alert("更新成功");
          }else {
            console.log(res.data.message);
            alert("更新失败: "+res.data.message);
          }
        }).catch(error =>{
          console.log("更新失败",error);
        });
      },
    }
  }
</script>



<template>
  <div>
    <h1>个人信息</h1>
    <div v-if="userData">
      <div>
        <p>UUID：{{ userData.uuid }}</p>
        <span v-if="!editing">
          <p>用户名：{{ userData.username }}</p>
          <p>QQ号：{{ userData.qq }}</p>
          <p>邮箱：{{ userData.email }}</p>
          <p>微信号：{{ userData.wechat }}</p>
          <p>TG：{{ userData.telegram }}</p>
          <button @click="editUserInfo">编辑</button>
        </span>
        <span v-else>
          <p>用户名：<input type="text" v-model="newUserData.username" @blur="validateUsername"></p>
          <span v-if="errors.username" style="color:red">{{ errors.username }}</span>
          <p>QQ号：<input type="text" v-model="newUserData.qq" @blur="validateQq"></p>
          <span v-if="errors.qq" style="color:red">{{ errors.qq }}</span>
          <p>邮箱：<input type="text" v-model="newUserData.email" @blur="validateEmail"></p>
          <span v-if="errors.email" style="color:red">{{ errors.email }}</span>
          <p>微信号：<input type="text" v-model="newUserData.wechat" @blur="validateWechat"></p>
          <span v-if="errors.wechat" style="color:red">{{ errors.wechat }}</span>
          <p>TG：<input type="text" v-model="newUserData.telegram" @blur="validateTelegram"></p>
          <span v-if="errors.telegram" style="color:red">{{ errors.telegram }}</span>
          <button @click="saveUserInfo">保存</button>
          <button @click="cancelEdit">取消</button>
        </span>
      </div>
      
      
    </div>
    <div v-else>
      <p>正在加载用户信息...</p>
    </div>
  </div>
</template>

