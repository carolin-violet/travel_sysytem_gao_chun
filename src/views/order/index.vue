<template>
  <div class="app-container">
    <el-row type="flex" justify="space-around" style="margin-bottom: 14px">
      <el-col :span="3">
        <el-date-picker
          v-model="date"
          value-format="yyyy-MM-dd"
          placeholder="选择预约日期查询"
          @change="handleDateChange"
          clearable
          :picker-options="pickerOptions">
        </el-date-picker>
      </el-col>
      <el-col :span="3">
        <el-select v-model="isPaid" clearable @change="handleOrderTypeChange" placeholder="请选择已支付或未支付">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="14"></el-col>
      <el-col :span="4">
        <el-input v-model="searchKey" placeholder="请输入订单id或下单人姓名回车查询" prefix-icon="el-icon-search" @change="handleSearch" clearable></el-input>
      </el-col>
    </el-row>


    <!--   展示表格 -->
    <el-table
      :data="orderList"
      @sort-change="handleSort"
      stripe
      border
      fit
      lazy
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="游客id">
              <span>{{ props.row.touristId }}</span>
            </el-form-item>
            <el-form-item label="商品id">
              <span>{{ props.row.commodityId }}</span>
            </el-form-item>
            <el-form-item label="商品名称">
              <span>{{ props.row.title }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        type="index"
        :index="calcIndex"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        label="订单id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        label="预约时间"
        align="center"
        width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.appointmentTime.split(' ')[0] }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="phone"
        label="预留电话"
        align="center"
        width="150">
      </el-table-column>
      <el-table-column
        prop="name"
        label="预约人姓名"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        prop="adult"
        label="成年人数"
        align="center"
        width="120">
      </el-table-column>
      <el-table-column
        prop="child"
        label="未成年人数"
        align="center"
        width="120">
      </el-table-column>
      <el-table-column
        prop="amount"
        label="总金额"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        label="是否支付"
        align="center"
        width="200">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.isPaid | paidFilter"
            disable-transitions>{{ scope.row.isPaid | paidNameFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="订单生成时间"
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


    <!--   修改用的表单 -->
    <el-dialog title="订单信息" :visible.sync="dialogFormVisible">
      <el-form :model="curOrder" label-width="120px">
        <el-form-item label="订单id">
          <el-input v-model="curOrder.id"></el-input>
        </el-form-item>
        <el-form-item label="商品id">
          <el-input v-model="curOrder.commodityId" disabled></el-input>
        </el-form-item>
        <el-form-item label="游客id">
          <el-input v-model="curOrder.touristId" disabled></el-input>
        </el-form-item>
        <el-form-item label="预约时间">
          <el-date-picker
            v-model="curOrder.appointmentTime"
            align="left"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预约人姓名">
          <el-input v-model="curOrder.name"></el-input>
        </el-form-item>
        <el-form-item label="预约人联系方式">
          <el-input v-model="curOrder.phone"></el-input>
        </el-form-item>
        <el-form-item label="成年人数">
          <el-input v-model="curOrder.adult"></el-input>
        </el-form-item>
        <el-form-item label="未成年人数">
          <el-input v-model="curOrder.child"></el-input>
        </el-form-item>
        <el-form-item label="总金额">
          <el-input v-model="curOrder.amount"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleModify">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import order from "@/api/order";
import {paramStr} from "@/utils";

export default {
  name: "Ticket",
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
  data () {
    return {
      orderList: [],
      total: null,
      current: 1,
      limit: 10,
      dialogFormVisible: false,
      curOrder: {},
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      isPaid: "",
      date: "",
      options: [
        {
          label: "已支付",
          value: 1
        },
        {
          label: "未支付",
          value: 0
        }
      ],
      searchKey: ""
    }
  },

  created() {
    this.getPageOrder()
  },

  methods: {
    // 分页获取订单
    async getPageOrder(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await order.getOrder( this.current, this.limit, {orderKey, orderType, isPaid: this.isPaid, date: this.date, searchKey: this.searchKey})
      this.orderList = res.data.rows
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口根据id删除评论
    removeOrder(data) {
      this.$confirm(`此操作将永久删除订单${data.id}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await order.removeOrder(data.id)
        if (res.code == 20000) {
          this.getPageOrder()
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 调用接口修改线路信息
    async updateOrder(data) {
      let res = await order.updateOrder(data)
      if (res.code == 20000) {
        this.dialogFormVisible = false
        this.$message.success("修改成功")
        this.getPageOrder()
      } else {
        this.$message.error("修改失败")
      }
    },

    // 删除按钮
    handleDelete(index, row) {
      this.removeOrder(row)
    },

    // 编辑按钮
    handleEdit(index, row) {
      this.curOrder = Object.assign({}, row)
      this.dialogFormVisible = true
    },

    // 修改数据信息
    handleModify() {
      this.updateOrder(this.curOrder)
    },

    handleCurrentChange(val) {
      this.current = val
      this.getPageOrder()
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSearch() {
      if (this.date === null) this.date = ""
      this.getPageOrder()
    },

    handleSort(column) {
      if (this.date === null) this.date = ""
      this.getPageOrder(paramStr(column.prop), column.order)
    },

    handleOrderTypeChange() {
      if (this.date === null) this.date = ""
      this.getPageOrder()
    },

    handleDateChange() {
      if (this.date === null) this.date = ""
      this.getPageOrder()
    }
  }
}
</script>

<style scoped>

</style>
