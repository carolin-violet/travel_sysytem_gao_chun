<template>
  <div class="app-container">

<!--  添加权限  -->
    <el-button
      size="small"
      type="primary"
      style="margin: 5px 20px"
      @click="handleAdd"><i class="el-icon-circle-plus-outline"></i>添加权限
    </el-button>

    <!--   展示表格 -->
    <el-table
      :data="permissions"
      stripe
      border
      fit
      lazy
      style="width: 100%">
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        label="权限列表id"
        align="center"
        width="280">
      </el-table-column>
      <el-table-column
        label="权限名称"
        align="center"
        width="280">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.roleName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="roleDesc"
        label="权限描述"
        align="center"
        width="280">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="medium"
            type="danger"
            style="margin: 0 20px"
            @click="handleDelete(scope.$index, scope.row)"><i class="el-icon-delete"></i>删除</el-button>
        </template>
      </el-table-column>
    </el-table>

<!--  添加权限表单  -->
    <el-dialog title="选择添加权限" :visible.sync="dialogFormVisible">
      <el-form :model="curPermission">
        <el-form-item label="活动区域" :label-width="formLabelWidth">
          <el-select v-model="curPermission.roleId" placeholder="请选择权限">
            <el-option v-for="role in roles" :key="role.id" :label="role.roleName" :value="role.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addPermission(curPermission)">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import manager from "@/api/manager";

export default {
  name: "Permission",
  data () {
    return {
      roles: [],
      permissions: [],
      user_id: null,
      dialogFormVisible: false,
      curPermission: {},
      formLabelWidth: "120px"
    }
  },

  created() {
    this.user_id = this.$route.query.id
    this.getAllRole()
    this.getPermissionById(this.user_id)
  },

  methods: {
    // 调用接口获取所有角色
    async getAllRole() {
      let res = await manager.getAllRole()
      this.roles = res.data.items
    },

    // 调用接口根据用户id获取用户权限
    async getPermissionById(id) {
      this.$nprogress.start()
      let res = await manager.getPermissionById(id)
      this.permissions = res.data.items
      this.permissions.forEach(item => {
        item.roleName = this.roles.find(role => role.id == item.roleId).roleName
        item.roleDesc = this.roles.find(role => role.id == item.roleId).roleDesc
      })
      this.$nprogress.done()
    },

    // 添加用户权限
    async addPermission(data) {
      this.dialogFormVisible = false
      let res = await manager.addPermission(data)
      if (res.code == 20000) {
        this.getPermissionById(this.user_id)
        this.$message.success("添加成功")
      } else {
        this.$message.error("添加失败")
      }
    },

    // 根据权限id删除权限信息
    delPermission(data) {
      this.$confirm(`此操作将永久删除${data.roleName}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await manager.delPermission(data.id)
        if (res.code == 20000) {
          this.getPermissionById(this.user_id)
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
      this.delPermission(row)
    },

    // 添加权限按钮
    handleAdd() {
      this.dialogFormVisible = true
      this.curPermission.userId = this.user_id
    }

  }
}
</script>

<style scoped>

</style>
