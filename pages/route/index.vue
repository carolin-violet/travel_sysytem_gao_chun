<template>
  <div class="route-container w-screen min-h-screen pt-24 px-24">
    <div class="py-12 px-24 shadow-xl">
      <div v-for="route in routes" :key="route.id" class="block flex my-16 overflow-hidden rounded-xl bg-white shadow-xl">
        <div style="width:500px;height:350px;" class="shadow-2xl">
          <img src="@/assets/svg/旅游.svg" :alt="route.title" class="w-full h-full object-cover">
        </div>
        <div class="flex-1 mt-3">
          <div class="px-24" style="height:200px;">
            <h3 class="text-center text-5xl font-black">{{ route.title }}</h3>
            <p class="text-xl font-medium">{{ route.description }} </p>
            <p class="mt-4 font-thin">&nbsp;&nbsp;{{ route.reason }} </p>
          </div>
          <div class="mx-auto mt-12 pt-6 w-96 h-24 text-white text-center text-3xl border-black border-2 cursor-pointer hover:text-black hover:bg-white transition-all duration-500" style="background-color:#262626">
            <nuxt-link :to="`/route/${route.id}`" class="w-full">
              <span class="">查看线路详情</span>
              <i class="el-icon-right"></i>
            </nuxt-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "route",
  components: {
  },
  data() {
    return {

    }
  },

  mounted() {
    // console.log(this.routes);
  },

  methods:{
  },

  async asyncData(context) {
    let res = await context.app.$axios.get('/travelRoute/findAll')
    // console.log(res.data)
    return res.data
  },
  head(){
    return{
      title:'推荐旅游线路',
      meta:[{
        'name':'keywords',
        'content': '固城湖、老街、慢城'
      }]
    }
  }

}
</script>

<style scoped>
.route-container{
  background-image: url('/线路.webp');
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: fixed;
}
</style>
