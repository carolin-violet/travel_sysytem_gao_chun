<template>
  <div class="w-full h-auto flex justify-center">
    <div>
      <detailArticle/>
      <comment :mark_id="$route.params.id"/>
      <MyMap :mark_id="$route.params.id" style="width: 100%;height: 500px;" class="my-3" />
      <el-row type="flex" justify="space-between">
        <span></span>
        <el-button @click="toOrder" type="primary">前往预定<i class="el-icon-right"></i></el-button>
      </el-row>
    </div>
  </div>
</template>

<script>
import detailArticle from "@/components/article";
import comment from "@/components/comment";
import MyMap from "@/components/MyMap";
import { mapGetters } from 'vuex'

export default {
  name: "scenic_detail",
  components: {
    detailArticle,
    comment,
    MyMap
  },
  data() {
    return {
    }
  },
  computed: {
    ...mapGetters(['isLogin'])
  },

  methods: {
    toOrder() {
      if (this.isLogin) {
        this.$router.push({
          path: '/order',
          query: {
            commodity_id: this.$route.params.id,
            mark: 'scenic'
          }
        })
      } else {
        this.$message.warning('请先登录');
      }
    }
  }
}
</script>

<style scoped>

</style>
