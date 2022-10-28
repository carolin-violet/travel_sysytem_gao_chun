<template>
  <div class="flex justify-center w-full h-full  pt-8">
    <el-form label-width="80px" :model="curInfo">

      <el-form-item label="游客头像">
        <div @click="$refs.pictureSelectorRef.dialogVisible=true" class="cursor-pointer">
          <div v-if="curInfo.avatar" class="relative group">
            <div class="absolute w-24 h-24 rounded-full bg-black bg-opacity-60 flex justify-center items-center opacity-0 group-hover:opacity-100">
              <i class="el-icon-edit text-2xl text-white"></i>
            </div>
            <img  :src="curInfo.avatar" class="avatar h-24 w-24 rounded-full">
          </div>
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </div>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="curInfo.nickName" clearable></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="curInfo.telephone" clearable></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="curInfo.email" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="curInfo.gender">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="生日">
        <el-date-picker
          v-model="curInfo.birthday"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="地址">
        <areaSelector v-model="curInfo.address" :echo="echo"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="changeInfo">保存</el-button>
      </el-form-item>
    </el-form>
    <MyPictureSelector mark="头像" @change="getPhotos" ref="pictureSelectorRef"/>
  </div>
</template>

<script>
import areaSelector from '@/components/areaSelector'
import MyPictureSelector from "~/components/MyPictureSelector";

export default {
  name: "userInfo",
  components: {
    areaSelector,
    MyPictureSelector,
  },
  data () {
    return{
      curInfo: {},
      echo: '',
    }
  },

  mounted() {
    this.curInfo = {...this.$cookies.get('travel-system-front-info')}
    this.echo = JSON.parse(this.curInfo.address)
    delete this.curInfo.password
  },

  methods: {
    // 更新用户信息
    async changeInfo() {
      this.$store.dispatch('hasAuth')
      const data = Object.assign({}, this.curInfo)
      let res = await this.$axios.put('/tourist/baseInfo', data)
      if (res.code === 20000) {
        this.$message.success('修改信息成功,请重新登录')
        this.$store.commit('logout')
        this.$router.push('/login')
      } else {
        this.$message.error('修改信息失败')
      }
    },

    // 获取图片选择器中选择的图片
    getPhotos(data){
      console.log(data[0].url);
      this.curInfo.avatar = data[0].url
    },

        // 选择地址获取数据
    getRegion(region) {
      console.log(region)
    }
  }
}
</script>

<style scoped>
</style>
