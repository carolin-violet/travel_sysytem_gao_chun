<template>
<!--  登陆页面-->
  <div class="login-container relative w-screen h-screen z-20">
<!--    登录框-->
    <section class="login-box absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 px-16 py-6 bg-white rounded-2xl shadow-2xl z-20">
      <div class="mx-3 my-3">
        <!-- 登录表单 -->
        <transition name="login" v-if="formType === 'login'">
          <el-form ref="LoginFormRef" :model="loginForm" :rules="loginFormRules" class="login-form" key="login-form">
            <h2 class="mb-24 text-5xl text-center font-black tracking-widest">登录</h2>
            <el-form-item prop="telephone">
              <el-input v-model="loginForm.telephone" placeholder="请输入手机号" prefix-icon="el-icon-mobile-phone"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" show-password placeholder="请输入密码" @keyup.enter.native="handleLogin" prefix-icon="el-icon-lock"></el-input>
            </el-form-item>
            <el-button @click="handleLogin" class="tracking-widest font-black">登 录</el-button>
            <el-row type="flex" justify="space-between" class="mt-9">
              <span class="tracking-widest text-xl cursor-pointer" @click="formType = 'password'">忘记密码</span>
              <span></span>
              <span class="tracking-widest text-xl">没有账号?<span @click="formType = 'register'" class="cursor-pointer">&nbsp;立即注册</span></span>
            </el-row>
          </el-form>
        </transition>

        <!-- 注册表单 -->
        <transition name="register" v-else-if="formType === 'register'">
          <el-form ref="registerFormRef" :model="registerForm" :rules="registerFormRules" label-width="120px" class="register-form" key="register-form">
            <h2 class="mb-12 text-5xl text-center font-black tracking-widest">注册</h2>
            <el-form-item label="昵称" prop="nickName">
              <el-input v-model="registerForm.nickName"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="telephone">
              <el-input v-model="registerForm.telephone"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="registerForm.password" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="registerForm.confirmPassword" show-password></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="registerForm.email"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="registerForm.gender">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="生日" prop="birthday">
              <el-date-picker
                v-model="registerForm.birthday"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <areaSelector @change="getRegion" v-model="registerForm.address"/>
            </el-form-item>
            <el-form-item label="验证码" prop="code">
              <el-input v-model="registerForm.code"></el-input>
              <el-button class="absolute right-0" @click="getCode('register')" :disabled="second !== 60">{{ second === 60 ? '点击发送验证码': second+'s' }}</el-button>
            </el-form-item>
            <el-row type="flex" justify="center">
              <el-button @click="handleRegister" class="tracking-widest font-black">注 册</el-button>
            </el-row>
            <el-row type="flex" justify="space-between" class="mt-3">
              <span></span>
              <span class="tracking-widest text-xl">已有账号?<span @click="formType = 'login'" class="cursor-pointer">&nbsp;立即登录</span></span>
            </el-row>
          </el-form>
        </transition>

        <!-- 找回密码表单 -->
        <transition name="password" v-else>
          <div class="reset-container">
            <el-steps :active="active" finish-status="success" simple style="margin-top: 20px">
              <el-step title="验证" ></el-step>
              <el-step title="修改" ></el-step>
              <el-step title="完成" ></el-step>
            </el-steps>
            <div class="mt-32 px-36">
              <el-form v-if="active === 0" ref="resetFormRef" :model="resetForm" :rules="resetFormRules" class="reset-form" key="reset-form">
                <el-form-item prop="telephone">
                  <el-input v-model="resetForm.telephone" placeholder="请输入手机号"></el-input>
                </el-form-item>
                <el-form-item prop="code">
                  <el-input v-model="resetForm.code" placeholder="请输入验证码"></el-input>
                  <el-button class="code-button absolute right-0" @click="getCode('reset')" :disabled="second !== 60">{{ second === 60 ? '点击发送验证码': second+'s' }}</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" class="w-full" @click="checkPhone">下一步</el-button>
                </el-form-item>
              </el-form>
              <el-form v-else-if="active === 1" ref="updatePasswordFormRef" :model="passwordForm" :rules="passwordFormRules" class="password-form" key="password-form">
                <el-form-item prop="password">
                  <el-input v-model="passwordForm.password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword">
                  <el-input v-model="passwordForm.confirmPassword" placeholder="再次确认密码"></el-input>
                </el-form-item>
                <el-form-item class="text-center">
                  <el-button type="primary" @click="retrievePassword" class="w-full">提交</el-button>
                </el-form-item>
              </el-form>
              <div v-else-if="active === 2">
                <el-result icon="success" title="密码修改成功" subTitle="点击下方按钮前往登录">
                  <template slot="extra">
                    <el-button type="primary" size="medium" @click="formType = 'login';next()">前往登录</el-button>
                  </template>
                </el-result>
              </div>
            </div>
          </div>
        </transition>


      </div>
    </section>

    <!-- 背景特效 -->
    <div class="square">
        <ul>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </div>
    <div class="circle">
        <ul>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </div>
  </div>
</template>

<script>
import areaSelector from '@/components/areaSelector'
import { validPhone, validPassword } from '@/utils/validate'

export default {
  name: "login",
  components: {
    areaSelector
  },
  data() {
    const validatePhone = (rule, value, callback) => {
      validPhone(value) ? callback() : callback(new Error('手机号格式错误'))
    }
    const validatePassword = (rule, value, callback) => {
      validPassword(value) ? callback() : callback(new Error('密码必须包含数字和大小写字母，可以有特殊字符'))
    }
    const validateConfirmPassword = (rule, value, callback) => {
      value === this.$refs.registerFormRef.model.password ? callback() : callback(new Error('两次密码输入不一致'))
    }
    const validateConfirmPassword2 = (rule, value, callback) => {
      value === this.$refs.updatePasswordFormRef.model.password ? callback() : callback(new Error('两次密码输入不一致'))
    }
    return {
      formType: 'login',
      timer: null,
      second: 60,
      active: 0,
      checkData: null,
      loginForm: {
        telephone: null,
        password: null
      },
      registerForm: {
        nickName: null,
        telephone: null,
        password: null,
        confirmPassword: null,
        email: null,
        birthday: null,
        gender: null,
        address: null
      },
      resetForm: {
        telephone: null,
        code: null
      },
      passwordForm: {
        password: null,
        confirmPassword: null
      },
      loginFormRules: {
        telephone: [
          { required: true, trigger: 'blur', message: '请输入手机号' },
          { trigger: 'change', validator:  validatePhone}
        ],
        password: [
          { required: true, trigger: 'blur', message: '请输入密码' },
          { trigger: 'change', validator:  validatePassword}
        ]
      },
      registerFormRules: {
        nickName: [
          { required: true, trigger: 'blur', message: '请输入昵称' },
          { max: 25, trigger: 'change', message: '昵称长度不能超过25个字符' },
        ],
        telephone: [
          { required: true, trigger: 'blur', message: '请输入手机号' },
          { trigger: 'change', validator:  validatePhone}
        ],
        password: [
          { required: true, trigger: 'blur', message: '请输入密码' },
          { trigger: 'change', validator:  validatePassword}
        ],
        confirmPassword: [
          { required: true, trigger: 'blur', message: '请输入确认密码' },
          { trigger: 'change', validator:  validateConfirmPassword}
        ],
        email: [
          { required: true, trigger: 'blur', message: '请输入邮箱' },
          { type: 'email', trigger: 'change', message: '邮箱格式错误'}
        ],
        birthday: [
          { required: true, trigger: 'blur', message: '请输入生日' }
        ],
        gender: [
          { required: true, trigger: 'change', message: '请选择性别' }
        ],
        address: [
          { required: true, trigger: 'change', message: '请选择所在地址' }
        ],
        code: [
          { required: true, trigger: 'change', message: '请输入验证码' }
        ],
      },
      resetFormRules: {
        telephone: [
          { required: true, trigger: 'blur', message: '请输入手机号' },
          { trigger: 'change', validator:  validatePhone}
        ],
        code: [
          { required: true, trigger: 'blur', message: '请输入验证码' }
        ]
      },
      passwordFormRules:{
        password: [
          { required: true, trigger: 'blur', message: '请输入密码' },
          { trigger: 'change', validator:  validatePassword}
        ],
        confirmPassword: [
          { required: true, trigger: 'blur', message: '请输入确认密码' },
          { trigger: 'change', validator:  validateConfirmPassword2}
        ],
      }
    }
  },
  methods: {
    // 登录
    handleLogin() {
      this.$refs.LoginFormRef.validate(async valid => {
        if (valid) {
          const password = await this.$RsaEncrypt(this.loginForm.password)

          const data = Object.assign({}, this.loginForm, {password: password})

          let res = await this.$axios.post('/tourist/login', data)
          if (res.code === 20000) {
            this.$message.success("登录成功")
            this.$store.commit("setUserInfo", res.data)
            this.$cookies.set('travel-system-front-token', res.data.token)
            this.$cookies.set('travel-system-front-info', res.data.info)

            this.$router.push({
              path: '/'
            })

          } else {
            this.$message.error("登录失败")
          }
        } else {
          console.log('error submit')
        }
      })
    },
    // 注册
    handleRegister() {
      const data = Object.assign({}, this.registerForm)
      this.$refs.registerFormRef.validate(async valid => {
        if (valid) {
          const res = await this.$axios.post('/tourist/register', data)
          if (res.code === 301) {
            this.$message.success("注册成功")
            this.formType = 'login'
          } else {
            this.$message.error("注册失败")
          }
        } else {
          console.log('error register')
        }
      })
    },
    // 注册时获取手机短信验证码
    getCode(type) {
      let telephone
      type === 'register' ? telephone = this.registerForm.telephone : telephone = this.resetForm.telephone
      if (!telephone) {
        this.$message.warning('请先输入手机号')
      }
      this.$axios.get(`/tourist/code/${telephone}`)
      this.timer = setInterval(()=>{
        this.second--
        if (this.second < 1) {
          clearInterval(this.timer)
          this.second = 60
        }
      }, 1000)
    },

    // 找回密码手机号验证点击下一步获取修改密码的凭证
    checkPhone() {
      this.$refs.resetFormRef.validate(async valid => {
        if (valid) {
          const res = await this.$axios.post('/tourist/code/check', {...this.resetForm})
          if (res.code === 20000) {
            this.checkData = res.data
            this.next()
          } else {
            this.$message.error('重置密码失败')
          }
        } else {
          console.log('submit error');
        }
      })
    },
    // 找回密码
    retrievePassword() {
      this.$refs.updatePasswordFormRef.validate(async valid => {
        if (valid) {
          const password = await this.$RsaEncrypt(this.passwordForm.password)

          const data = Object.assign({}, this.checkData, { password: password })
          let res = await this.$axios.patch('/tourist/password/reset', data)
          if (res.code === 301) {
            this.next()
          } else {
            this.$message.error('重置密码失败')
          }
        } else {
          console.log('error submit')
        }
      })
    },

    next() {
      if (this.active++ >= 2) this.active = 0
    }
  }
}
</script>

<style scoped lang="less">
.login-container{
  background-image: -moz-linear-gradient(180deg, rgb(244, 255, 204), rgb(155, 255, 153));
  background-image: -webkit-linear-gradient(180deg, rgb(244, 255, 204), rgb(155, 255, 153));
  background-image: linear-gradient(180deg, rgb(244, 255, 204), rgb(155, 255, 153));
}

.code-button{
  margin-top: 1px;
  margin-right: 1px;
  width: 130px;
  height: 38px;
}

// 登录表单
.login-form{
  .el-input{
    width: 416px;
  }
  .el-button{
    width: 416px;
    font-size: 20px;
    color: #fafafa;
    border-radius: 20px;
    background-color: #85FFBD;
    background-image: linear-gradient(45deg, #85FFBD 0%, #FFFB7D 100%);
  }
}

// 注册表单
.register-form{
  .el-row{
    .el-button{
    width: 416px;
    font-size: 20px;
    color: #fafafa;
    border-radius: 20px;
    background-color: #85FFBD;
    background-image: linear-gradient(45deg, #85FFBD 0%, #FFFB7D 100%);
    }
  }
}

.reset-container{
  width: 500px;
  height: 400px;
}


// 以下为背景特效
ul li{
    position: absolute;
    border: 1px solid #fff;
    background-color: #fff;
    width: 30px;
    height: 30px;
    list-style: none;
    opacity: 0;
}
.square li{
    top: 40vh;
    left: 60vw;
    /* 执行动画：动画名 时长 线性的 无限次播放 */
    animation: square 10s linear infinite;
}
.square li:nth-child(2){
    top: 80vh;
    left: 10vw;
    /* 设置动画延迟时间 */
    animation-delay: 2s;
}
.square li:nth-child(3){
    top: 80vh;
    left: 85vw;
    /* 设置动画延迟时间 */
    animation-delay: 4s;
}
.square li:nth-child(4){
    top: 10vh;
    left: 70vw;
    /* 设置动画延迟时间 */
    animation-delay: 6s;
}
.square li:nth-child(5){
    top: 10vh;
    left: 10vw;
    /* 设置动画延迟时间 */
    animation-delay: 8s;
}
.circle li{
    bottom: 0;
    left: 15vw;
    /* 执行动画 */
    animation: circle 10s linear infinite;
}
.circle li:nth-child(2){
    left: 35vw;
    /* 设置动画延迟时间 */
    animation-delay: 2s;
}
.circle li:nth-child(3){
    left: 55vw;
    /* 设置动画延迟时间 */
    animation-delay: 6s;
}
.circle li:nth-child(4){
    left: 75vw;
    /* 设置动画延迟时间 */
    animation-delay: 4s;
}
.circle li:nth-child(5){
    left: 90vw;
    /* 设置动画延迟时间 */
    animation-delay: 8s;
}

/* 定义动画 */
@keyframes square {
    0%{
        transform: scale(0) rotateY(0deg);
        opacity: 1;
    }
    100%{
        transform: scale(5) rotateY(1000deg);
        opacity: 0;
    }
}
@keyframes circle {
    0%{
        transform: scale(0) rotateY(0deg);
        opacity: 1;
        bottom: 0;
        border-radius: 0;
    }
    100%{
        transform: scale(5) rotateY(1000deg);
        opacity: 0;
        bottom: 90vh;
        border-radius: 50%;
    }
}
</style>
