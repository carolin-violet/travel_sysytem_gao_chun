<template>
  <div class="w-screen h-auto overflow-x-hidden">
    <!--  公共头-->
    <MyHeader/>

    <nuxt/>

    <!--  公共底-->
    <MyFooter/>
    <!-- 音乐播放器 -->
    <!-- <aPlayer/> -->

    <!-- 进入反馈入口 -->
    <transition name="feedbackEntry">
      <div ref="feedbackEntryRef" v-if="$route.path!=='/feedback'" v-show="showFeedbackEntry" class="feedback-entry fixed right-0 top-1/3 w-20 h-72 py-6 text-white text-center cursor-pointer" style="background-color:#262626" @click="$router.push('/feedback')">
        <i class="el-icon-message text-3xl"></i>
        <span class="block mx-auto my-6 text-xl" style="writing-mode:vertical-rl">欢迎发表反馈意见</span>
      </div>
    </transition>
  </div>
</template>

<script>
import MyHeader from "@/components/MyHeader";
import MyFooter from "@/components/MyFooter";
import aPlayer from "@/components/aPlayer"

export default {
  name: "default",
  components: {
    MyHeader,
    MyFooter,
    aPlayer
  },
  data() {
    return {
      showFeedbackEntry: false
    }
  },
  mounted() {
    // ((window.gitter = {}).chat = {}).options = {
    //   room: 'carolin-violet/travel_gaochun'
    // };

    window.addEventListener('scroll', this.changeFeedbackEntryShow)
  },

  beforeDestroy() {
    window.removeEventListener(this.changeFeedbackEntryShow)
  },

  methods: {
    changeFeedbackEntryShow() {
      this.showFeedbackEntry = document.documentElement.scrollTop > 0 ? true : false
    },
  },

}
</script>

<style scoped>
.feedbackEntry-enter-active, .feedbackEntry-leave-active {
  transition: all 3s;
}
.feedbackEntry-enter, .feedbackEntry-leave-to /* .fade-leave-active below version 2.1.8 */ {
  transform: translateX(100%);
  opacity: 0;
}

.feedbackEntry-enter-to, .feedbackEntry-leave /* .fade-leave-active below version 2.1.8 */ {
  transform: translateX(0);
  opacity: 1;
}
</style>
