<template>
  <div>
    <el-dialog class="img-select-container" title="选择图片" :visible.sync="dialogVisible">
      <div class="img-selector">
        <div class="img-item" v-for="(photo, index) in photoObjList" :key="photo.id" @click="handleCheckPhoto(index)">
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
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleAddPhoto">添加</el-button>
        </el-row>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import photos from "@/api/photos";

export default {
  name: "PhotoSelector",
  props: ['selectorMarkId'],
  data() {
    return {
      photoObjList: [],
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
  methods: {
    // 获取图片
    async getPhotos() {
      let res = await photos.getPhotosByMarkId(this.selectorMarkId, this.current, this.limit)
      // console.log("res:", res)
      this.photoObjList = res.data.list.map(item => { item.isChecked = false; return item })
      this.total = res.data.total
    },

    handleCheckPhoto(index) {
      let count = 0
      this.photoObjList.map(item => {
        if (item.isChecked) count++;
      })
      if (count < 1 || (count===1 && this.photoObjList[index].isChecked)) {
        this.photoObjList[index].isChecked = !this.photoObjList[index].isChecked
      }  else {
        this.$message.error("只能选一张图片")
      }

    },

    // 图片换页
    handleCurrentChange(val) {
      this.current = val
      this.getPhotos()
    },

    // 获取单独一张图片
    handleAddPhoto() {
      this.dialogVisible = false
      this.photoObjList.forEach(item => {
        if (item.isChecked) {
          this.$emit("getPhoto", item)
          item.isChecked = false
        }
      });
    },

  }
}
</script>

<style scoped lang="scss">
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
