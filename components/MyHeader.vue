<template>
<!--  整体头部部分-->
  <transition name="nav">
    <div class="nav w-screen h-28 z-50" :class="[showFixedNav?'fixed':'absolute',showFixedNav?'bg-white':'']" v-show="showHead">
    <header class="relative w-full h-full font-black">
<!--      logo-->
      <section class="absolute w-1/5 h-12 transform -translate-y-1/2" v-if="$route.path !== '/' || showFixedNav">
        <nuxt-link to="/" title="相约高淳 蟹逅慢城">
          <img class="h-32 w-32" src="/logo.png" alt="美丽高淳-南京后花园" title="相约高淳 蟹逅慢城">
        </nuxt-link>
      </section>
<!--      导航栏-->
      <nav class="absolute top-12 right-12 h-28 text-2xl space-x-12" :class="showFixedNav||$route.path !== '/'?'text-black':'text-white'">
        <nuxt-link to="/hotel" :class="showFixedNav||$route.path !== '/'?'nav-item-fixed':'nav-item'" class="relative" title="游高淳-旅馆">度假酒店</nuxt-link>
        <nuxt-link to="/delicacy" :class="showFixedNav||$route.path !== '/'?'nav-item-fixed':'nav-item'" class="relative" title="游高淳-美食">当地美食</nuxt-link>
        <nuxt-link to="/scenic" :class="showFixedNav||$route.path !== '/'?'nav-item-fixed':'nav-item'" class="relative" title="游高淳-景点">热门景区</nuxt-link>
        <nuxt-link to="/route" :class="showFixedNav||$route.path !== '/'?'nav-item-fixed':'nav-item'" class="relative" title="游高淳-旅游线路">推荐路线</nuxt-link>
        <nuxt-link to="/notice" :class="showFixedNav||$route.path !== '/'?'nav-item-fixed':'nav-item'" class="relative" title="游高淳-公告">活动资讯</nuxt-link>
        <nuxt-link to="/note" :class="showFixedNav||$route.path !== '/'?'nav-item-fixed':'nav-item'" class="relative" title="游高淳-游记">慢城游记</nuxt-link>
        <nuxt-link to="/feedback" :class="showFixedNav||$route.path !== '/'?'nav-item-fixed':'nav-item'" class="relative" title="游高淳-反馈">投诉与建议</nuxt-link>
        <nuxt-link v-if="!isLogin" to="/login" :class="showFixedNav||$route.path !== '/'?'nav-item-fixed':'nav-item'" class="relative" title="游高淳-登录">登录</nuxt-link>
        <el-dropdown v-else>
          <span class="el-dropdown-link">
            <el-avatar :src="userInfo.avatar" :size="30">
            <i class="el-icon-user-solid"></i>
          </el-avatar>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item style="border-bottom:1px solid #409EFF">{{ userInfo.nickName }}</el-dropdown-item>
            <el-dropdown-item>
              <i class="el-icon-user"></i>
              <nuxt-link to="/profile">个人中心</nuxt-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-badge :value="CommodityCount" :hidden="CommodityCount === 0" class="item">
                <i class="el-icon-shopping-cart-full"></i>
              </el-badge>
              <nuxt-link to="/shoppingCart">购物车</nuxt-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <i class="el-icon-switch-button" @click="handleLogout"></i>
              <span @click="handleLogout">退出登录</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </nav>
    </header>
  </div>
  </transition>

</template>

<script>
import { throttle } from 'throttle-debounce';
import { mapState, mapGetters } from 'vuex'

export default {
  name: "MyHeader",
  data() {
    return {
      imageUrl: require('@/assets/images/avatar.webp'),
      showFixedNav: false,
      showHead: true,
      changeShowFixedNav: null
    }
  },

  watch: {
    showFixedNav: {
      handler: function(newVal, oldVal) {
        if (newVal) {
          this.showHead = false
          this.$nextTick(()=> {
            this.showHead = true
          })
        }
      },
      immediate: false
    }
  },

  mounted() {
    this.changeShowFixedNav = throttle(100, () => {
      this.showFixedNav = document.documentElement.scrollTop > 100 ? true : false
    })
    window.addEventListener('scroll', this.changeShowFixedNav)
  },
  beforeDestroy() {
    window.removeEventListener(this.changeShowFixedNav)
  },

  computed: {
    ...mapState({
      userInfo: state => state.userInfo
    }),
    ...mapGetters(['isLogin', 'CommodityCount'])
  },
  methods: {
    handleLogout() {
      this.$store.commit('logout')
      this.$cookies.removeAll()
      this.$router.replace('/login')
    },
  }
}
</script>

<style scoped>
.nav-item:after{
  position: absolute;
  content: "";
  width: 0;
  height: 1px;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  background-color: #fff;
  transition: all 0.25s;
}
.nav-item:hover:after{
  width: 100%;
}

.nav-item-fixed:after{
  position: absolute;
  content: "";
  width: 0;
  height: 1px;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  background-color: #111;
  transition: all 0.25s;
}
.nav-item-fixed:hover:after{
  width: 100%;
}

.nav-enter-active {
  transition: all 1s;
}

.nav-enter, .nav-leave-to {
  transform: translateY(-100%);
}

.nav-enter-to, .nav-leave {
  transform: translateY(0);
}

</style>
