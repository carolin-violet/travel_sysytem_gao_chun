<template>
  <div class="app-container">
    <div style="display: flex;justify-content: space-between; margin-bottom: 14px;">
      <div style="display: flex">
        <el-select v-model="sentimentType" clearable @change="handleSentimentTypeChange" placeholder="请选择好评或差评">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-input v-model="searchKey" placeholder="请输入评论内容回车查询" prefix-icon="el-icon-search" @change="handleSearch" clearable style="margin-left: 15px;"></el-input>
      </div>
      <div>
        <el-button type="primary" @click="dialogVisible = true">更新评论情感</el-button>
      </div>
    </div>

    <!--   展示表格 -->
    <el-table
      :data="commentList"
      @sort-change="handleSort"
      stripe
      border
      fit
      lazy
      style="width: 100%">
      <el-table-column
        type="index"
        :index="calcIndex"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        label="评论id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        prop="markId"
        label="标志id"
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
        prop="comment"
        label="评论内容"
        align="center"
        show-overflow-tooltip
        width="600">
      </el-table-column>
      <el-table-column
        prop="sentiment"
        label="情感倾向"
        align="center"
        show-overflow-tooltip
        width="150">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.sentiment | sentimentFilter"
            disable-transitions>{{ scope.row.sentiment | sentimentNameFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="评论时间"
        align="center"
        sortable="custom"
        width="160">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
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

    <el-dialog title="更新评论情感" :visible.sync="dialogVisible">
      <h3>{{ time }}s后进行下一次更新</h3>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelTimer">取 消</el-button>
        <el-button type="primary" @click="handleModify" :loading="loading">点击更新评论情感</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import comment from "@/api/comment";
import {paramStr} from "@/utils";

export default {
  name: "Comment",
  filters: {
    sentimentFilter(sentiment) {
      const sentimentMap = {
        2: 'success',
        0: 'danger',
        null: 'info'
      }
      return sentimentMap[sentiment]
    },
    sentimentNameFilter(sentiment) {
      const sentimentNameMap = {
        2: '好评',
        0: '差评',
        null: '未进行分析'
      }
      return sentimentNameMap[sentiment]
    }
  },
  data () {
    return {
      commentList: [],
      current: 1,
      limit: 10,
      total: null,
      loading: false,
      searchKey: "",
      sentimentType: "",
      dialogVisible: false,
      timer: null,
      time: 30,
      options: [
        {
          label: "好评",
          value: 2
        },
        {
          label: "差评",
          value: 0
        }
      ]
    }
  },

  created() {
    this.getPageComment()
  },

  methods: {
    // 调用接口获取所有评论信息
    async getPageComment(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await comment.getPageComment(this.current, this.limit, {orderKey, orderType, searchKey: this.searchKey, sentimentType: this.sentimentType})
      this.commentList = res.data.rows
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口根据id删除评论
    removeComment(data) {
      this.$confirm(`此操作将永久删除评论${data.id}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await comment.removeComment(data.id)
        console.log(res)
        if (res.code == 20000) {
          this.getPageComment()
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 跟新情感倾向
    async updateSentiment() {
      let res = await comment.updateSentiment()
      if (res.code == 20000) {
        this.$message.success("更新2条数据成功")
        this.getPageComment()
      } else {
        this.$message.error("更新失败")
      }
    },

    // 点击更新评论情感按钮
    handleModify() {
      this.loading = true
      this.timer = setInterval(() => {
        if (this.time === 30) this.updateSentiment()
        this.time--
        if (this.time < 1) this.time = 30
      }, 1000)
    },

    // 关闭计时器
    cancelTimer() {
      this.loading = false
      clearInterval(this.timer)
      this.time = 30
      this.dialogVisible = false
    },

    // 删除按钮
    handleDelete(index, row) {
      this.removeComment(row)
    },

    // 处理分页器分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageComment()
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSearch() {
      // console.log(this.searchKey)
      this.getPageComment()
    },

    handleSort(column) {
      // console.log(column.prop, column.order)
      this.getPageComment(paramStr(column.prop), column.order)
    },

    handleSentimentTypeChange() {
      this.getPageComment()
    },
  }
}
</script>

<style scoped lang="scss">
.el-select ::v-deep input {

  width: 200px;

}
</style>
