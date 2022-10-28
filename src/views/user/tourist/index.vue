<template>
  <div class="app-container">

    <el-row type="flex" justify="space-around" style="margin-bottom: 14px">
      <el-col>
        <el-select v-model="genderType" clearable @change="handleGenderTypeChange" placeholder="请选择男性或女性">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-input v-model="searchKey" placeholder="请输入游客昵称或id回车查询" prefix-icon="el-icon-search" @change="handleSearch" clearable></el-input>
      </el-col>
    </el-row>

    <!--   展示表格 -->
    <el-table
      :data="touristList"
      @sort-change="handleSort"
      stripe
      border
      fit
      lazy
      style="width: 100%">
      <el-table-column
        label="ID"
        type="index"
        :index="calcIndex"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        label="游客id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        label="游客昵称"
        align="center"
        show-overflow-tooltip
        width="200  ">
        <template slot-scope="scope">
          <el-tag type="success">{{ scope.row.nickName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="telephone"
        label="游客联系方式"
        align="center"
        width="130">
      </el-table-column>
      <el-table-column
        prop="email"
        label="游客邮箱"
        align="center"
        width="180">
      </el-table-column>
      <el-table-column
        prop="birthday"
        label="游客生日"
        align="center"
        width="130">
      </el-table-column>
      <el-table-column
        label="游客性别"
        align="center"
        width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.gender === '男' ? 'primary' : 'warning'">{{ scope.row.gender }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="address"
        label="游客居住地"
        show-overflow-tooltip
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        label="游客头像"
        align="center"
        width="150">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.avatar"
            :preview-src-list="[scope.row.avatar]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="注册时间"
        align="center"
        sortable="custom"
        width="200">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            icon="el-icon-lock"
            @click="handlePassword(scope.$index, scope.row)">修改密码</el-button>
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

    <!--   添加删除用的表单 -->
    <el-dialog title="游客信息" :visible.sync="dialogFormVisible">
      <el-form
        label-width="120px"
        v-loading="loading"
        :model="touristPassword"
        :rules="ruleTourist"
        ref="touristForm"
        element-loading-text="信息跟新中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(255, 255, 255, 0.2)"
      >
        <el-form-item label="密码" prop="password">
          <el-input v-model="touristPassword.password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="touristPassword.confirmPassword"></el-input>
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
import tourist from "@/api/tourist";
import {paramStr} from "@/utils";
import { validPassword, checkKeyboardContinuousChar } from "@/utils/validate";
import { encryptPassword } from '@/utils/secure';

export default {
  name: "Tourist",
  data () {
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.$refs.touristForm.model.password) callback(new Error('两次密码输入不一致'))
      else callback()
    }
    return {
      touristList: [],
      current: 1,
      limit: 10,
      total: null,
      searchKey: "",
      options: [
        {
          label: "男",
          value: "男"
        },
        {
          label: "女",
          value: "女"
        }
      ],
      genderType: "",
      touristPassword: {},
      dialogFormVisible: false,
      loading: false,
      ruleTourist: {
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { trigger: ['blur', 'change'], validator: function(rule, value, callback) {
            if (!validPassword(value)) callback(new Error('密码中必须包含大小写 字母、数字、特称字符，至少8个字符，最多32个字符'))
            else if(checkKeyboardContinuousChar(value)) callback(new Error('输入字符不能连续'))
            else callback()
          } },
        ],
        confirmPassword: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          { trigger: ['blur', 'change'], validator: validateConfirmPassword },
        ]
      }
    }
  },

  created() {
    this.getPageTourist()
  },

  methods: {
    // 调用接口获取所有游客信息
    async getPageTourist(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await tourist.getPageTourist(this.current, this.limit, {orderKey, orderType, searchKey: this.searchKey, genderType: this.genderType})
      this.touristList = res.data.rows.map(item => {
        const addr = JSON.parse(item.address)
        item.address = `${addr.province.name}-${addr.city.name}-${addr.county.name}`
        return item
      })
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口修改游客密码
    changePassword(data) {
      this.$refs.touristForm.validate(async valid => {
        if (valid) {
          delete data.confirmPassword
          this.loading = true
          let cipher = await encryptPassword(data.password)
          data.password = cipher
          let res = await tourist.changePassword(data)
          this.loading = false
          if (res.code == 20000) {
            this.getPageTourist()
            this.$message.success("修改密码成功")
          } else {
            this.$message.error("修改密码失败")
          }
        } else {
          return false
        }
      })

    },

    // 调用接口根据id删除游客
    removeTourist(data) {
      this.$confirm(`此操作将永久删除游客${data.id}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        console.log(data)
        let res = await tourist.removeTourist(data.id)
        if (res.code == 20000) {
          this.getPageTourist()
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 修改密码按钮
    handlePassword(index, row) {
      this.touristPassword = Object.assign({}, row, {confirmPassword: "", password: ""})
      this.dialogFormVisible = true
    },

    // 删除按钮
    handleDelete(index, row) {
      this.removeTourist(row)
    },

    // 对话框确定按钮
    handleModify() {
      this.changePassword(this.touristPassword)
      this.dialogFormVisible = false
    },

    // 处理分页器分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageTourist()
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSearch() {
      // console.log(this.searchKey)
      this.getPageTourist()
    },

    handleSort(column) {
      // console.log(column.prop, column.order)
      this.getPageTourist(paramStr(column.prop), column.order)
    },

    handleGenderTypeChange() {
      this.getPageTourist()
    }
  }
}
</script>

<style scoped>

</style>
