<template>
  <div class="notice-container relative w-screen h-auto pb-6 pt-48 bg-yellow-50">

    <img src="@/assets/svg/公告.svg" class="absolute left-16 top-0" alt="" width="400" height="400">

    <div class="py-16 px-24" >
      <div v-for="notice in noticeList" :key="notice.id" class="block flex my-6 overflow-hidden rounded-xl bg-white shadow-xl">
        <div style="width:500px;height:350px;" class="shadow-xl">
          <el-image :src="notice.cover" lazy class="w-full h-full">
            <div slot="placeholder" class="image-slot">
              <img src="/loading.gif" alt="">
            </div>
            <div slot="error" class="image-slot">
              <img src="@/assets/svg/图片加载失败.svg" alt="">
            </div>
          </el-image>
        </div>
        <div class="flex-1">
          <div class="px-24 mt-3" style="height:200px;">
            <h3 class="text-center text-5xl font-black">{{ notice.name }}</h3>
            <p class="mt-6 text-xl font-medium">{{ notice.description }} </p>
            <el-row type="flex" justify="space-between">
              <span></span>
              <span class="mt-4 font-thin">{{ notice.modify_time }} </span>
            </el-row>
          </div>
          <div class="mx-auto mt-12 pt-6 w-96 h-24 text-white text-center text-3xl border-black border-2 cursor-pointer hover:text-black hover:bg-white transition-all duration-500" style="background-color:#262626">
            <nuxt-link :to="`/notice/${notice.id}`" class="w-full">
              <span class="">查看咨询详情</span>
              <i class="el-icon-right"></i>
            </nuxt-link>
          </div>
        </div>
      </div>
    </div>
    <section class=" w-full h-auto mt-6">
      <pagination :cur="cur" :limit="limit" :total="total" :continueNum="continueNum" :color="'bg-yellow-300'" @changePage="changePage" class="mx-auto w-1/4"/>
    </section>
  </div>
</template>

<script>
import pagination from "@/components/pagination";

export default {
  name: "notice",
  components: {
    pagination
  },
  data() {
    return {
      cur: 1,
      limit: 5,
      continueNum: 3,
      total: null,
      noticeList: []
    }
  },
  created() {
    this.getPageData()
  },
  methods: {
    async getPageData() {
      let res = await this.$axios.get(`/notice/findAll/${this.cur}/${this.limit}`)
      this.noticeList = res.data.noticeList
      this.total = res.data.count
    },
    changePage(page) {
      this.cur = page
      this.getPageData()
    },
  },
  head(){
      return{
        title:'慢城咨询',
        meta:[{
          'name':'keywords',
          'content': '公告、咨询、高淳国际慢城'
        }]
      }
    }
}
</script>

<style scoped>
</style>
