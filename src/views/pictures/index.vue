<template>
  <div class="photos-container">
<!--    顶部查询模块-->
    <div class="photos-header">
      <el-input v-model="query.name" clearable placeholder="请输入对应景点、美食、旅馆或公告的名称" style="width: 350px" :disabled="query.mark === '其他'"></el-input>
      <el-select v-model="query.mark" placeholder="请选择分类" clearable style="margin: 0 10px" @change="handleQueryChange">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button type="info" @click="resetQuery">重置查询条件</el-button>
      <el-button icon="el-icon-search" type="primary" @click="handleSearch">查询</el-button>
      <el-button type="success" icon="el-icon-circle-plus-outline" @click="handleAddPhoto">添加图片</el-button>
    </div>

<!--   主体图片部分-->
    <div class="photos-container">

      <div class="photo-item"  v-for="photo in photoList">
        <el-image
          :src="photo.url"
          :preview-src-list="[photo.url]"
          lazy>
          <div slot="placeholder" class="image-slot">
            <img src="/loading.gif" alt="">
          </div>
        </el-image>
        <div class="buttons-container">
          <i class="el-icon-delete" @click="deletePhoto(photo.id)"></i>
        </div>
      </div>
    </div>

    <!--  上传图片  -->
    <el-dialog :visible.sync="dialogVisible" title="上传图片" width="25vw">
      <div style="display: flex; margin-bottom: 14px">
        <el-select v-model="uploadParam.mark" clearable placeholder="请图片类型" @change="handleTypeChange" style="margin-right: 14px">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="uploadParam.markId" clearable placeholder="请选择详细物品" :disabled="uploadParam.mark === '其他'">
          <el-option
            v-for="item in nameList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </div>
      <el-upload
        class="upload-demo"
        drag
        :action="BASE_API + '/travel_system/propagandist-photos'"
        :limit="10"
        :before-upload="beforeUpload"
        :on-success="handlePhotoSuccess"
        :data="uploadParam"
        :headers="headers"
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip"><el-link type="primary" href="https://devtool.tech/tiny-image" target="_blank">上传前请将图片压缩成webp格式</el-link>最多一次上传10张图片,仅支持webp格式</div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
import photos from "@/api/photos";
import {getToken} from "@/utils/auth";

export default {
  name: "index",
  data() {
    return {
      options: [
        {
          label: "美食",
          value: "美食"
        },
        {
          label: "景点",
          value: "景点"
        },
        {
          label: "旅馆",
          value: "旅馆"
        },
        {
          label: "公告",
          value: "公告"
        },
        {
          label: "其他",
          value: "其他"
        }
      ],
      query: {
        mark: "", // 图片类型标识
        name: "" // 图片所属名称或者id
      },
      uploadParam: {
        mark: "",  // 图片类型标识
        markId: ""   // 图片所属名称
      },
      nameList: null,
      markId: null,
      photoList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      BASE_API: process.env.VUE_APP_BASE_API
    }
  },

  mounted() {
    this.getPhotos()
  },

  computed: {
    // 获取token
    headers() {
      return { token: getToken() }
    },

  },

  methods: {

    // 重置查询输入条件
    resetQuery() {
      this.query.name = ""
      this.query.mark = ""
      this.getPhotos()
    },

    handleQueryChange() {
      if (this.query.mark === "其他") {
        this.query.name = ""
      }
    },

    // 搜索图片
    handleSearch() {
      this.getPhotos()
    },

    // 点击添加图片的回调
    handleAddPhoto() {
      this.dialogVisible = true
    },

    // 条件查询图片
    async getPhotos() {
      this.$nprogress.start()
      let res = await photos.getParamsPhotos(this.query.mark, this.query.name)
      this.photoList = res.data.list
      this.$nprogress.done()
    },

    // 根据id删除图片
    deletePhoto(id) {
      this.$confirm(`此操作将永久删除图片${id}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await photos.delPhoto(id)
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

    // 上传时改变图片类型的回调
    async handleTypeChange() {
      if (this.uploadParam.mark === "其他") return;
      let res = await photos.getMarkNames(this.uploadParam.mark)
      if (res.code === 20000) {
        this.nameList = res.data.list.map(item => ({ id: item.id, name: item.name}))
      }
    },

    // 图片上传前的回调
    beforeUpload(file) {
      if (this.uploadParam.mark === "") {
        this.$message.error("下拉框内容必须选择!")
        return false;
      } else if (this.uploadParam.mark !== "其他") {
        if (this.uploadParam.markId === "") {
          return false;
        }
      }
      const isWEBP = file.type === 'image/webp';
      const isLt1M = file.size / 1024 / 1024 < 3;

      if (!isWEBP) {
        this.$message.error('上传图片只能是 webp 格式!');
      }
      if (!isLt1M) {
        this.$message.error('上传图片大小不能超过 3MB!');
      }
      return isWEBP && isLt1M;
    },

    // 图片上传成功的回调
    handlePhotoSuccess(response, file, fileList) {
      // console.log(file) // 返回上传的图片
      // console.log(fileList) // 返回上传的所有图片的列表
      // console.log(response)  // 单个图片返回的响应数据
      console.log(file)
      this.$message.success(`图片${file.name}上传成功!`)
    },
  },
}
</script>

<style scoped lang="scss">

.photos-container{
  padding: 20px 15px;
  .photos-header{
    margin-bottom: 20px;
  }
  .photos-container{
    column-count: 4;//想要排成的列数
    column-gap: 10px;
    .photo-item{
      position: relative;
      .el-image{
        width: 100%;
      }
      &:hover{
        ::v-deep .buttons-container{
          opacity: 1;
        }
      }
      ::v-deep .buttons-container {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 60px;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: rgba(0,0,0,0.8);
        opacity: 0;
        z-index: 1;
        i{
          color: #fff;
          font-size: 24px;
          cursor: pointer;
        }
      }
    }
  }
}
</style>
