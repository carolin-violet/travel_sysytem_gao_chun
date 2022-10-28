<template>
  <div class="site-container min-h-screen py-24 px-24">
    <section class="my-6">
      <div v-for="site in sites" :key="site.id" class="site my-6 py-6 h-64 rounded-xl shadow-xl" style="padding-left: 66px;">
        <div>
          <i class="iconfont icon-flag" style="font-size: 30px;"></i>
          <span class="ml-6" style="font-size: 30px;">{{ site.site }}</span>
        </div>
        <p class="text-xl mt-3">{{ site.start_time }}-{{ site.end_time }}</p>
        <p class="text-2xl mt-3">{{ site.description }}</p>
      </div>
      <comment :mark_id="$route.params.id" class="shadow-xl"/>
      <el-row type="flex" justify="space-between">
        <span></span>
        <el-button @click="toOrder" type="primary">前往预定<i class="el-icon-right"></i></el-button>
      </el-row>
    </section>
  </div>
</template>

<script>
import comment from "@/components/comment";
import { mapGetters } from 'vuex'

export default {
  name: 'route_detail',
  components: {
    comment
  },
  data() {
    return {

    }
  },
  computed: {
    ...mapGetters(['isLogin'])
  },

  methods: {
    // 进入预定页
    toOrder() {
      if (this.isLogin) {
        this.$router.push({
          path: '/order',
          query: {
            commodity_id: this.$route.params.id,
            mark: 'route'
          }
        })
      } else {
        this.$message.warning('请先登录');
      }
    }
  },

  async asyncData(context) {
    const res = await context.app.$axios.get(`/travelRoute/${context.app.context.route.path.split('/')[2]}/sites/findAll`)
    res.data.sites.sort((a, b) => a.sort - b.sort)
    return res.data
  }
}
</script>


<style>

.site-container{
  background-image: url('/站点页背景.jfif');
  background-attachment: fixed;
  background-size: cover;
  background-repeat: no-repeat;
}

.site{
  background-image: radial-gradient(circle at 25px 32px, transparent 13px, rgb(255, 255, 255) 0px);
}
</style>
