<template>
  <div class="w-full h-auto">

<!--    订单表格-->
    <el-table
      :data="noteList"
      border
      fit
      @cell-dblclick="handleShowInput"
      style="width: 100%">
      <el-table-column
        label="发布时间"
        align="center"
        width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ formatDateTime(scope.row.create_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="游记内容"
        align="center"
        show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="!scope.row.showInput">{{ scope.row.content }}</span>
          <el-input v-else  maxlength="255" v-model="scope.row.content" @change="handleChange(scope.row)" @blur="handleBlur(scope.row,$event)" :ref="`inputRef${scope.row.id}`"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        label="照片"
        align="center"
        width="80">
        <template slot-scope="scope">
          <el-image
            :src="scope.row.photos.length?scope.row.photos[0].url:''"
            :preview-src-list="scope.row.urlList"
            lazy="lazy">
            <div slot="placeholder" class="image-slot">
              <img src="/loading.gif" alt="">
            </div>
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="danger"
            style="margin: 0 20px"
            @click="handleDelete(scope.$index, scope.row)"><i class="el-icon-delete"></i>删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    分页器-->
    <el-pagination
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
import moment from 'moment'

export default {
  name: "note",
  data() {
    return {
      noteList: [],
      current: 1,
      limit: 5,
      total: 0
    }
  },

  created() {
    this.getPageNote()
  },

  methods: {
    async getPageNote() {
      this.$store.dispatch('hasAuth')
      let res = await this.$axios.get(`/travelNote/self/${this.current}/${this.limit}`)
      this.noteList = res.data.rows.map(item => {item.urlList=item.photos.map(item=>item.url?item.url:'');item.showInput=false;return item})
      this.total = res.data.count
    },

    handleDelete(index ,row) {
      this.$store.dispatch('hasAuth')
      this.$confirm(`此操作将永久删除游记${row.id}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await this.$axios.delete(`/travelNote/${row.id}`)
        if (res.code == 20000) {
          this.getPageNote()
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageNote()
    },

    // 格式化日期
    formatDateTime(time) {
      const publishTime = moment(time).utcOffset(480).format('yyyy-MM-DD HH:mm:ss')
      return publishTime
    },

    // 处理输入框的双击显示
    handleShowInput(row, column, cell, event) {
      row.showInput = true
      this.$nextTick(() => {
        this.$refs[`inputRef${row.id}`].focus()
      })
    },

    // 输入框失焦或按回车处理(仅当改变输入框内容时触发)
    async handleChange(row) {
      this.$store.dispatch('hasAuth')
      console.log(row.content);
      const res = await this.$axios.patch(`/travelNote/${row.id}`, {content: row.content})
      res.code === 20000 ? this.$message.success('修改成功') : this.$message.error('修改失败')
    },

    // 输入框失焦
    handleBlur(row, event) {
      row.showInput = false
    }
  }
}
</script>

<style scoped>

</style>
