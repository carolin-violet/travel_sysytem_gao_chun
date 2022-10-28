<template>
  <div class="note-container relative w-screen min-h-screen">

    <!-- 发布按钮 -->
    <el-button v-if="isLogin" @click="drawerVisible = true" class="absolute top-32 right-4 w-16 h-16" type="primary" icon="el-icon-edit" circle></el-button>
    <div class="absolute top-16 left-36 flex text-5xl" style="font-family: 默陌山魂手迹;">
      <div>留</div>
      <div>下</div>
      <div class="mt-1 text-7xl">游</div>
      <div class="mt-1 text-7xl">玩</div>
      <div>体</div>
      <div>验</div>
    </div>

    <section class="w-4/5 h-auto mx-auto pt-32">
      <div v-for="(item, index) in noteList" :key="index" class="w-full h-auto bg-white my-6 rounded-2xl shadow-2xl p-4">
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
        <section class="w-full my-3 text-2xl font-medium" style="font-family: Aa元气满满;">
          {{ item.content }}
        </section>
    <!--    图片列表信息-->
        <section class="w-full h-auto space-x-5">
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
        <!-- 评论 -->
        <comment :mark_id="item.id" class="mt-6"/>
      </div>
    </section>

    <section class="w-screen h-auto">
      <pagination :cur="cur" :limit="limit" :total="total" :continueNum="continueNum" @changePage="changePage" class="w-1/4 mx-auto"/>
    </section>

<!--    上传部分-->
    <el-drawer
      title="发布游记"
      :visible.sync="drawerVisible"
      direction="ltr"
      close-on-press-escape
      destroy-on-close
      :size="800"
      ref="drawer"
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
      <div class="mr-36" >
        <el-form :model="note" label-width="120px">
          <el-form-item label="游记内容:">
            <el-input v-model="note.content" maxlength="255" type="textarea" :rows="12" placeholder="请填写游玩感受"></el-input>
          </el-form-item>
        </el-form>
        <div class="demo-drawer__footer">
          <el-button @click="drawerVisible = false" class="ml-36">取 消</el-button>
          <el-button type="primary" @click="uploadTravelNote" :loading="loading">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
        </div>
      </div>
    </el-drawer>

    <MyPictureSelector mark="游记" @change="getPhotos" ref="pictureSelectorRef"/>
  </div>
</template>

<script>
import pagination from "~/components/pagination";
import MyPictureSelector from "~/components/MyPictureSelector";
import comment from "~/components/comment";
import {mapState, mapGetters} from 'vuex'
import moment from 'moment'

export default {
  name: "note",
  components: {
    pagination,
    MyPictureSelector,
    comment
  },
  data() {
    return {
      cur: 1,
      limit: 5,
      continueNum: 3,
      total: null,
      noteList: [],
      loading: false,
      note: {
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
      userInfo: state => state.userInfo,
    }),
    ...mapGetters(['isLogin'])
  },
  created() {
    this.getPageData()
  },
  methods: {
    // 切换分页
    changePage(page) {
      this.cur = page
      this.getPageData()
    },

    // 上传游记
    async uploadTravelNote() {
      if (!this.note.content) {
        this.$message.warning('请输入内容再上传')
        return;
      }
      this.$store.dispatch('hasAuth')
      this.loading = true
      this.note.photoUrlList = this.selectPhotoList.map(item => item.id)
      this.note.tourist_id = this.userInfo.id
      let res = await this.$axios.post('/travelNote', this.note)
      if (res.code === 20000) {
        this.getPageData()
        this.$message.success('上传游记成功')
        this.note = {}
        this.drawerVisible = false
      } else {
        this.$message.error('上传游记失败')
      }
      this.loading = false
    },

    // 获取游记信息
    async getPageData() {
      let res = await this.$axios.get(`/travelNote/${this.cur}/${this.limit}`)
      this.total = res.data.count
      this.noteList = res.data.rows.map(item=>{item.urlList=item.photos.map(item=>item.url);return item})
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
        title:'高淳游记',
        meta:[{
          'name':'keywords',
          'content': '游客、游记、高淳国际慢城'
        }]
      }
    }
}
</script>

<style scoped lang="less">
.note-container{
  background-image: url('/帐篷露营.webp');
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
