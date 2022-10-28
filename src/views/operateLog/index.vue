<template>
  <div class="app-container">

    <!--   展示表格 -->
    <el-table
      :data="logList"
      @sort-change="handleSort"
      stripe
      border
      fit
      lazy
      style="width: 100%">
      <el-table-column
        type="index"
        label="ID"
        :index="calcIndex"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        label="日志id"
        align="center"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="操作人姓名"
        align="center"
        width="180">
      </el-table-column>
      <el-table-column
        prop="userId"
        label="操作人id"
        align="center"
        show-overflow-tooltip
        width="200">
      </el-table-column>
      <el-table-column
        prop="module"
        label="操作模块"
        align="center"
        show-overflow-tooltip
        width="150">
      </el-table-column>
      <el-table-column
        prop="operate"
        label="操作详情"
        align="center"
        show-overflow-tooltip
        width="200">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="操作时间"
        align="center"
        sortable="custom"
        width="180">
      </el-table-column>
      <el-table-column
        prop="ip"
        label="操作ip"
        align="center"
        show-overflow-tooltip
        width="150">
      </el-table-column>
      <el-table-column
        prop="address"
        label="操作地点"
        align="center"
        show-overflow-tooltip
        width="200">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-rank"
            @click="getLogDetail(scope.$index, scope.row)">查看操作结果详情</el-button>
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

    <el-dialog title="操作结果详情" :visible.sync="dialogVisible">
      <div>{{logDetail}}</div>
    </el-dialog>

  </div>
</template>

<script>
import operateLog from "@/api/operateLog";
import {paramStr} from "@/utils";

export default {
  name: "operateLog",
  data () {
    return {
      logList: [],
      current: 1,
      limit: 15,
      total: null,
      logDetail: null,
      dialogVisible: false
    }
  },

  created() {
    this.getPageLog()
  },

  methods: {
    // 调用接口获取操作记录信息
    async getPageLog(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await operateLog.getPageLog(this.current, this.limit, {orderKey, orderType})
      this.logList = res.data.items
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口获取操作记录结果详情
    async getLogDetail(index, row) {
      this.$nprogress.start()
      let res = await operateLog.getLogDetail(row.id)
      if (res.code === 20000) {
        this.logDetail = res.data.result
        if (this.logDetail.length) this.dialogVisible = true
        this.$message.success('获取成功')
      }
      this.$nprogress.done()
    },

    // 处理分页器分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageLog()
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSort(column) {
      // console.log(column.prop, column.order)
      this.getPageLog(paramStr(column.prop), column.order)
    }
  }
}
</script>

<style scoped>

</style>
