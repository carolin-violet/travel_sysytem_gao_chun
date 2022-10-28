<template>
  <div class="app-container">
    <el-button
      size="small"
      type="primary"
      style="margin: 5px 20px"
      @click="handleAdd"><i class="el-icon-circle-plus-outline"></i>添加站点
    </el-button>

    <!--   展示表格 -->
    <el-table
      :data="siteList"
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
        label="站点id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        prop="site"
        label="站点名称"
        align="center"
        show-overflow-tooltip
        width="200">
      </el-table-column>
      <el-table-column
        prop="description"
        label="站点描述"
        align="center"
        show-overflow-tooltip
        width="480">
      </el-table-column>
      <el-table-column
        prop="sort"
        label="站点排序"
        align="center"
        width="80">
      </el-table-column>
      <el-table-column
        prop="startTime"
        label="开始时间"
        align="center"
        width="120">
      </el-table-column>
      <el-table-column
        prop="endTime"
        label="结束时间"
        align="center"
        width="120">
      </el-table-column>
      <el-table-column
        label="添加时间"
        align="center"
        width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="info"
            @click="handleEdit(scope.$index, scope.row)"><i class="el-icon-edit"></i>编辑</el-button>
          <el-button
            size="small"
            type="danger"
            style="margin: 0 10px"
            @click="handleDelete(scope.$index, scope.row)"><i class="el-icon-delete"></i>删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--   添加删除用的表单 -->
    <el-dialog title="线路信息" :visible.sync="dialogFormVisible">
      <el-form
        :model="curSite"
        label-width="120px"
        :rules="siteRule"
        ref="siteForm"
        v-loading="loading"
        element-loading-text="信息跟新中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(255, 255, 255, 0.2)"
      >
        <el-form-item label="站点id" v-if="flag===0">
          <el-input v-model="curSite.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="站点名称">
          <el-cascader
            :options="options"
            :show-all-levels="false"
            clearable
            :placeholder="curSite.site || '请选择站点'"
            v-model="tempData"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="站点描述">
          <el-input v-model="curSite.description" type="textarea" :autosize="{minRows: 1, maxRows: 10}"></el-input>
        </el-form-item>
        <el-form-item label="站点排序">
          <el-input v-model="curSite.sort"></el-input>
        </el-form-item>
        <el-form-item label="活动时间">
          <el-time-picker
            v-model="curSite.startTime"
            value-format="HH:mm:ss"
            placeholder="开始时间">
          </el-time-picker>
          --
          <el-time-picker
            arrow-control
            v-model="curSite.endTime"
            value-format="HH:mm:ss"
            placeholder="结束时间">
          </el-time-picker>

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
import routeSite from "@/api/routeSite";

export default {
  name: "routeSites",
  data () {
    return {
      routeId: null,
      siteList: [],
      flag: 0,
      curSite: {},
      dialogFormVisible: false,
      options: [],
      tempData: null,
      loading: false,
      siteRule: {}
    }
  },

  created() {
    this.routeId = this.$route.query.route_id
    this.getAllSitesByRouteId()
    this.getAllItems()
  },

  methods: {
    // 根据线路id获取线路上所有的站点
    async getAllSitesByRouteId () {
      this.$nprogress.start()
      let res = await routeSite.getAllSitesByRouteId(this.routeId)
      this.siteList = res.data.items
      this.$nprogress.done()
    },


    // 调用接口添加线路信息
    async addSite(data) {
      this.loading = true
      let res = await routeSite.addSite(data)
      this.loading = false
      if (res.code == 20000) {
        this.dialogFormVisible = false
        this.$message.success("添加成功")
        this.getAllSitesByRouteId()
      } else {
        this.$message.error("添加失败")
      }
    },

    // 调用接口修改线路信息
    async updateSite(data) {
      this.loading = true
      console.log('data:', data);
      let res = await routeSite.updateSite(data)
      this.loading = false
      if (res.code == 20000) {
        this.dialogFormVisible = false
        this.$message.success("修改成功")
        this.getAllSitesByRouteId()
      } else {
        this.$message.error("修改失败")
      }
    },

    // 调用接口根据id删除线路
    removeSite(data) {
      this.$confirm(`此操作将永久删除${data.name}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await routeSite.removeSite(data.id)
        if (res.code == 20000) {
          this.getAllSitesByRouteId()
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 获取所有分类及分类的数据名称
    async getAllItems() {
      let res = await routeSite.getAllItems()
      this.options = res.data.items
    },

    // 编辑按钮
    handleEdit(index, row) {
      this.tempData = null
      this.curSite = Object.assign({}, row)
      this.flag = 0
      this.dialogFormVisible = true
    },

    // 删除按钮
    handleDelete(index, row) {
      this.removeSite(row)
    },


    // 添加按钮
    handleAdd() {
      this.tempData = null
      this.curSite = {routeId: this.routeId}
      this.flag = 1
      this.dialogFormVisible = true
    },

    // 确定按钮, flag为1就添加线路，为0就修改线路
    handleModify() {
      this.curSite.site = this.tempData[1] || this.curSite.site
      if (this.flag === 1) {
        this.addSite(this.curSite)
      } else {
        this.updateSite(this.curSite)
      }
    },

  }
}
</script>

<style scoped>

</style>
