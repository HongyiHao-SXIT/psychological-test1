<template>
  <div>
    <el-card class="login-form-layout">
      <el-form autoComplete="on"
               :model="loginForm"
               :rules="loginRules"
               ref="loginForm"
               label-position="left">
        <div style="text-align: center">
          <svg-icon icon-class="login-mall" style="width: 56px;height: 56px;color: #409EFF"></svg-icon>
        </div>
        <h2 class="login-title color-main">注册</h2>
        <el-form-item prop="username">
          <el-input name="username"
                    type="text"
                    v-model="loginForm.username"
                    autoComplete="on"
                    placeholder="请输入需要注册用户名">
          <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input name="password"
                    :type="pwdType"
                    v-model="loginForm.password"
                    autoComplete="on"
                    placeholder="请输入密码">
          <span slot="prefix">
            <svg-icon icon-class="password" class="color-main"></svg-icon>
          </span>
            <span slot="suffix" @click="showPwd">
            <svg-icon icon-class="eye" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="againPassword">
          <el-input name="againPassword"
                    :type="pwdType"
                    v-model="loginForm.againPassword"
                    autoComplete="on"
                    placeholder="请再次输入密码">
          <span slot="prefix">
            <svg-icon icon-class="password" class="color-main"></svg-icon>
          </span>
            <span slot="suffix" @click="showPwd">
            <svg-icon icon-class="eye" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item>
           <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          <el-select style="width: 100%" v-model="loginForm.gender" placeholder="请选择性别">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </el-select>
        </el-form-item>
        <el-form-item >
           <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          <el-input placeholder="请输入手机号" v-model="loginForm.phone" >
              <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input placeholder="请输入身份证号" v-model="loginForm.idNumber">
              <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input placeholder="请输入邮箱" v-model="loginForm.email">
              <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 60px;text-align: center">
          <el-row>
            <el-col :span="20">
              <el-button style="width: 80%" type="primary" :loading="loading" @click.native.prevent="handleRegister">
                注册
              </el-button>
            </el-col>
            <el-col :span="4">
              <el-link @click="toLogin" type="primary">登录</el-link>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </el-card>
    <img :src="login_center_bg" class="login-center-layout">
  </div>
</template>

<script>
import {isvalidUsername} from '@/utils/validate';
import {setSupport,getSupport,setCookie,getCookie} from '@/utils/support';
import login_center_bg from '@/assets/images/login_bg.svg'
import {createAdmin} from '@/api/login';

export default {

  name: 'login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!isvalidUsername(value)) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    };
    const validatePass = (rule, value, callback) => {
      if (value.length < 3) {
        callback(new Error('密码不能小于3位'))
      } else {
        callback()
      }
    };
    return {
      loginForm: {
        username: '',
        password: '',
        againPassword: '',
        gender:'',
        phone:'',
        idNumber:'',
        email:''
      },
      loginRules: {
        username: [{required: true, trigger: 'blur', validator: validateUsername}],
        password: [{required: true, trigger: 'blur', validator: validatePass}],
        againPassword: [{required: true, trigger: 'blur', validator: validatePass}]
      },
      loading: false,
      pwdType: 'password',
      login_center_bg,
      dialogVisible:false,
      supportDialogVisible:false,
      options:[{
        value:'a',
        label:'男'
      },{
        value:'b',
        label:'女'
      }]
    }
  },
  created() {

  },
  methods: {
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleRegister() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          if(this.loginForm.password!==this.loginForm.againPassword){
            this.$message.error('两次密码不匹配');
            return;
          }
          this.loading = true;
          createAdmin(this.loginForm).then(response => {
            this.$message({
              message: '添加成功！',
              type: 'success'
            });
            setTimeout(()=>{
              this.$router.push({path: '/'})
            },1500)
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('参数验证不合法！');
          return false
        }
      })
    },
    dialogConfirm(){
      this.dialogVisible =false;
      setSupport(true);
    },
    dialogCancel(){
      this.dialogVisible = false;
      setSupport(false);
    },
    toLogin(){
      this.$router.push({path: '/login'},()=>{})

    }
  }
}
</script>

<style scoped>
.login-form-layout {
  position: absolute;
  left: 0;
  right: 0;
  width: 500px;
  margin: 140px auto;
  border-top: 10px solid #409EFF;
}

.login-title {
  text-align: center;
}

.login-center-layout {
  background: #409EFF;
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
}
</style>
