<template>
  <div class="min-h-screen py-24 px-24">
    <el-form ref="orderFormRef" :model="order" :rules="orderRule" label-width="150px" label-position="right" label-suffix="：" class="shadow-2xl">
      <h1 class="text-center font-black" style="font-size: 40px;">
        <i class="iconfont icon-a-02_menpiao" style="font-size: 40px;"></i>
        {{ $route.query.mark === 'route' ? itemInfo.title : itemInfo.name }}
      </h1>
      <el-form-item label="单价">
        <span>￥{{ itemInfo.price }}</span>
      </el-form-item>
      <el-form-item label="折扣价" v-if="itemInfo.price !== itemInfo.discount_price">
        <span>￥{{ itemInfo.discount_price }}</span>
      </el-form-item>
      <el-form-item label="成年人人数">
        <el-input-number size="medium" :min="0" v-model="order.adult"></el-input-number>
      </el-form-item>
      <el-form-item label="未成年人人数">
        <el-input-number size="medium" :min="0" v-model="order.child"></el-input-number>
      </el-form-item>
      <el-form-item label="总额">
        <span>{{ order.amount }}</span>
      </el-form-item>
      <el-form-item label="注">
        <small class="text-gray-300">成年人全价，未成年人半价</small>
      </el-form-item>

      <el-divider></el-divider>

      <el-form-item label="预约时间" prop="appointment_time">
        <el-date-picker
          v-model="order.appointment_time"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="预约人姓名" prop="name">
        <el-input v-model="order.name"></el-input>
      </el-form-item>
      <el-form-item label="预约人联系方式" prop="phone">
        <el-input v-model="order.phone"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="commitOrder">点击预约</el-button>
      </el-form-item>
    </el-form>

    <el-dialog>预约成功后弹框提示，可以前往购物车支付</el-dialog>
  </div>
</template>>

<script>
import { mapState } from 'vuex'
import { validPhone } from '@/utils/validate'

export default{
  name: 'order',
  data() {
    const validatePhone = (rule, value, callback) => {
      validPhone(value) ? callback() : callback(new Error('手机号格式错误'))
    }
    return {
      itemInfo: {},
      order: {
        adult: 0,
        child: 0,
        amount: 0,
        appointmentTime: null,
        name: '',
        phone: '',
        is_paid: 0,
      },
      orderRule:{
        appointment_time: [{required: true, trigger: 'change', message: '请选择预约时间'}],
        name: [{required: true, trigger: 'blur', message: '请填写联系人姓名'}],
        phone: [
          {required: true, trigger: 'blur', message: '请填写联系人电话'},
          {validator: validatePhone, trigger: 'change'},
        ]
      }
    }
  },

  mounted() {
    if(this.$route.query.mark === 'route') {
      this.getRoutePrice()
    } else if (this.$route.query.mark === 'scenic') {
      this.getScenicPrice()
    }
  },

  watch: {
    order: {
      handler: function(newVal, oldVal) {
        this.order.amount = (newVal.adult +  newVal.child/2)*this.itemInfo.discount_price
      },
      deep: true
    }
  },

  computed: {
    ...mapState({
      userInfo: state => state.userInfo
    })
  },

  methods:{
    // 获取推荐线路的价钱
    async getRoutePrice() {
      const res = await this.$axios.get(`/travelRoute/${this.$route.query.commodity_id}/price`)
      this.itemInfo = res.data
    },

    // 获取景点门票价钱
    async getScenicPrice() {
      const res = await this.$axios.get(`/scenicSpot/${this.$route.query.commodity_id}/price`)
      this.itemInfo = res.data
    },

    // 提交订单
    commitOrder() {
      if (this.order.amount == 0) {
        this.$message.warning('请至少添加一个成年人或未成年人')
        return;
      }
      this.$refs.orderFormRef.validate(async valid => {
        if (valid) {
          this.$store.dispatch('hasAuth')
          const data = Object.assign({}, this.order, this.$route.query)
          data.tourist_id = this.userInfo.id
          const res = await this.$axios.post('/order', data)
          if (res.code === 20000) {
            this.$message({
              message: '预约成功，请及时前往购物车支付',
              type: 'success',
              duration: '3000'
            })
            this.$store.commit('setCommodityCount', res.data.commodity_count)
            this.$router.push('/route')
          }
        } else {
          console.log('error submit');
        }
      })

    }
  }
}
</script>
