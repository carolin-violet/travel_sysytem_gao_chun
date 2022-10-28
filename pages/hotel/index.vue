<template>
  <div class="relative w-full min-h-screen">
    <div class="relative w-full h-auto py-96" style="background: url('/旅馆封面.jfif') no-repeat center center/cover;">
      <div class="absolute left-48 text-white">
        <p class="text-xl font-black tracking-tight">你，距离向往的生活有多远？</p>
        <p class="my-12 text-7xl font-black tracking-widest">在慢山慢水中找回梦的远方</p>
        <p class="text-xl font-thin tracking-wide">慢节奏的生活更能让人静下来，我们在慢中汲取养分，获取对于生活的鉴赏力。</p>
        <p class="text-xl font-thin tracking-wide">高淳特有的“慢调子”让身处慢城的游人放松身心，抛却了都市生活中的烦恼与压力。</p>
      </div>
    </div>
    <photoPanel :image-list="hotels"/>
  </div>
</template>

<script>
import photoPanel from "@/components/photoPanel";

export default {
  name: "hotel",
  components: {
    photoPanel
  },
  data() {
    return {
    }
  },

  mounted() {
    console.log(this.hotels);
  },

  methods: {
  },
  async asyncData(context) {
    let res = await context.app.$axios.get('/hotel/findAll')
    return res.data
  },
  head(){
    return{
      title:'高淳旅馆',
      meta:[{
        'name':'keywords',
        'content': '民宿、酒店、度假村'
      }]
    }
  }

}
</script>

<style scoped>

</style>
