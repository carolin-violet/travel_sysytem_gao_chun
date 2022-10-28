<template>
  <div class="w-screen h-auto">
    <footer class="relative w-auto h-48 bg-gray-200">
<!--      备案号信息等-->
      <section class="absolute w-1/5 h-28 left-1/2 transform -translate-x-1/2 top-1/2 -translate-y-1/2 space-y-3 text-center pt-2 text-xl text-black">
        <span class="block ">Copyright©2022</span>
        <span class="block ">Design by Carolin Violet</span>
        <span class="block ">苏ICP备********号</span>
      </section>

<!--      微信二维码、微博号等-->
      <section class="absolute w-1/3 h-36 left-2/3 top-1/2 transform -translate-y-1/2 flex -space-x-8 pl-24 pt-6 text-center">
        <div class="w-32">
          <i class="iconfont icon-weixin text-4xl cursor-pointer"></i>
          <div class="weixin-code w-24 h-24 mx-auto">
            <img src="http://www.chinacittaslow.com/statics/common/code_img.png" alt="">
          </div>
        </div>

        <div class="w-32">
          <i class="iconfont icon-weibo text-4xl cursor-pointer" @click="toWeibo"></i>
          <div class="w-24 h-24 mx-auto"></div>
        </div>

        <div class="w-96 text-left pl-12">
          <i class="iconfont icon-dianhua text-4xl"></i>
          <strong>联系电话：18314014691</strong>
        </div>
      </section>
    </footer>
  </div>
</template>

<script>
export default {
  name: "MyFooter",
  methods: {
    toWeibo() {
      window.open("https://weibo.com/u/2611597163", "_blank")
    }
  }
}
</script>

<style scoped>
.weixin-code > img {
  visibility: hidden;
}
.icon-weixin:hover ~ .weixin-code > img {
  visibility: visible;
}
</style>
