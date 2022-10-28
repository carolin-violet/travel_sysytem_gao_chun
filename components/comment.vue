<template>
  <div class="mb-12">
    <!-- 评论 -->
    <section>
      <div class="flex">
        <el-avatar :src="userInfo.avatar" :size="45">
          <i class="el-icon-user-solid"></i>
        </el-avatar>
        <input type="text" maxlength="255" :disabled="!isLogin" v-model="comment" :placeholder="isLogin?'欢迎评论':'请先登录'" style="padding:2px 10px;height:50px;outline: none; background-color: #f1f2f3;border: 1px solid #f1f2f3;" class="flex-1 mx-3 rounded-xl hover:bg-white focus:bg-white transition-all duration-200"/>
        <el-button @click="addComment" :disabled="!isLogin" class=" rounded-xl text-white cursor-pointer bg-blue-300 hover:bg-blue-500" style="height:50px;">发送</el-button>
      </div>
      <div v-for="comment in commentList" :key="comment.id" class="flex mt-6 ml-6">
        <el-avatar :src="comment.tourist_avatar" :size="35">
          <i class="el-icon-user-solid"></i>
        </el-avatar>
        <div class="ml-6 flex">
          <div class="flex flex-col">
            <span style="color:#61666d">{{ comment.tourist_nickName }}</span>
            <span style="color:#9499a0">{{ comment.create_time }}</span>
          </div>
          <p class="ml-3 text-xl font-medium" style="color:#18191c;font-family: 风歌忘忧;">{{ comment.comment }}</p>
        </div>
      </div>
    </section>
    <!--    分页器-->
    <el-pagination
      class="mt-3"
      background
      @current-change="handleCurrentChange"
      :current-page="current"
      layout="prev, pager, next, total"
      :page-size="limit"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'

export default{
  name: 'comment',
  props: ['mark_id'],
  data() {
    return {
      current: 1,
      limit: 5,
      total: null,
      commentList: [],
      comment: ''
    }
  },

  created() {
    this.getPageComment()
  },

  computed: {
    ...mapState({
      userInfo: state => state.userInfo
    }),
    ...mapGetters(['isLogin'])
  },

  methods: {
    // 分页获取评论
    async getPageComment() {
      const res = await this.$axios.get(`/comment/findAll/${this.current}/${this.limit}`,{
        params: {
          mark_id: this.mark_id
        }
      })
      this.total = res.data.count
      this.commentList = res.data.commentList
    },

    // 添加评论
        // 添加评论
    async addComment() {
      if (!this.comment) {
        this.$message.warning('请输入有效评论')
        return
      }
      this.$store.dispatch('hasAuth')
      const data = {
        comment: this.comment,
        tourist_id: this.userInfo.id,
        mark_id: this.mark_id
      }
      const res = await this.$axios.post('/comment', data)
      if (res.code === 20000) {
        this.$message.success('评论成功')
        this.comment = ''
        this.getPageComment()
      }
    },

        // 切换分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageComment()
    },
  },
}
</script>

<style scoped>

</style>
