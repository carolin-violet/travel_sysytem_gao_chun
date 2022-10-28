<template>
  <div class="app-container">

    <el-row type="flex" justify="space-around" style="margin-bottom: 14px">
      <el-col>
        <el-button type="primary" @click="handleAdd" icon="el-icon-circle-plus-outline" size="small">添加线路
        </el-button>
      </el-col>
      <el-col :span="4">
        <el-input v-model="searchKey" placeholder="请输入线路名称或id回车查询" prefix-icon="el-icon-search" @change="handleSearch" clearable></el-input>
      </el-col>
    </el-row>

    <!--   展示表格 -->
    <el-table
      :data="routeList"
      @sort-change="handleSort"
      stripe
      border
      fit
      lazy
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="线路描述">
              <span>{{ props.row.description }}</span>
            </el-form-item>
            <el-form-item label="线路推荐原因">
              <span>{{ props.row.reason }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        type="index"
        label="ID"
        :index="calcIndex"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        label="线路id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        prop="title"
        label="线路标题"
        align="center"
        show-overflow-tooltip
        width="600">
      </el-table-column>
      <el-table-column
        prop="price"
        label="单价"
        align="center"
        width="120">
      </el-table-column>
      <el-table-column
        prop="discountPrice"
        label="折扣价"
        align="center"
        width="120">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="添加时间"
        align="center"
        sortable="custom"
        width="200">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="info"
            icon="el-icon-edit"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="small"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button
            size="small"
            type="success"
            @click="handleMoreSites(scope.$index, scope.row)">查看站点</el-button>
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

    <!--   添加删除用的表单 -->
    <el-dialog title="线路信息" :visible.sync="dialogFormVisible">
      <el-form
        :model="curRoute"
        label-width="120px"
        :rules="ruleRoute"
        ref="routeForm"
        v-loading="loading"
        element-loading-text="信息跟新中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(255, 255, 255, 0.2)"
      >
        <el-form-item label="线路id" v-if="flag===0">
          <el-input v-model="curRoute.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="线路标题" prop="title">
          <el-input v-model="curRoute.title"></el-input>
        </el-form-item>
        <el-form-item label="线路描述" prop="description">
          <el-input v-model="curRoute.description" type="textarea" :autosize="{minRows: 1, maxRows: 10}"></el-input>
        </el-form-item>
        <el-form-item label="线路推荐原因" prop="reason">
          <el-input v-model="curRoute.reason" type="textarea" :autosize="{minRows: 1, maxRows: 10}"></el-input>
        </el-form-item>
        <el-form-item label="线路单价" prop="price">
          <el-input v-model="curRoute.price"></el-input>
        </el-form-item>
        <el-form-item label="线路折扣价" prop="discountPrice">
          <el-input v-model="curRoute.discountPrice"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleModify" :loading="buttonLoading">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import touristRoute from "@/api/touristRoute";
import {paramStr} from "@/utils";
import { testPrice } from "@/utils/validate";

export default {
  name: "touristRoute",
  data () {
    return {
      routeList: [],
      dialogFormVisible: false,
      flag: null,
      curRoute: {},
      current: 1,
      limit: 10,
      total: null,
      buttonLoading: false,
      searchKey: "",
      loading: false,
      ruleRoute: {
        title: [
          { required: true, message: '请输入线路名称', trigger: 'blur' },
          { min: 1, max: 20, message: '线路名称长度为1-20个字符', trigger: ['blur', 'change'] },
        ],
        description: [
          { required: true, message: '请输入线路描述', trigger: 'blur' },
          { min: 1, max: 255, message: '线路描述长度为1-255个字符', trigger: ['blur', 'change'] },
        ],
        reason: [
          { required: true, message: '请输入线路详细地址', trigger: 'blur' },
          { min: 1, max: 255, message: '线路推荐原因长度为1-255个字符', trigger: ['blur', 'change'] },
        ],
        price: [
          { required: true, message: '请输入单价', trigger: 'blur' },
          { trigger: ['blur', 'change'], validator: function(rule, value, callback) {
            if (testPrice(value)) callback()
            else callback(new Error('请输入正数并且小数点后最多只有2位'))
          } },
        ],
        discountPrice: [
          { required: true, message: '请输入单价', trigger: 'blur' },
          { trigger: ['blur', 'change'], validator: function(rule, value, callback) {
            if (!testPrice(value)) callback(new Error('请输入正数并且小数点后最多只有2位'))
            else if (value > this.ruleRoute.price) callback(new Error('折扣价不能大于原价'))
            else callback()
          } },
        ],
      }
    }
  },

  created() {
    this.getPageRoute()
  },

  methods: {
    // 调用接口获取所有线路信息
    async getPageRoute(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await touristRoute.getPageRoute(this.current, this.limit, {orderKey, orderType, searchKey: this.searchKey})
      this.routeList = res.data.items.map(item => {
        item.price = item.price.toFixed(2)
        item.discountPrice = item.discountPrice.toFixed(2)
        return item
      })
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口添加线路信息
    addRoute(data) {
      this.$refs.routeForm.validate(async valid => {
        if (valid) {
          this.loading = true
          let res = await touristRoute.addRoute(data)
          this.loading = false
          this.buttonLoading = false
          if (res.code == 20000) {
            this.dialogFormVisible = false
            this.$message.success("添加成功")
            this.getPageRoute()
          } else {
            this.$message.error("添加失败")
          }
        } else {
          return false
        }
      })
    },

    // 调用接口修改线路信息
    updateRoute(data) {
      this.$refs.routeForm.validate(async valid => {
        if (valid) {
          this.loading = true
          let res = await touristRoute.updateRoute(data)
          this.loading = false
          this.buttonLoading = false
          if (res.code == 20000) {
            this.dialogFormVisible = false
            this.$message.success("修改成功")
            this.getPageRoute()
          } else {
            this.$message.error("修改失败")
          }
        } else {
          return false
        }
      })
    },

    // 调用接口根据id删除线路
    removeRoute(data) {
      this.$confirm(`此操作将永久删除${data.name}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await touristRoute.removeRoute(data.id)
        if (res.code == 20000) {
          this.getPageRoute()
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 编辑按钮
    handleEdit(index, row) {
      this.curRoute = Object.assign({}, row)
      this.flag = 0
      this.dialogFormVisible = true
    },

    // 删除按钮
    handleDelete(index, row) {
      this.removeRoute(row)
    },


    // 添加按钮
    handleAdd() {
      this.curRoute = {}
      this.flag = 1
      this.dialogFormVisible = true
    },

    // 确定按钮, flag为1就添加线路，为0就修改线路
    handleModify() {
      this.buttonLoading = true
      if (this.flag === 1) {
        this.addRoute(this.curRoute)
      } else {
        this.updateRoute(this.curRoute)
      }
    },

    // 查看站点详情按钮
    handleMoreSites(index, row) {
      this.$router.push({
        path: "/touristRoute/routeSites",
        query: {
          route_id: row.id
        }
      })
    },

    // 处理分页器分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageRoute()
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSearch() {
      // console.log(this.searchKey)
      this.getPageRoute()
    },

    handleSort(column) {
      // console.log(column.prop, column.order)
      this.getPageRoute(paramStr(column.prop), column.order)
    }

  },
}
</script>

<style scoped>

</style>
