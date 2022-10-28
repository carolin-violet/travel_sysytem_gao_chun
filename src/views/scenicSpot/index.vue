<template>
  <div class="app-container">

    <el-row type="flex" justify="space-around" style="margin-bottom: 14px">
      <el-col>
        <el-button type="primary" @click="handleAdd" icon="el-icon-circle-plus-outline" size="small">添加景点
        </el-button>
      </el-col>
      <el-col :span="4">
        <el-input v-model="searchKey" placeholder="请输入景点名称或id回车查询" prefix-icon="el-icon-search" @change="handleSearch" clearable></el-input>
      </el-col>
    </el-row>

    <!--   展示表格 -->
    <el-table
      :data="scenicSpotList"
      @sort-change="handleSort"
      stripe
      border
      fit
      lazy
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="景点说明">
              <span>{{ props.row.description }}</span>
            </el-form-item>
            <el-form-item label="景点地址">
              <span>{{ props.row.address }}</span>
            </el-form-item>
            <el-form-item label="门票单价">
              <span>{{ props.row.price }}</span>
            </el-form-item>
            <el-form-item label="门票折扣价">
              <span>{{ props.row.discountPrice }}</span>
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
        label="景点id"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        prop="name"
        label="景点名称"
        align="center"
        show-overflow-tooltip
        width="400">
      </el-table-column>
      <el-table-column
        label="景点封面图片"
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
        prop="recommendRank"
        label="推荐指数"
        align="center"
        sortable="custom"
        width="100">
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
          <el-button
            size="mini"
            type="warning"
            icon="el-icon-coordinate"
            @click="toCoordinate(scope.$index, scope.row)">修改坐标</el-button>
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
    <el-dialog title="景点基本信息" :visible.sync="dialogFormVisible">
      <el-form
        :model="curScenicSpot"
        label-width="120px"
        :rules="ruleScenicSpot"
        ref="scenicSpotForm"
        v-loading="loading"
        element-loading-text="信息跟新中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(255, 255, 255, 0.2)"
      >
        <el-form-item label="景点id" v-show="flag===0">
          <el-input v-model="curScenicSpot.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="景点名称" prop="name">
          <el-input v-model="curScenicSpot.name"></el-input>
        </el-form-item>
        <el-form-item label="景点说明" prop="description">
          <el-input v-model="curScenicSpot.description" type="textarea" :autosize="{ minRows: 1, maxRows: 10}"></el-input>
        </el-form-item>
        <el-form-item label="景点地址" prop="address">
          <el-input v-model="curScenicSpot.address" type="textarea" :autosize="{ minRows: 1, maxRows: 10}"></el-input>
        </el-form-item>
        <el-form-item label="推荐指数" prop="recommendRank">
          <el-input v-model="curScenicSpot.recommendRank"></el-input>
        </el-form-item>
        <el-form-item label="景点单价" prop="price">
          <el-input v-model="curScenicSpot.price"></el-input>
        </el-form-item>
        <el-form-item label="景点折扣价" prop="discountPrice">
          <el-input v-model="curScenicSpot.discountPrice"></el-input>
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
import scenicSpot from "@/api/scenicSpot";
import PhotoSelector from "@/components/PhotoSelector";
import {paramStr} from "@/utils";
import {testPrice} from "@/utils/validate";

export default {
  name: "ScenicSpot",
  components: {
    PhotoSelector
  },
  data () {
    return {
      scenicSpotList: [],
      dialogFormVisible: false,
      flag: null,
      curScenicSpot: {},
      dialogVisible: false,
      disabled: false,
      current: 1,
      limit: 5,
      total: null,
      loading: false,
      selectorMarkId: null,
      searchKey: "",
      ruleScenicSpot: {
        name: [
          { required: true, message: '请输入景点名称', trigger: 'blur' },
          { min: 1, max: 20, message: '景点名称长度为1-20个字符', trigger: ['blur', 'change'] },
        ],
        description: [
          { required: true, message: '请输入景点描述', trigger: 'blur' },
          { min: 1, max: 255, message: '景点描述长度为1-255个字符', trigger: ['blur', 'change'] },
        ],
        address: [
          { required: true, message: '请输入景点详细地址', trigger: 'blur' },
          { min: 1, max: 255, message: '景点地址长度为1-255个字符', trigger: ['blur', 'change'] },
        ],
        recommendRank: [
          { required: true, message: '请输入推荐指数', trigger: 'blur' },
          { trigger: ['blur', 'change'], validator: function(rule, value, callback) {
            if ([1,2,3,4,5].includes(value)) callback()
            else callback(new Error('请输入[1,2,3,4,5]中任意一个指数'))
          } },
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
            else if (value > this.ruleScenicSpot.price) callback(new Error('折扣价不能大于原价'))
            else callback()
          } },
        ],
      }
    }
  },

  created() {
    this.getPageScenicSpot()
  },

  methods: {
    // 调用接口获取所有景点信息
    async getPageScenicSpot(orderKey="create_time", orderType="descending") {
      this.$nprogress.start()
      let res = await scenicSpot.getPageScenicSpot(this.current, this.limit, {orderKey, orderType, searchKey: this.searchKey})
      this.scenicSpotList = res.data.items.map(item => {
        item.price = item.price.toFixed(2)
        item.discountPrice = item.discountPrice.toFixed(2)
        return item
      })
      this.total = res.data.total
      this.$nprogress.done()
    },

    // 调用接口添加景点信息
    addScenicSpot(data) {
      this.$refs.scenicSpotForm.validate(async valid => {
        if (valid) {
          this.loading = true
          let res = await scenicSpot.addScenicSpot(data)
          this.loading = false
          if (res.code == 20000) {
            this.dialogFormVisible = false
            this.$message.success("添加成功")
            this.getPageScenicSpot()
            this.curScenicSpot = {}
            this.dialogImageUrl = ''
          } else {
            this.$message.error("添加失败")
          }
        } else {
          return false
        }
      })
    },

    // 调用接口修改景点信息
    async updateScenicSpot(data) {
      this.$refs.scenicSpotForm.validate(async valid => {
        if (valid) {
          this.loading = true
          let res = await scenicSpot.updateScenicSpot(data)
          this.loading = false
          if (res.code == 20000) {
            this.dialogFormVisible = false
            this.$message.success("修改成功")
            this.getPageScenicSpot()
            this.curScenicSpot = {}
            this.dialogImageUrl = ''
          } else {
            this.$message.error("修改失败")
          }
        } else {
          return false
        }
      })
    },

    // 调用接口根据id删除景点
    removeScenicSpot(data) {
      this.$confirm(`此操作将永久删除${data.name}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let res = await scenicSpot.removeScenicSpot(data.id)
        if (res.code == 20000) {
          this.getPageScenicSpot()
          this.$message.success("删除成功")
          this.curScenicSpot = {}
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
      this.curScenicSpot = Object.assign({}, row)
      this.flag = 0
      this.dialogFormVisible = true
    },

    // 删除按钮
    handleDelete(index, row) {
      this.removeScenicSpot(row)
    },

    // 查看更多图片按钮
    toArticle(index, row) {
      this.$router.push({
        path: "/article/edit",
        query: {
          mark: "景点",
          name: row.name,
          mark_id: row.id   // 传入景点id
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
      const data = this.scenicSpotList.find(item => item.id === photo.markId)
      data.cover = photo.url
      let res = await scenicSpot.updateScenicSpot(data)
      if (res.code == 20000) {
        this.$message.success("修改成功")
        this.getPageScenicSpot()
      } else {
        this.$message.error("修改失败")
      }
    },

    // 添加按钮
    handleAdd() {
      this.curScenicSpot = {}
      this.flag = 1
      this.dialogFormVisible = true
    },

    // 确定按钮, flag为1就添加景点，为0就修改景点
    handleModify() {
      if (this.flag === 1) {
        this.addScenicSpot(this.curScenicSpot)
      } else {
        this.updateScenicSpot(this.curScenicSpot)
      }
    },

    // 处理分页器分页
    handleCurrentChange(val) {
      this.current = val
      this.getPageScenicSpot()
    },

    calcIndex(index) {
      return (this.current -1) * this.limit + index + 1
    },

    handleSearch() {
      // console.log(this.searchKey)
      this.getPageScenicSpot()
    },

    handleSort(column) {
      // console.log(column.prop, column.order)
      this.getPageScenicSpot(paramStr(column.prop), column.order)
    },

    toCoordinate(index, row) {
      this.$router.push({
        path: '/geoInfo/coordinate',
        query: {
          mark_id: row.id,
          name: row.name
        }
      })
    }

  }
}
</script>

<style scoped>

</style>
