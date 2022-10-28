<template>
  <div class="dashboard-container">
    <panel-group></panel-group>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <baseCount style="height: 350px"/>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card>
          <comment style="height: 350px"/>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="position: relative">
          <i class="travel-iconfont travel-icon-arrow-left-bold" @click="changeWindow1(-1)" ></i>
          <i class="travel-iconfont travel-icon-arrow-right-bold" @click="changeWindow1(1)"></i>
          <age style="height: 350px" v-if="window1.content[window1.index] === 'age'"/>
          <gender style="height: 350px" v-if="window1.content[window1.index] === 'gender'"/>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 40px; margin-bottom: 30px">
      <el-col :span="18">
        <el-card>
          <income style="height: 350px"/>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="position: relative">
          <i class="travel-iconfont travel-icon-arrow-left-bold" @click="changeWindow2(-1)"></i>
          <i class="travel-iconfont travel-icon-arrow-right-bold" @click="changeWindow2(1)"></i>
          <is-paid style="height: 350px" v-if="window2.content[window2.index] === 'isPaid'"/>
          <scenic-and-route style="height: 350px" v-if="window2.content[window2.index] === 'scenicAndRoute'"/>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import panelGroup from './panelGroup'
import baseCount from "@/views/dashboard/charts/baseCount";
import comment from "@/views/dashboard/charts/comment";
import income from "@/views/dashboard/charts/income";
import isPaid from "@/views/dashboard/charts/isPaid";
import scenicAndRoute from "@/views/dashboard/charts/scenicAndRoute";
import gender from "@/views/dashboard/charts/gender";
import age from "@/views/dashboard/charts/age";

export default {
  name: 'Dashboard',
  components: {
    panelGroup,
    baseCount,
    comment,
    income,
    isPaid,
    scenicAndRoute,
    age,
    gender
  },
  data () {
    return {
      allData: null,
      window1: {
        index: 0,
        content: ['age', 'gender']
      },
      window2: {
        index: 0,
        content: ['isPaid', 'scenicAndRoute']
      }
    }
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  methods: {
    changeWindow1(num) {
      this.window1.index += num
      if (this.window1.index > this.window1.content.length - 1) {
        this.window1.index = 0
      } else if (this.window1.index < 0) {
        this.window1.index = this.window1.content.length - 1
      }
    },
    changeWindow2(num) {
      this.window2.index += num
      if (this.window2.index > this.window2.content.length - 1) {
        this.window2.index = 0
      } else if (this.window2.index < 0) {
        this.window2.index = this.window2.content.length - 1
      }
    },
  }
}
</script>

<style lang="scss" scoped>

.dashboard-container{
  width: 100%;
  min-height: 100vh;
  background-color: #f0f2f5;
  padding: 0 20px 0 20px;
}

.travel-iconfont{
  position: absolute;
  top: 50%;
  font-size: 24px;
  transform: translateY(-50%);
  cursor: pointer;
  &.travel-icon-arrow-left-bold{
    left: 10px;
  }
  &.travel-icon-arrow-right-bold{
    right: 10px;
  }
  }
</style>
