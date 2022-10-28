<template>
  <div class="app-container">

    <el-row type="flex" justify="space-around" style="margin-bottom: 14px">
      <el-col>
        <el-button type="primary" @click="handleAdd" icon="el-icon-circle-plus-outline" size="small">添加公告
        </el-button>
      </el-col>
      <el-col :span="4">
        <el-input v-model="searchKey" placeholder="请输入公告名称或id回车查询" prefix-icon="el-icon-search" @change="handleSearch" clearable></el-input>
      </el-col>
    </el-row>

    <!--   展示表格 -->
    <el-table
      :data="noticeList"
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
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        label="公告id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        prop="name"
        label="公告标题"
        align="center"
        show-overflow-tooltip
        width="280">
      </el-table-column>
      <el-table-column
        label="公告封面图片"
        align="center"
        width="200">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.cover"
            :preview-src-list="[scope.row.cover]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column
        prop="description"
        label="公告内容"
        align="center"
        show-overflow-tooltip
        width="350">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="添加时间"
        align="center"
        sortable="custom"
        width="160">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="info"
            icon="el-icon-edit"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-edit-outline"
            @click="toArticle(scope.$index, scope.row)">编辑文章</el-button>
          <el-button
            size="mini"
            type="success"
            icon="el-icon-picture-outline "
            @click="changeCover(scope.$index, scope.row)">更换封面图</el-button>
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


    <!--   添加修改用的表单 -->
    <el-dialog title="公告信息" :visible.sync="dialogFormVisible">
      <el-form
        :model="curNotice"
        label-width="120px"
        v-loading="loading"
        element-loading-text="信息跟新中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(255, 255, 255, 0.2)"
      >
        <el-form-item label="公告id" v-show="flag===0">
          <el-input v-model="curNotice.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="公告名称">
          <el-input v-model="curNotice.name"></el-input>
        </el-form-item>
        <el-form-item label="公告说明">
          <el-input
            v-model="curNotice.description"
            type="textarea"
            :rows="4"
            placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleModify">确 定</el-button>
      </div>
    </el-dialog>

    <!--    图片选择器-->
    <PhotoSelector ref="PhotoSelectorRef" @getPhoto="getPhoto" :selectorMarkId="selectorMarkId"/>
  </div>
</template>

<script>
import notice from "@/api/notice";
import PhotoSelector from "@/components/PhotoSelector";
import {paramStr} from "@/utils";

export default {
  name: "Notice",
  components: {
    PhotoSelector
  },
  data () {
    return {
      noticeList: [],
      dialogFormVisible: false,
      flag: null,
      curNotice: {},
      dialogVisible: false,
      disabled: false,
      current: 1,
      limit: 5,
      total: null,
      loading: false,
      selectorMarkId: null,
      searchKey: ""
    }
  },

  created() {
    this.getPageNotice()
  },

  computed: {
    config() {
      return {token: this.$store.state.token}
    }
  },

  methods: {
    // 调用接口获取所有公告信息
    async getPageNotice(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await notice.getPageNotice(this.current, this.limit, {orderKey, orderType, searchKey: this.searchKey})
      this.noticeList = res.data.rows
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口添加公告信息
    async addNotice(data) {
      this.loading = true
      let res = await notice.addNotice(data)
      this.loading = false
      if (res.code == 20000) {
        this.dialogFormVisible = false
        this.$message.success("添加成功")
        this.getPageNotice()
      } else {
        this.$message.error("添加失败")
      }
    },

    // 调用接口修改公告信息
    async updateNotice(data) {
      this.loading = true
      let res = await notice.updateNotice(data)
      this.loading = false
      if (res.code == 20000) {
        this.dialogFormVisible = false
        this.$message.success("修改成功")
        this.getPageNotice()
      } else {
        this.$message.error("修改失败")
      }
    },

    // 调用接口根据id删除公告
    removeNotice(data) {
      this.$confirm(`此操作将永久删除${data.name}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await notice.removeNotice(data.id)
        if (res.code == 20000) {
          this.getPageNotice()
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
      this.curNotice = Object.assign({}, row)
      this.flag = 0
      this.dialogFormVisible = true
    },

    // 删除按钮
    handleDelete(index, row) {
      this.removeNotice(row)
    },

    // 查看更多图片按钮
    toArticle(index, row) {
      this.$router.push({
        path: "/article/edit",
        query: {
          mark: "美食",
          name: row.name,
          mark_id: row.id   // 传入美食id
        }
      })
    },

    // 更换封面图按钮
    changeCover(index, row) {
      this.$refs.PhotoSelectorRef.dialogVisible = true
      this.selectorMarkId = row.id
    },

    // 图片选择器的选择图片功能
    async getPhoto(photo) {
      const data = this.noticeList.find(item => item.id === photo.markId)
      data.cover = photo.url
      let res = await notice.updateNotice(data)
      if (res.code == 20000) {
        this.$message.success("修改成功")
        this.getPageNotice()
      } else {
        this.$message.error("修改失败")
      }
    },


    // 添加按钮
    handleAdd() {
      this.flag = 1
      this.dialogFormVisible = true
    },

    // 确定按钮, flag为1就添加公告，为0就修改公告
    handleModify() {
      if (this.flag === 1) {
        this.addNotice(this.curNotice)
      } else {
        this.updateNotice(this.curNotice)
      }
    },

    // 处理分页器分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageNotice()
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSearch() {
      // console.log(this.searchKey)
      this.getPageNotice()
    },

    handleSort(column) {
      // console.log(column.prop, column.order)
      this.getPageNotice(paramStr(column.prop), column.order)
    }
  }
}
</script>

<style scoped>

</style>
