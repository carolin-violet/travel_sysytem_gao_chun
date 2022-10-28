<template>
  <div class="w-full h-auto">

<!--    订单表格-->
    <el-table
      :data="orderList"
      border
      style="width: 100%">
      <el-table-column
        label="下单时间"
        align="center"
        width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ formatDateTime(scope.row.create_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="title"
        label="商品名称"
        align="center"
        show-overflow-tooltip
        width="180">
      </el-table-column>
      <el-table-column
        label="预约时间"
        align="center"
        width="150">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ formatDate(scope.row.appointment_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="预约人姓名"
        align="center"
        show-overflow-tooltip
        width="100">
        <template slot-scope="scope">
          <el-tag type="primary">{{ scope.row.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="phone"
        label="预约电话"
        align="center"
        width="150">
      </el-table-column>
      <el-table-column
        prop="adult"
        label="成年人数"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="child"
        label="未成年人数"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="amount"
        label="总金额"
        align="center"
        width="100">
      </el-table-column>
      <el-table-column
        label="是否支付"
        align="center"
        >
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.is_paid | paidFilter"
            disable-transitions>{{ scope.row.is_paid | paidNameFilter }}</el-tag>
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
  name: "order",
  data() {
    return {
      orderList: [],
      current: 1,
      limit: 5,
      total: 0
    }
  },
  filters: {
    paidFilter(paid) {
      const paidMap = {
        1: 'success',
        0: 'danger'
      }
      return paidMap[paid]
    },
    paidNameFilter(paid) {
      const paidNameMap = {
        1: '已支付',
        0: '未支付'
      }
      return paidNameMap[paid]
    }
  },

  created() {
    this.getPageOrder()
  },

  methods: {
    async getPageOrder() {
      this.$store.dispatch('hasAuth')
      let res = await this.$axios.get(`/order/${this.current}/${this.limit}`)
      this.orderList = res.data.rows
      this.total = res.data.count
    },

    handleCurrentChange(val) {
      this.current = val
      this.getPageOrder()
    },

    // 格式化日期
    formatDateTime(time) {
      const publishTime = moment(time).utcOffset(480).format('yyyy-MM-DD HH:mm:ss')
      return publishTime
    },

    // 格式化日期
    formatDate(time) {
      const publishTime = moment(time).utcOffset(480).format('yyyy-MM-DD')
      return publishTime
    },

  }
}
</script>

<style scoped>

</style>
