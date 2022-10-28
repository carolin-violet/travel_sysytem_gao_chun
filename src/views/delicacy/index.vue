<template>
  <div class="app-container">

    <el-row type="flex" justify="space-around" style="margin-bottom: 14px">
      <el-col>
        <el-button type="primary" @click="handleAdd" icon="el-icon-circle-plus-outline" size="small">添加美食
        </el-button>
      </el-col>
      <el-col :span="4">
        <el-input v-model="searchKey" placeholder="请输入美食名称或id回车查询" prefix-icon="el-icon-search" @change="handleSearch" clearable></el-input>
      </el-col>
    </el-row>

    <!--   展示表格 -->
    <el-table
      :data="delicacyList"
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
        label="美食id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        prop="name"
        label="美食名称"
        align="center"
        show-overflow-tooltip
        width="200">
      </el-table-column>
      <el-table-column
        label="美食封面图片"
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
        label="美食说明"
        align="center"
        show-overflow-tooltip
        width="280">
      </el-table-column>
      <el-table-column
        prop="recommendRank"
        label="推荐指数"
        align="center"
        sortable="custom"
        width="150">
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

    <!--   添加删除用的表单 -->
    <el-dialog title="美食基本信息" :visible.sync="dialogFormVisible">
      <el-form
        :model="curDelicacy"
        :rules="ruleDelicacy"
        ref="delicacyForm"
        label-width="120px"
        v-loading="loading"
        element-loading-text="信息跟新中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(255, 255, 255, 0.2)"
      >
        <el-form-item label="美食id" v-if="flag===0">
          <el-input v-model="curDelicacy.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="美食名称" prop="name">
          <el-input v-model="curDelicacy.name"></el-input>
        </el-form-item>
        <el-form-item label="美食说明" prop="description">
          <el-input v-model="curDelicacy.description" type="textarea" :autosize="{minRows: 1, maxRows: 10}"></el-input>
        </el-form-item>
        <el-form-item label="推荐指数" prop="recommendedRank">
          <el-input v-model.number="curDelicacy.recommendedRank"></el-input>
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
import delicacy from "@/api/delicacy";
import PhotoSelector from "@/components/PhotoSelector";
import {paramStr} from "@/utils";

export default {
  name: "Delicacy",
  components: {
    PhotoSelector
  },
  data () {
    return {
      delicacyList: [],
      dialogFormVisible: false,
      flag: null,
      curDelicacy: {},
      dialogVisible: false,
      disabled: false,
      total: null,
      current: 1,
      limit: 5,
      loading: false,
      selectorMarkId: null,
      searchKey: "",
      ruleDelicacy: {
        name: [
          { required: true, message: '请输入美食名称', trigger: 'blur' },
          { min: 1, max: 20, message: '美食名称长度为1-20个字符', trigger: ['blur', 'change'] },
        ],
        description: [
          { required: true, message: '请输入美食描述', trigger: 'blur' },
          { min: 1, max: 255, message: '美食描述长度为1-255个字符', trigger: ['blur', 'change'] },
        ],
        recommendRank: [
          { required: true, message: '请输入推荐指数', trigger: 'blur' },
          { trigger: ['blur', 'change'], validator: function(rule, value, callback) {
            if ([1,2,3,4,5].includes(value)) callback()
            else callback(new Error('请输入[1,2,3,4,5]中任意一个指数'))
          } },
        ]
      }
    }
  },

  created() {
    this.getPageDelicacy()
  },

  methods: {
    // 调用接口获取所有美食信息
    async getPageDelicacy(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await delicacy.getPageDelicacy(this.current, this.limit, {orderKey, orderType, searchKey: this.searchKey})
      this.delicacyList = res.data.items
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口添加美食信息
    addDelicacy(data) {
      this.$refs.delicacyForm.validate(async valid => {
        if (valid) {
          this.loading = true
          let res = await delicacy.addDelicacy(data)
          this.loading = false
          if (res.code == 20000) {
            this.dialogFormVisible = false
            this.$message.success("添加成功")
            this.getPageDelicacy()
            this.curDelicacy = {}
            this.dialogImageUrl = ''
          } else {
            this.$message.error("添加失败")
          }
        } else {
          return false
        }
      })

    },

    // 调用接口修改美食信息
    updateDelicacy(data) {
      this.$refs.delicacyForm.validate(async valid => {
        if (valid) {
          this.loading = true
          let res = await delicacy.updateDelicacy(data)
          this.loading = false
          if (res.code == 20000) {
            this.dialogFormVisible = false
            this.$message.success("修改成功")
            this.getPageDelicacy()
            this.curDelicacy = {}
            this.dialogImageUrl = ''
          } else {
            this.$message.error("修改失败")
          }
        } else {
          return false
        }
      })
    },

    // 调用接口根据id删除美食
    removeDelicacy(data) {
      this.$confirm(`此操作将永久删除${data.name}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await delicacy.removeDelicacy(data.id)
        if (res.code == 20000) {
          this.getPageDelicacy()
          this.$message.success("删除成功")
          this.curDelicacy = {}
          this.dialogImageUrl = ''
        } else {
          this.$message.error("删除失败")
        }
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 编辑按钮
    handleEdit(index, row) {
      this.curDelicacy = Object.assign({}, row)
      this.flag = 0
      this.dialogFormVisible = true
    },

    // 删除按钮
    handleDelete(index, row) {
      this.removeDelicacy(row)
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
      const data = this.delicacyList.find(item => item.id === photo.markId)
      data.cover = photo.url
      let res = await delicacy.updateDelicacy(data)
      if (res.code == 20000) {
        this.$message.success("修改成功")
        this.getPageDelicacy()
      } else {
        this.$message.error("修改失败")
      }
    },

    // 添加按钮
    handleAdd() {
      this.curDelicacy = {}
      this.flag = 1
      this.dialogFormVisible = true
    },

    // 确定按钮, flag为1就添加美食，为0就修改美食
    handleModify() {
      if (this.flag === 1) {
        this.addDelicacy(this.curDelicacy)
      } else {
        this.updateDelicacy(this.curDelicacy)
      }
    },

    // 处理分页器分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageDelicacy()
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSearch() {
      // console.log(this.searchKey)
      this.getPageDelicacy()
    },

    handleSort(column) {
      // console.log(column.prop, column.order)
      this.getPageDelicacy(paramStr(column.prop), column.order)
    }
  }
}
</script>

<style scoped>

</style>
