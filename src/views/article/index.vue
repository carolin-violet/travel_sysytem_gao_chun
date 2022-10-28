<template>
  <div class="article-container">
    <div class="article-header">
      <strong>新文章</strong>
      <el-button type="primary" @click="handlePublish">发布</el-button>
    </div>
    <el-input placeholder="请输入标题" v-model="article.title"></el-input>
    <mavon-editor
      v-model="value"
      :toolbars="toolbars"
      :ishljs="true"
      ref="mdRef"
      @change="change"
      @imgAdd="imgAdd"
      @imgDel="imgDel"
      :stripIgnoreTag= "false">
      <template slot="left-toolbar-after">
        <button
          type="button"
          @click="handleMorePictures"
          class="op-icon fa fa-mavon-align-left"
          aria-hidden="true"
          title="点击选择图片"
        ></button>
      </template>
    </mavon-editor>

    <el-dialog class="img-select-container" title="选择图片" :visible.sync="dialogVisible">
      <div class="img-selector">
        <div class="img-item" v-for="(photo, index) in photoObjList" :key="photo.id" @click="photoObjList[index].isChecked = !photoObjList[index].isChecked">
          <el-image
            :src="photo.url"
            fit="cover"></el-image>
          <div class="buttons-container" :class="{checked: photo.isChecked }">
            <i class="el-icon-check"></i>
          </div>
        </div>
      </div>
      <el-row type="flex" justify="end">
        <el-col :span="6">
          <el-pagination
            background
            @current-change="handleCurrentChange"
            layout="total, prev, pager, next"
            :total="total">
          </el-pagination>
        </el-col>
      </el-row>
      <el-row type="flex" justify="end">
        <el-row :span="6">
          <el-button type="primary" @click="handleAddPhoto">添加</el-button>
        </el-row>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import photos from "@/api/photos";
import article from "@/api/article";

export default {
  name: "recommendArticle",
  components: {
  },
  data() {
    return {
      dialogVisible: false,
      value: '',
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
      },
      current: 1,
      limit: 10,
      total: null,
      photoObjList: [],
      article: {},
      hasArticle: null,  // 判断之前有没有发布过文章
    }
  },

  mounted() {
    this.getArticle()
  },

  methods: {
    change(value, render) {
      console.log('value', value);
      console.log('render', render);
    },

    // 自定义上传图片
    imgAdd(pos, $file) {
      console.log('imgAdd');
    },
    // 删除图片
    imgDel(pos, $file) {
      console.log('imgDel')
    },

    // 点击按钮打开弹框可以看到更多图片
    handleMorePictures() {
      this.getPhotos()
      this.dialogVisible = true
    },

    // 获取图片
    async getPhotos() {
      let res = await photos.getPhotosByMarkId(this.$route.query.mark_id, this.current, this.limit)
      this.photoObjList = res.data.list.map(item => { item.isChecked = false; return item })
      this.total = res.data.total
    },

    // 图片换页
    handleCurrentChange(val) {
      this.current = val
      this.getPhotos()
    },

    // 添加图片至文章中
    handleAddPhoto() {
      this.dialogVisible = false
      this.photoObjList.forEach(item => {
        if (item.isChecked) {
          this.$refs.mdRef.d_render += `<p><img src="${item.url}" alt="${this.$route.query.name}"/></p>`
          this.$refs.mdRef.d_value += `![${this.$route.query.name}](${item.url})\n\n`
          item.isChecked = false
        }
      })
    },

    // 查询文章
    async getArticle() {
      let res = await article.getArticleById(this.$route.query.mark_id)
      if (res.data.article ) {
        this.hasArticle = true
        this.article = res.data.article
        this.$refs.mdRef.d_render = res.data.article.contentHtml
        this.$refs.mdRef.d_value = res.data.article.contentText
      } else {
        this.hasArticle = false
      }
    },

    // 发布文章
    async publishArticle() {
      this.article.contentHtml = this.$refs.mdRef.d_render
      this.article.contentText = this.$refs.mdRef.d_value
      this.article.mark = this.$route.query.mark
      this.article.markId = this.$route.query.mark_id
      let res = await article.addArticle(this.article)
      if (res.code === 20000) {
        this.$message.success("发布成功!")
        this.$router.go(-1)
      }
    },

    // 修改文章
    async updateArticle() {
      this.article.contentHtml = this.$refs.mdRef.d_render
      this.article.contentText = this.$refs.mdRef.d_value
      let res = await article.updateArticle(this.article)
      if (res.code === 20000) {
        this.$message.success("更新成功!")
        this.$router.go(-1)
      }
    },

    // 点击发布按钮的回调事件，判断应该发布还是更新文章
    handlePublish() {
      this.hasArticle ? this.updateArticle() : this.publishArticle()
    }
  },
}
</script>

<style scoped lang="scss">
.article-container {
  padding: 20px 20px;
  .article-header {
    margin-bottom: 20px;
    strong {
      line-height: 40px ;
      font-size: 20px
    }
    .el-button{
      float: right
    }
  }
  .el-input{
    margin-bottom: 20px;
  }
}

::v-deep .el-dialog{
  width: 950px;
  .img-selector{
    width: 900px;
    display: flex;
    flex-wrap: wrap;
    .img-item{
      position: relative;
      margin: 10px 15px;
      float: left;
      width: 150px;
      height: 150px;
      cursor: pointer;
      .el-image{
        width: 100%;
        height: 100%;
      }
      .buttons-container {
        position: absolute;
        top: 0;
        right: 0;
        width: 20px;
        height: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: rgba(146,146,146,0.5);
        z-index: 1;
        &.checked {
          background-color: rgba(15,210,90,0.7);
        }
        i{
          font-size: 14px;
          cursor: pointer;
          color: #fff;
        }
      }
    }
  }
}
</style>
