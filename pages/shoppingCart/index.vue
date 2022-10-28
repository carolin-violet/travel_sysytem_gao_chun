<template>
  <div class="relative w-screen min-h-screen">
    <div class="absolute top-16 left-0 w-96 h-96">
      <img src="@/assets/svg/购物消费.svg" alt="">
    </div>
    <section class="order-container absolute w-1/2 h-auto bg-white rounded-3xl mt-36 left-1/2 transform -translate-x-1/2 shadow-2xl">
      <section class="order-header w-full py-3 px-6">
        <strong class="text-3xl">购物车 （全部{{ orderList.length }}）</strong>
      </section>
      <hr>
      <section class="order-body w-full px-6 py-3">
<!--        中间头部说明-->
        <section class="body-header w-full flex space-x-5 items-center text-center font-bold">
          <div class="flex-1">
            <span>全选</span>
            <input type="checkbox" v-model="allChecked" @click="handleCheckAll">
          </div>
          <div class="flex-1">订单名称</div>
          <div class="flex-1">人数统计</div>
          <div class="flex-1">预订时间</div>
          <div class="flex-1">总金额</div>
          <div class="flex-1">操作</div>
        </section>
<!--        订单列表-->
        <section class="order-item w-full flex items-center text-center space-x-5 my-5 py-5 bg-gray-100 rounded-2xl" :class="order.checked ? 'bg-red-100 border-2 border-red-500' : ''" v-for="order in orderList" :key="order.id">
          <div class="flex-1">
            <span>选择</span>
            <input type="checkbox" v-model="order.checked" @click="handleCheck(order.id)">
          </div>
          <div class="flex-1">
            <i :class="{iconfont: true, 'icon-menpiao': order.mark === 'scenic', 'icon-xianlu': order.mark === 'route'}" class="text-4xl"></i>
            <a rel="nofollow" :href="order.mark === 'scenic' ? `/scenic/${order.commodityId}` : '/route'" :title="order.title ">{{ order.title }}</a>
          </div>
          <div class="flex-1">
            <div>成年人数：{{ order.adult }}</div>
            <div class="mt-3">未成年人数：{{ order.child }}</div>
          </div>
          <div class="flex-1">{{ order.appointmentTime }}</div>
          <div class="flex-1 text-red-500 font-bold">￥{{ order.amount }}</div>
          <div class="flex-1"><i class="iconfont icon-shanchu font-bold text-4xl cursor-pointer hover:text-red-400" @click="handleDelete(order)"></i></div>
        </section>
      </section>
      <hr>
      <section class="order-footer w-full px-6">
        <div class="float-right my-3">
          <span class="text-xl mr-3">已选商品 <strong class="font-bold text-red-500">{{ payList.length }}</strong> 件</span>
          <span class="text-xl mr-3">合计: <strong class="font-bold text-red-500">{{ allAmount }}</strong></span>
          <button class="w-28 h-16 rounded-2xl bg-gray-400 text-white text-2xl" :class="payList.length > 0 ? 'bg-red-400' : 'cursor-not-allowed'" :disabled="payList.length===0" @click="dialogVisible = true">结 算</button>
        </div>
      </section>
    </section>

<!--    支付弹框-->
    <el-dialog
      title="请扫码支付"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
<!--      支付切换按钮-->
      <section>
        <el-radio-group v-model="radio" size="small">
          <el-radio label="weixin" border>微信支付</el-radio>
          <el-radio label="zhifubao" border>支付宝支付</el-radio>
        </el-radio-group>
      </section>
      <section>
        <span>共{{ allAmount }} 元</span>
      </section>

<!--      二维码图片-->
      <section>
        <client-only>
          <vue-qr :logo-src="qrLogo" text="Hello!" :size="300"></vue-qr>
        </client-only>
      </section>

      <span slot="footer" class="dialog-footer">
    <el-button type="warning" @click="dialogVisible = false">支付遇到问题?</el-button>
    <el-button type="primary" @click="successPay">已完成支付</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import {mapState, mapGetters} from "vuex"

export default {
  name: "index",
  data() {
    return {
      orderList: [],
      allChecked: false,
      allAmount: 0,
      payList: [],  // 存放需要支付的每一个商品的id
      dialogVisible: false,
      radio: 'weixin',
      qrLogo: require('@/assets/images/avatar.webp'),
      current: 1,
      limit: 5
    }
  },

  computed: {
    ...mapState({
      touristId: state => state.userInfo.id
    }),
    ...mapGetters(['isLogin'])
  },
  created() {
    this.getOrders()
  },
  watch: {
    touristId(newVal, oldVal) {
      if (typeof newVal === "string") {
        this.getOrders()
      }
    },
  },

  methods: {
    // 获取未支付的订单
    async getOrders() {
      this.$store.dispatch('hasAuth')
      let res = await this.$axios.get(`/order/not_paid`)
      this.orderList = res.data.orders.map(order => {
        order.amount = Number(Number(order.amount).toFixed(2))
        order.checked = false
        return order
      })
      this.$store.commit('setCommodityCount', this.orderList.length)
    },

    // 单选框点击事件
    handleCheck(id) {
      // 点击商品前面的按钮
      let checkedCount = 0
      this.orderList.forEach(order => {
        order.id === id ? order.checked = !order.checked : ''
        order.checked === true ? checkedCount++ : ''
      })
      checkedCount === this.orderList.length ? this.allChecked = true : this.allChecked = false
      this.calcAll()
    },

    // 点击全选
    handleCheckAll() {
      // 点击全选按钮
      if (this.allChecked === false) {
        this.orderList.forEach(order => {
          order.checked = true
        })
      } else {
        this.orderList.forEach(order => {
          order.checked = false
        })
      }
      this.allChecked = !this.allChecked
      this.calcAll()
    },

    // 获取需要支付的商品id和金额
    calcAll() {
      const list = []
      let allAmount = 0
      this.orderList.forEach(order => {
        if (order.checked === true) {
          list.push(order.id)
          allAmount += order.amount
        }
      })
      this.payList = list
      this.allAmount = allAmount.toFixed(2)
    },

    // 判断是否有一个被选中
    hasChecked() {
      const checked = this.orderList.some(order => {
        order.checked = true
      })
      console.log(checked)
    },

    // 支付弹框
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },

    // 点击删除按钮
    handleDelete(order) {
      this.$store.dispatch('hasAuth')
      this.$confirm(`此操作将永久删除订单${order.id}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(async () => {
        let res = await this.$axios.delete(`order/${order.id}`)
        if (res.code === 204) {
          this.$message.success('删除成功!')
          this.getOrders()
        } else {
          this.$message.error('删除失败')
        }

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    // 已完成支付按钮
    async successPay() {
      this.$store.dispatch('hasAuth')
      for (const id of this.payList) {
        const data = {id, isPaid: 1}
        let res = await this.$axios.patch('/order', data)
        this.$store.commit('setCommodityCount', this.$store.state.commodity_count - 1)
      }
      this.$router.push('/profile/order')
    }

  },

  mounted() {
    // console.log('mounted')
  }
}
</script>

<style scoped>

</style>
