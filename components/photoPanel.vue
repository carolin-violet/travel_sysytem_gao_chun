<template>
  <div class="relative flex justify-center  w-screen h-auto py-32 px-16" style="background-color:#f6f6f6">
    <section class="flex flex-wrap justify-items-start w-auto">
      <div v-for="item in imageList" :key="item.id" @click="toDetail(item.id)" class="item-container mx-32 my-2 cursor-pointer" style="width:400px;height: 500px;">
        <div style="width:400px;height: 300px;" class="photo-container relative overflow-hidden shadow-xl">
          <el-image :src="item.cover" lazy class="w-full h-full">
            <div slot="placeholder" class="image-slot">
              <img src="/loading.gif" alt="">
            </div>
            <div slot="error" class="image-slot">
              <img :src="require('@/assets/svg/图片加载失败.svg')" alt="">
            </div>
          </el-image>
        </div>
        <div class="mt-6">
          <h2 class="text-3xl font-black">{{ item.name }}</h2>
          <p class="mt-6 text-2xl font-thin">
            <span class="description relative">
              {{ item.description }}
            </span>
          </p>
        </div>
      </div>
    </section>
  </div>
</template>

<script>

export default {
  name: "photoPanel",
  props: ["imageList"],
  data() {
    return {

    }
  },
  methods:{
    toDetail(id) {
      this.$router.push({
        path: `${this.$route.fullPath}/${id}`
      })
    }
  }
}
</script>

<style scoped>

/* 鼠标悬停效果 */
.item-container img{
  position: relative;
  transition: transform 1s ease-in-out;
}
.item-container:hover img{
  transform: scale(1.05);
}
.item-container:hover .photo-container::before{
  position: absolute;
  content: "";
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.2);
  z-index: 100 ;
}

.description{
  background: linear-gradient(transparent calc(100% - 1px), #111 0);
}
</style>
