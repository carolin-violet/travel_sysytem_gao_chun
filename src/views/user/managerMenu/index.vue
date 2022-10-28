<template>
  <div class="app-container">
    <el-row type="flex" justify="space-around" style="margin-bottom: 14px">
      <el-col>
        <el-button type="primary" @click="handleAdd" icon="el-icon-circle-plus-outline" size="small">添加管理员
        </el-button>
      </el-col>
      <el-col :span="4">
        <el-input v-model="searchKey" placeholder="请输入管理员姓名或id回车查询" prefix-icon="el-icon-search" @change="handleSearch" clearable></el-input>
      </el-col>
    </el-row>

<!--   展示表格 -->
    <el-table
      :data="managerList"
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
        label="用户id"
        align="center"
        width="280">
      </el-table-column>
      <el-table-column
        label="姓名"
        align="center"
        width="280">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="telephone"
        label="手机号"
        align="center"
        width="280">
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
            type="success"
            icon="el-icon-lock"
            @click="handlePassword(scope.$index, scope.row)">修改密码</el-button>
          <el-button
            size="small"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button
            size="small"
            type="warning"
            @click="handlePermission(scope.$index, scope.row)">查看权限</el-button>
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
    <el-dialog title="管理员信息" :visible.sync="dialogFormVisible">
      <el-form
        v-loading="loading"
        element-loading-text="信息跟新中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(255, 255, 255, 0.2)"
      >
        <el-form :model="managerAdd" v-if="isAdd" label-width="120px" :rules="addRule" ref="addForm">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="managerAdd.name"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="telephone">
            <el-input v-model="managerAdd.telephone"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="managerAdd.password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="managerAdd.confirmPassword"></el-input>
          </el-form-item>
        </el-form>

        <el-form :model="managerEdit" v-if="isEdit" label-width="120px" :rules="editRule" ref="editForm">
          <el-form-item label="用户id">
            <el-input v-model="managerEdit.id" disabled></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="managerEdit.name"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="telephone">
            <el-input v-model="managerEdit.telephone"></el-input>
          </el-form-item>
        </el-form>

        <el-form :model="managerPassword" v-if="isPassword" label-width="120px" :rules="passwordRule" ref="passwordForm">
          <el-form-item label="密码" prop="password">
            <el-input v-model="managerPassword.password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="managerPassword.confirmPassword"></el-input>
          </el-form-item>
        </el-form>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleModify">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import manager from "@/api/manager";
import {paramStr} from "@/utils";
import { validPassword, isPhone, checkKeyboardContinuousChar } from "@/utils/validate";
import { encryptPassword } from '@/utils/secure';

export default {
  name: "ManagerMenu",
  data() {
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.$refs.addForm.model.password) callback(new Error('两次密码输入不一致'))
      else callback()
    }
    const validateConfirmPassword2 = (rule, value, callback) => {
      if (value !== this.$refs.passwordForm.model.password) callback(new Error('两次密码输入不一致'))
      else callback()
    }
    return {
      managerList: [],
      dialogFormVisible: false,
      managerAdd: {},
      managerEdit: {},
      managerPassword: {},
      searchKey: "",
      current: 1,
      limit: 10,
      total: null,
      loading: false,
      isEdit: false,
      isAdd: false,
      isPassword: false,
      addRule: {
        name: [
        { required: true, message: '请输入姓名', trigger: 'blur' },
        { min: 1, max: 20, message: '姓名长度在1-20个字符', trigger: ['change', 'blur'] },
        ],
        telephone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          { trigger: ['blur', 'change'], validator: function(rule, value, callback) {
            if (isPhone(value)) {
              callback()
            } else {
              callback(new Error('请输入正确格式的手机号'))
            }
          } },
        ],
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
      },
      editRule: {
        name: [
        { required: true, message: '请输入姓名', trigger: 'blur' },
        { min: 1, max: 20, message: '姓名长度在1-20个字符', trigger: ['change', 'blur'] },
        ],
        telephone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          { trigger: ['blur', 'change'], validator: function(rule, value, callback) {
            if (isPhone(value)) {
              callback()
            } else {
              callback(new Error('请输入正确格式的手机号'))
            }
          } },
        ]
      },
      passwordRule: {
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
          { trigger: ['blur', 'change'], validator: validateConfirmPassword2 },
        ]
      }
    }
  },
  created() {
    this.getManager()
  },
  methods: {
    // 调用接口获取所有管理员信息
    async getManager(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await manager.getPageManager(this.current, this.limit, {orderKey, orderType, searchKey: this.searchKey})
      this.managerList = res.data.items
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口添加管理员
    addManager(data) {
      this.$refs.addForm.validate(async valid => {
        if (valid) {
          delete data.confirmPassword
          this.loading = true
          const cipher = await encryptPassword(data.password)
          data.password = cipher
          let res = await manager.addManager(data)
          this.loading = false
          if (res.code == 20000) {
            this.getManager()
            this.$message.success("添加成功")
          } else {
            this.$message.error("添加失败")
          }
        } else {
          return false
        }
      })
    },

    // 调用接口修改管理员信息
    updateManager(data) {
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          this.loading = true
          let res = await manager.updateManager(data)
          this.loading = false
          if (res.code == 20000) {
            this.getManager()
            this.$message.success("修改信息成功")
          } else {
            this.$message.error("修改信息失败")
          }
        } else {
          return false
        }
      })
    },

    // 调用接口修改管理员密码
    changePassword(data) {
      this.$refs.passwordForm.validate(async valid => {
        if (valid) {
          delete data.confirmPassword
          this.loading = true
          let cipher = await encryptPassword(data.password)
          data.password = cipher
          let res = await manager.changePassword(data)
          this.loading = false
          if (res.code == 20000) {
            this.getManager()
            this.$message.success("修改密码成功")
          } else {
            this.$message.error("修改密码失败")
          }
        } else {
          return false
        }
      })
    },

    // 调用接口删除管理员
    delManager(data) {
      this.$confirm(`此操作将永久删除${data.name}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await manager.removeManager(data.id)
        if (res.code == 20000) {
          this.getManager()
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 添加按钮
    handleAdd() {
      this.managerAdd = {}
      this.dialogFormVisible = true
      this.isAdd = true
      this.isEdit = false
      this.isPassword = false
    },

    // 编辑按钮
    handleEdit(index, row) {
      this.managerEdit = Object.assign({}, row)
      this.dialogFormVisible = true
      this.isAdd = false
      this.isEdit = true
      this.isPassword = false
    },

    // 修改密码按钮
    handlePassword(index, row) {
      this.managerPassword = Object.assign({}, row, {password: "", confirmPassword: ""})
      this.dialogFormVisible = true
      this.isAdd = false
      this.isEdit = false
      this.isPassword = true
    },

    // 删除按钮
    handleDelete(index, row) {
      this.delManager(row)
    },

    // 查看权限按钮
    handlePermission(index, row) {
      this.$router.push({
        path: "/user/permission",
        query: {
          id: row.id
        }
      })
    },

    // 添加或删除管理员
    handleModify() {
      if (this.isAdd) {
        this.addManager(this.managerAdd)
      } else if (this.isEdit) {
        this.updateManager(this.managerEdit)
      } else if (this.isPassword) {
        this.changePassword(this.managerPassword)
      }
      this.dialogFormVisible = false
    },

    // 处理分页器分页
    handleCurrentChange(val) {
      this.current = val
      this.getManager()
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSearch() {
      // console.log(this.searchKey)
      this.getManager()
    },

    handleSort(column) {
      // console.log(column.prop, column.order)
      this.getManager(paramStr(column.prop), column.order)
    }
  }
}
</script>

<style scoped>
</style>
