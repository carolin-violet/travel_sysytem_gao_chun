<template>
  <div>
    <!-- 图片选择 -->
    <el-dialog :visible.sync="dialogVisible" title="图片选择器(选中为红色则表示图片已使用，本系统一张图片只能使用一次)">
      <div class="img-selector-container">
        <div v-for="(photoObj, keyIndex) in photoObjList" :key="photoObj.key" class="img-selector">
          <h3>{{ photoObj.key }}</h3>
          <div class="list-container">
            <div class="img-item" v-for="(photo, index) in photoObj.photoList" :key="photo.id" @click="handleCheckPhoto(keyIndex, index)" :class="{checked: photo.isChecked,used: photo.mark_id}">
              <el-image :src="photo.url" lazy>
                <div slot="placeholder" class="image-slot">
                  <img src="/loading.gif" alt="">
                </div>
                <div slot="error" class="image-slot">
                  <img src="@/assets/svg/图片加载失败.svg" alt="">
                </div>
              </el-image>
              <div class="buttons-container" :class="{checked: photo.isChecked }">
                <i class="el-icon-check"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 底部按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleConfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: "MyPictureSelector",
  props: ['mark'],
  data() {
    return {
      photoObjList: [],
      checkList: [],
      recordList: [],
      current: 1,
      limit: 10,
      total: null,
      dialogVisible: false,
      loading: false
    }
  },

  watch: {
    selectorMarkId(newVal, oldVal) {
      // console.log(newVal, oldVal)
      this.getPhotos()
    }
  },

  mounted() {
    this.getPhotos()
  },

  methods: {
    // 获取图片
    async getPhotos() {
      // this.$store.dispatch('hasAuth')
      let res = await this.$axios.get('/tourist_photos/days', {params: {mark: this.mark}})
      const data = []
      if (res.code === 20000) {
        let photoObj = res.data.photos
        Object.keys(photoObj).sort((a,b) => b-a).map(key => {data.push({key, photoList: photoObj[key].map(item => {item.isChecked = false; return item})})})
      }
      this.photoObjList = data
    },

    // 点击图片事件
    handleCheckPhoto(keyIndex, index) {
      this.photoObjList[keyIndex].photoList[[index]].isChecked = !this.photoObjList[keyIndex].photoList[[index]].isChecked
      this.filterPhotos()
    },

    // 获取所有点中图片
    filterPhotos(){
      this.checkList = [...this.recordList]
      this.photoObjList.map(item => item.photoList.map(item => {item.isChecked?this.checkList.some(obj => obj.id===item.id)?'':this.checkList.push(item):this.checkList.some(obj => obj.id===item.id)?this.checkList.splice(this.checkList.findIndex(obj=>obj.id===item.id),1):''}))
    },

    // 点击确定按钮
    handleConfirm() {
      this.recordList = [...this.checkList]
      this.$emit('change', this.checkList)
      this.dialogVisible = false
    },

    // 点击取消按钮
    handleCancel() {
      this.checkList = [...this.recordList]
      this.$emit('change', this.checkList)
      this.dialogVisible = false
    },

    // 暴露给父组件用的取消选中的图片
    abolishPhoto(id){
      this.photoObjList.forEach(item => item.photoList.forEach(item => {item.id===id?item.isChecked=!item.isChecked:''}))
      this.filterPhotos()
      this.recordList = [...this.checkList]
      this.$emit('change', this.checkList)
    }
  }
}
</script>

<style scoped lang="less">
.el-dialog {
  width: 950px;
  .img-selector-container{
    width: 900px;
    height: 400px;
    overflow: scroll;
    .img-selector{
      .list-container{
        display: flex;
        flex-wrap: wrap;
        .img-item{
          position: relative;
          margin: 10px 15px;
          width: 180px;
          height: 150px;
          cursor: pointer;
          &.checked{
            .buttons-container{
              background-color: rgba(15,210,90,0.7);
            }
            border: 2px solid rgba(15,210,90,0.7);
          }
          &.used{
            border: 2px solid red;
          }
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
            // &.checked {
            //   background-color: rgba(15,210,90,0.7);
            // }
            i{
              font-size: 14px;
              cursor: pointer;
              color: #fff;
            }
          }
        }
      }
    }
  }
}
</style>
