<template>
  <div class="w-full h-full px-96 pt-8">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="请输入密码" prop="password">
        <el-input v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="再次确认密码" prop="confirmPassword">
        <el-input v-model="form.confirmPassword"></el-input>
      </el-form-item>
      <el-form-item class="text-center">
        <el-button type="primary" @click="updatePassword">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { validPassword } from '@/utils/validate'

export default {
  name: "password",
  data () {
    const validatePassword = (rule, value, callback) => {
      validPassword(value) ? callback() : callback(new Error('密码必须包含数字和大小写字母，可以有特殊字符'))
    }
    const validateConfirmPassword = (rule, value, callback) => {
      value === this.$refs.formRef.model.password ? callback() : callback(new Error('两次密码输入不一致'))
    }
    return{
      form: {
        password: null,
        confirmPassword: null
      },
      rules:{
        password: [
          { required: true, trigger: 'blur', message: '请输入密码' },
          { trigger: 'change', validator:  validatePassword}
        ],
        confirmPassword: [
          { required: true, trigger: 'blur', message: '请输入确认密码' },
          { trigger: 'change', validator:  validateConfirmPassword}
        ],
      }
    }
  },

  methods: {
    updatePassword() {
      this.$store.dispatch('hasAuth')
      this.$refs.formRef.validate(async valid => {
        if (valid) {
          const password = await this.$RsaEncrypt(this.form.password)

          let res = await this.$axios.patch('/tourist/password', { password: password })
          if (res.code === 301) {
            this.$message.success('修改密码成功,请重新登录')
            this.$store.commit('logout')
            this.$router.push('/login')
          } else {
            this.$message.error('修改密码失败')
          }
        } else {
          console.log('error submit')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
