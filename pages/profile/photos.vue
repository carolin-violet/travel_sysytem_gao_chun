<template>
  <div class="px-12  py-1" style="max-height:600px">
    <el-row class="ml-3" type="flex" justify="space-between">
      <div>
        <el-select v-model="mark" clearable placeholder="请选择分类">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-button @click="search" type="warning">查询</el-button>
        <el-button @click="reset" type="success">重置</el-button>
      </div>
      <el-button class="mr-12" @click="$refs.pictureUploaderRef.dialogVisible = true" icon="el-icon-picture" circle type="primary"></el-button>
    </el-row>
    <div class="flex flex-wrap content-start mt-3" style="max-height: 500px;">
      <div class="photo-item my-3" v-for="photo in photoList" :key="photo.id">
        <el-image
          :src="photo.url"
          :preview-src-list="urlList"
          lazy>
          <div slot="placeholder" class="image-slot">
            <img src="/loading.gif" alt="">
          </div>
        </el-image>
        <i class="delete-icon el-icon-circle-close" @click="deletePhoto(photo.id)"></i>
      </div>
    </div>
    <!--    分页器-->
    <el-pagination
      background
      @current-change="handleCurrentChange"
      :current-page="current"
      layout="prev, pager, next, total"
      :page-size="limit"
      :total="total">
    </el-pagination>

    <pictureUploader ref="pictureUploaderRef"/>
  </div>
</template>

<script>
import pictureUploader from '@/components/pictureUploader'

export default{
  name: 'picture_repository',
  components: {
    pictureUploader
  },
  data() {
    return {
      options:[
        {
          label: '游记',
          value: '游记'
        },
        {
          label: '反馈',
          value: '反馈'
        },
        {
          label: '头像',
          value: '头像'
        },
      ],
      mark: '',
      current: 1,
      limit: 15,
      total:0,
      dialogVisible: false,
      photoList:[],
      urlList: []
    }
  },

  mounted() {
    this.getPagePictures()
  },

  methods: {

    // 分页查询图片
    async getPagePictures() {
      this.$store.dispatch('hasAuth')
      const res = await this.$axios.get(`/tourist_photos/${this.current}/${this.limit}`, {
        params: {
          mark: this.mark
        }
      })
      this.total = res.data.count
      this.photoList = res.data.rows
      this.urlList = res.data.rows.map(item=> item.url)
    },

    // 根据id删除图片
    deletePhoto(id) {
      this.$store.dispatch('hasAuth')
      this.$confirm(`此操作将永久删除图片${id}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await this.$axios.delete(`/photo-oss/${id}`)
        if (res.code === 20000) {
          this.$message.success('删除成功')
        } else {
          this.$message.error('删除失败')
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },


    // 切换分页
    handleCurrentChange(val) {
      this.current = val
      this.getPagePictures()
    },

    // 根据筛选条件查询
    search() {
      this.getPagePictures()
    },

    // 重置筛选条件
    reset() {
      this.mark = ''
      this.getPagePictures()
    },
  }
}
</script>

<style scoped lang="less">
.photo-item{
  position: relative;
  width:180px;
  height:120px;
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
