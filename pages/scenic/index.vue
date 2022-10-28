<template>
  <div class="relative w-full min-h-screen">
    <div class="relative py-12">
      <el-carousel :interval="3000" autoplay type="card" height="500px">
        <el-carousel-item>
          <img src="/景点页轮播图/1.jpg" class="w-full h-full object-cover" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="/景点页轮播图/2.jpg" class="w-full h-full object-cover" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="/景点页轮播图/3.jpg" class="w-full h-full object-cover" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="/景点页轮播图/4.jpg" class="w-full h-full object-cover" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="/景点页轮播图/5.jpg" class="w-full h-full object-cover" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="/景点页轮播图/6.jfif" class="w-full h-full object-cover" alt="">
        </el-carousel-item>
      </el-carousel>
      <p class="absolute bottom-0 left-48 transform translate-y-1/2 text-7xl tracking-widest font-black z-10" style="font-family:电影海报字体">在慢中探索与沉思，在沉思中奔赴梦的远方</p>
    </div>

    <photoPanel :image-list="scenicSpots"/>
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
    console.log(this.scenicSpots);
  },

  methods: {
  },
  async asyncData(context) {
    let res = await context.app.$axios.get('/scenicSpot/findAll')
    return res.data
  },
  head(){
    return{
      title:'高淳景点',
      meta:[{
        'name':'keywords',
        'content': '高淳老街、游子山、水慢城、固城湾、桠溪慢城'
      }]
    }
  }

}
</script>

<style>
.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 500px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
