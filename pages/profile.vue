<template>
  <div class="relative w-full min-h-screen z-30" style="background-color: #8EC5FC;background-image: linear-gradient(62deg, #8EC5FC 0%, #E0C3FC 100%);">
<!--    主体部分-->
    <section class="w-2/3 absolute top-48 left-0 right-0 mx-auto flex rounded-2xl shadow-2xl" style="min-height: 600px">
<!--      左边侧边栏-->
      <aside class="text-left bg-gray-100 rounded-l-2xl" style="width: 150px">
        <div class="border-b-2" style="height: 48px; line-height: 48px; padding-left: 30px">
          <span class="text-gray-400 text-xl">个人中心</span>
        </div>
        <div class="cursor-pointer" style="height: 48px; line-height: 48px; padding-left: 30px" @click="changeNav('/profile')" :class="{'bg-blue-300': curTitle === '首页', 'text-white': curTitle === '首页'}">
          <i class="iconfont icon-home"></i>
          <nuxt-link to="/profile">首页</nuxt-link>
        </div>
        <div class="cursor-pointer" style="height: 48px; line-height: 48px; padding-left: 30px" @click="changeNav('/profile/userInfo')" :class="{'bg-blue-300': curTitle === '个人信息', 'text-white': curTitle === '个人信息'}">
          <i class="iconfont icon-user"></i>
          <nuxt-link to="/profile/userInfo">个人信息</nuxt-link>
        </div>
        <div class="cursor-pointer" style="height: 48px; line-height: 48px; padding-left: 30px" @click="changeNav('/profile/password')" :class="{'bg-blue-300': curTitle === '修改密码', 'text-white': curTitle === '修改密码'}">
          <i class="iconfont icon-password"></i>
          <nuxt-link to="/profile/userInfo">修改密码</nuxt-link>
        </div>
        <div class="cursor-pointer" style="height: 48px; line-height: 48px; padding-left: 30px" @click="changeNav('/profile/order')" :class="{'bg-blue-300': curTitle === '历史订单', 'text-white': curTitle === '历史订单'}">
          <i class="iconfont icon-yuding"></i>
          <nuxt-link to="/profile/order">历史订单</nuxt-link>
        </div>
        <div class="cursor-pointer" style="height: 48px; line-height: 48px; padding-left: 30px" @click="changeNav('/profile/note')" :class="{'bg-blue-300': curTitle === '历史游记', 'text-white': curTitle === '历史游记'}">
          <i class="iconfont icon-youji"></i>
          <nuxt-link to="/profile/note">历史游记</nuxt-link>
        </div>
        <div class="cursor-pointer" style="height: 48px; line-height: 48px; padding-left: 30px" @click="changeNav('/profile/feedback')" :class="{'bg-blue-300': curTitle === '历史反馈', 'text-white': curTitle === '历史反馈'}">
          <i class="iconfont icon-wodefankui"></i>
          <nuxt-link to="/profile/feedback">历史反馈</nuxt-link>
        </div>
        <div class="cursor-pointer" style="height: 48px; line-height: 48px; padding-left: 30px" @click="changeNav('/profile/photos')" :class="{'bg-blue-300': curTitle === '图库', 'text-white': curTitle === '图库'}">
          <i class="iconfont icon-tuku"></i>
          <nuxt-link to="/profile/feedback">图库</nuxt-link>
        </div>
      </aside>

<!--      右边主体部分-->
      <section class="flex-1 bg-white rounded-r-2xl">
        <div class="w-full flex items-center border-b-2" style="height: 48px; line-height: 48px; padding-left: 30px">
          <div class="bg-blue-300" style="width: 4px;height: 16px; border-radius: 4px"></div>
          <span class="ml-3 text-xl text-blue-300">{{ curTitle }}</span>
        </div>
        <nuxt-child></nuxt-child>
      </section>
    </section>
  </div>
</template>

<script>
export default {
  name: "profile",
  data () {
    return {

    }
  },

  computed: {
    curTitle() {
      let title
      switch (this.$route.fullPath.split('/')[2]) {
        case undefined: title = '首页';break;
        case 'userInfo': title = '个人信息';break;
        case 'order': title = '历史订单';break;
        case 'password': title = '修改密码';break;
        case 'note': title = '历史游记';break;
        case 'feedback': title = '历史反馈';break;
        case 'photos': title = '图库';break;
      }
      return title
    }
  },

  mounted() {
    this.$store.dispatch('hasAuth')
  },

  methods: {
    changeNav(url) {
      this.$router.push(url)
    }
  }
}
</script>

<style scoped>

</style>
