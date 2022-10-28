<template>
  <div class="feedback-container relative w-screen h-auto min-h-screen">

    <!-- 发布按钮 -->
    <el-button v-if="isLogin" @click="drawerVisible = true" class="absolute top-32 right-4 w-16 h-16" type="primary" icon="el-icon-edit" circle></el-button>

    <div class="absolute ml-48 mt-6 text-5xl font-medium text-white" style="font-family: 默陌山魂手迹;">
      <span>歡迎提供寶貴的</span>
      <span class="text-7xl">投訴</span>
      <span>或</span>
      <span class="text-7xl">建議</span>
    </div>

    <section class="w-4/5 h-auto mx-auto pt-32">
      <div v-for="(item, index) in feedbackList" :key="index" class="w-full h-auto bg-white my-6 rounded-2xl shadow-2xl p-4">
    <!--    头像、昵称、时间信息-->
        <section class="w-full h-auto text-xl flex">
          <div style="width: 50px;height: 50px;">
            <el-image class="w-full h-full object-cover rounded-full" :src="item.tourist_avatar"></el-image>
          </div>
          <div class="ml-3 w-auto h-20 flex flex-col">
            <span>{{ item.tourist_nickName }}</span>
            <span>{{ formatDateTime(item.create_time) }}</span>
          </div>
        </section>
        <!--    描述内容-->
        <section class="w-full h-auto text-2xl">
          {{ item.content }}
        </section>
    <!--    图片列表信息-->
        <section class="w-full h-auto my-3 space-x-5">
          <el-image
            v-for="photo in item.photos"
            :key="photo.id"
            style="width: 100px; height: 100px"
            lazy
            :src="photo.url"
            :preview-src-list="item.urlList">
            <div slot="placeholder" class="image-slot">
              <img src="/loading.gif" alt="">
            </div>
          </el-image>
        </section>

        <el-divider></el-divider>
        <!-- 官方回复 -->
        <section v-if="item.replay_time && item.replay" class="mb-6">
          <el-row type="flex" justify="space-between">
            <span class="text-xl"><span class="text-3xl">官方回复：</span>{{ item.replay }}</span>
            <span><i class="el-icon-time"></i>{{ formatDateTime(item.replay_time) }}</span>
          </el-row>
        </section>
      </div>
    </section>

    <section class="w-screen h-auto">
      <pagination :cur="cur" :limit="limit" :total="total" :continueNum="continueNum" @changePage="changePage" class="w-1/4 mx-auto"/>
    </section>

<!--    上传部分-->
    <el-drawer
      title="发布反馈"
      :visible.sync="drawerVisible"
      direction="ltr"
      close-on-press-escape
      destroy-on-close
      :size="800"
    >
      <!--  上传图片按钮  -->
      <div class="pl-28 pb-16">
        <el-button @click="$refs.pictureSelectorRef.dialogVisible=true" icon="el-icon-plus" type="primary">选择图片</el-button>
      </div>

      <div class="flex flex-wrap mt-3" style="max-height:500px;overflow: scroll;">
        <div class="photo-item" v-for="photo in selectPhotoList" :key="photo.id">
          <el-image
            :src="photo.url"
            :preview-src-list="[photo.url]"
            lazy>
            <div slot="placeholder" class="image-slot">
              <img src="/loading.gif" alt="">
            </div>
          </el-image>
          <i class="delete-icon el-icon-circle-close" @click="deletePhoto(photo.id)"></i>
        </div>
      </div>


      <!-- 上传主题内容 -->
      <div class="mr-36">
        <el-form :model="feedback" label-width="120px">
          <el-form-item label="反馈内容:">
            <el-input v-model="feedback.content" maxlength="255" type="textarea" :rows="12" placeholder="请填写投诉或建议"></el-input>
          </el-form-item>
        </el-form>
        <div class="demo-drawer__footer">
          <el-button @click="drawerVisible = false" class="ml-36">取 消</el-button>
          <el-button type="primary" @click="uploadFeedback" :loading="loading">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
        </div>
      </div>
    </el-drawer>

    <MyPictureSelector mark="反馈" @change="getPhotos" ref="pictureSelectorRef"/>
  </div>
</template>

<script>
import pagination from "~/components/pagination";
import MyPictureSelector from "~/components/MyPictureSelector";
import {mapState, mapGetters} from 'vuex'
import moment from "moment"

export default {
  name: "note",
  components: {
    pagination,
    MyPictureSelector
  },
  data() {
    return {
      cur: 1,
      limit: 5,
      continueNum: 3,
      total: null,
      feedbackList: [],
      loading: false,
      feedback: {
        tourist_id: null,
        content: null,
        photoUrlList: []
      },
      selectPhotoList: [],
      drawerVisible: false,
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.userInfo
    }),
    ...mapGetters(['isLogin'])
  },
  created() {
    this.getPageData()
  },
  methods: {
    // 分页
    changePage(page) {
      this.cur = page
      this.getPageData()
    },

    // 上传游记
    async uploadFeedback() {
      if (this.feedback.content === '') {
        this.$message.warning('请输入内容')
        return;
      }
      this.$store.dispatch('hasAuth')
      this.loading = true
      this.feedback.photoUrlList = this.selectPhotoList.map(item => item.id)
      this.feedback.tourist_id = this.userInfo.id
      let res = await this.$axios.post('/feedback', this.feedback)
      if (res.code === 20000) {
        this.getPageData()
        this.$message.success('上传游记成功')
        this.feedback = {}
        this.drawerVisible = false
      } else {
        this.$message.error('上传游记失败')
      }
      this.loading = false
    },

    // 获取反馈信息
    async getPageData() {
      let res = await this.$axios.get(`/feedback/${this.cur}/${this.limit}`)
      this.total = res.data.count
      this.feedbackList = res.data.rows.map(item=>{item.urlList=item.photos.map(item=>item.url);return item})
    },

    // 获取图片选择器中选择的图片
    getPhotos(data){
      this.selectPhotoList = data
    },

    // 取消选中的图片
    deletePhoto(id){
      this.$refs.pictureSelectorRef.abolishPhoto(id)
    },

    // 格式化日期
    formatDateTime(time) {
      const publishTime = moment(time).utcOffset(480).format('yyyy-MM-DD HH:mm:ss')
      return publishTime
    },
  },
  head(){
      return{
        title:'高淳投诉与建议',
        meta:[{
          'name':'keywords',
          'content': '游客、游记、高淳国际慢城'
        }]
      }
    }
}
</script>

<style scoped lang="less">
.feedback-container{
  background-image: url('/服务中心.webp');
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: fixed;
}

.photo-item{
  position: relative;
  width:200px;
  height:150px;
  margin:0 10px;
  .el-image{
    width: inherit;
    height: inherit;
  }
  .delete-icon{
    position: absolute;
    top: 0;
    right: 0;
    font-size: 24px;
    cursor: pointer;
    color: rgba(0,0,0,0.8);
  }
}
</style>
