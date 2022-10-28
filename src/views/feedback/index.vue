<template>
  <div class="app-container">

    <!--   展示表格 -->
    <el-table
      :data="noteList"
      @sort-change="handleSort"
      stripe
      border
      fit
      lazy
      style="width: 100%">
      <el-table-column
        type="index"
        :index="calcIndex"
        width="50">
      </el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="回复时间">
              <span>{{ props.row.replayTime }}</span>
            </el-form-item>
            <el-form-item label="回复内容">
              <el-input v-if="props.row.isShowInput" type="text" maxlength="255" @change="handleReplay(props.row)" @blur="props.row.isShowInput = false" v-model="props.row.replay"></el-input>
              <span v-else @dblclick.stop="changeShowInput(props.row)">{{ props.row.replay ? props.row.replay : '暂无回复，双击此处添加回复' }}</span>
            </el-form-item>
            <el-form-item label="图片">
              <el-image
                style="width: 100px; height: 100px"
                v-for="(url, index) in props.row.photoUrlList"
                :key="index"
                :src="url"
                :preview-src-list="props.row.photoUrlList">
              </el-image>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        prop="id"
        label="反馈id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        prop="touristId"
        label="游客id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        prop="content"
        label="反馈内容"
        align="center"
        show-overflow-tooltip
        width="800">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="发布时间"
        align="center"
        sortable="custom"
        width="200">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="danger"
            icon="el-icon-delete"
            style="margin: 0 20px"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
import feedback from "@/api/feedback";
import {paramStr} from "@/utils";

export default {
  name: "Feedback",
  data () {
    return {
      noteList: [],
      current: 1,
      limit: 10,
      total: null
    }
  },

  created() {
    this.getPageFeedback()
  },

  methods: {
    // 调用接口获取所有反馈信息
    async getPageFeedback(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await feedback.getPageFeedback(this.current, this.limit, {orderKey, orderType})
      this.noteList = res.data.rows.map(item => {item.isShowInput = false; return item})
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口根据id删除反馈
    removeFeedback(data) {
      this.$confirm(`此操作将永久删除反馈${data.id}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await feedback.removeFeedback(data.id)
        if (res.code == 20000) {
          this.getPageFeedback()
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 删除按钮
    handleDelete(index, row) {
      this.removeFeedback(row)
    },

    // 处理分页器分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageFeedback()
    },

    // 查看图片详情
    handleMorePictures(index,row) {
      this.$router.push({
        path: "/pictures/uploadPictures",
        query: {
          mark_id: row.id   // 传入旅馆id
        }
      })
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSort(column) {
      // console.log(column.prop, column.order)
      this.getPageFeedback(paramStr(column.prop), column.order)
    },

    async handleReplay(row) {
      const res = await feedback.addReplay(row.id, {replay: row.replay})
      res.code === 20000 ? this.$message.success('回复成功') : this.$message.error('回复失败')
      row.isShowInput = false
    },

    changeShowInput(row) {
      row.isShowInput = true
    }
  }
}
</script>

<style scoped>

</style>
