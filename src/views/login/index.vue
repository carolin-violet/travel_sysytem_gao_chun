<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">欢迎登录高淳旅游服务管理系统</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="手机号"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="submit"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>


      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="submit">Login</el-button>

    </el-form>

<!--    滑块验证弹窗-->
    <Vcode :show="isShow" @success="onSuccess" @close="onClose" />
  </div>
</template>

<script>
import { validUsername, validPassword, checkKeyboardContinuousChar } from '@/utils/validate'
import { encryptPassword } from '@/utils/secure';
import Vcode from "vue-puzzle-vcode";

export default {
  name: 'Login',
  components: {
    Vcode,
  },
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('请输入用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (!validPassword(value)) {
        callback(new Error('密码中必须包含大小写 字母、数字、特称字符，至少8个字符，最多32个字符'))
      } 
      else if(checkKeyboardContinuousChar(value)){
        callback(new Error('输入字符不能连续'))
      }
      else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: ['blur', 'change'], validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      isShow: false,
      codeMsg: null,
      flag: 0,  //可登录标志
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    },
    codeMsg: {
      handler: function (val, oldVal) {
        if (val != oldVal) {
          this.flag = 1
        }
      }
    }
  },

  created() {
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    // 登录函数
    handleLogin() {
      // 处理登录验证
      this.$refs.loginForm.validate(async valid => {
        if (valid) {
          this.loading = true
          const data = Object.assign({}, this.loginForm)
          let cipher =  await encryptPassword(data.password)
          data.password = cipher
          this.$store.dispatch('user/login', data).then(() => {
            this.loading = false
            // this.$router.push({path: this.redirect || '/'})
            this.$router.push({path: '/dashboard' || '/'})
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    // 以下为vue-puzzle-vcode的一些函数
    submit() {
      this.isShow = true;
    },

    onSuccess(msg) {
      this.isShow = false; // 通过验证后，需要手动关闭模态框
      this.handleLogin()
    },

    onClose() {
      this.isShow = false;
    },
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<!--这里为可以自己修改的-->
<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  position: relative;
  min-height: 100%;
  width: 100%;
  background: url("/bg.jpg") no-repeat 100% 100%/cover;
  overflow: hidden;
  &:before{
    content: "";
    position: absolute;
    top:0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(255,255,255,0.2);
  }

  .login-form {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    padding: 30px 30px 30px;
    width: 520px;
    max-width: 100%;
    border-radius: 20px;
    overflow: hidden;
    background-color: rgba(0,0,0,0.6);
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 80px;
  }

  .code-button {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 130px;
    height: 35px;
    color: white;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
