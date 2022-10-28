<template>
  <div class="photos-container">
    <!--  上传图片  -->
    <el-dialog title="上传图片" :visible.sync="dialogVisible" width="30%">
      <div style="display: flex; margin-bottom: 14px">
        <el-select v-model="mark" clearable placeholder="请选择图片类型">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div>

      <el-upload
        class="upload-demo"
        drag
        :action="url"
        :limit="10"
        :before-upload="beforeUpload"
        :on-success="handlePhotoSuccess"
        :data="{mark}"
        :headers="{token: this.token}"
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip"><el-link type="primary" href="https://devtool.tech/tiny-image" target="_blank">建议上传前请将图片压缩成webp格式</el-link>最多一次上传10张图片,仅支持webp格式</div>
      </el-upload>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: "pictureUploader",
  data() {
    return {
      options: [
        {
          label: "游记",
          value: "游记"
        },
        {
          label: "反馈",
          value: "反馈"
        },
        {
          label: "头像",
          value: "头像"
        }
      ],
      mark: '',
      markId: null,
      url: process.env.baseUrl + '/travel_system/tourist_photos/upload',
      dialogVisible: false
    }
  },

  computed: {
    ...mapState({
      userInfo: state => state.userInfo,
      token: state => state.token,
    }),
  },

  methods: {
    // 图片上传前的回调
    beforeUpload(file) {
      if (this.mark === "") {
        this.$message.error("下拉框内容必须选择!")
        return false;
      }
      const isWEBP = file.type === 'image/webp';
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt5M = file.size / 1024 / 1024 < 5;

      const trueImage = isWEBP || isJPG || isPNG
      if (!trueImage) {
        this.$message.error('上传头像图片只能是 webp、jpg、png 格式!');
      }
      if (!isLt5M) {
        this.$message.error('上传头像图片大小不能超过 1MB!');
      }
      return trueImage && isLt5M;
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

<style scoped lang="less">

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
