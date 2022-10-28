<template>
  <div class="w-auto h-16 mx-auto space-x-5 text-white">
    <button class="inline-block w-20 h-12 text-center rounded-2xl bg-opacity-80 leading-10 cursor-pointer" :class="color || 'bg-indigo-300'" :disabled="cur === 1" @click="changeCurPage(cur - 1)">上一页</button>
    <button class="inline-block w-12 h-12 text-center rounded-full bg-opacity-80 leading-10 cursor-pointer" :class="color || 'bg-indigo-300'" v-if="startAndEnd.start > 1 " @click="changeCurPage(1)">1</button>
    <button class="inline-block w-12 h-12 text-center rounded-full bg-opacity-80 leading-10 cursor-pointer" :class="color || 'bg-indigo-300'" v-if="startAndEnd.start > 2 ">...</button>

    <button
      class="inline-block w-12 h-12 text-center rounded-full bg-opacity-80 leading-10 cursor-pointer"
      :class="color || 'bg-indigo-300'"
      v-for="(page, index) in startAndEnd.end"
      :key="index"
      v-if="page >= startAndEnd.start"
      @click="changeCurPage(page)"
    >
      {{ page }}
    </button>


    <button class="inline-block w-12 h-12 text-center rounded-full bg-opacity-80 leading-10 cursor-pointer" :class="color || 'bg-indigo-300'" v-if="startAndEnd.end < totalPage - 1">...</button>
    <button class="inline-block w-12 h-12 text-center rounded-full bg-opacity-80 leading-10 cursor-pointer" :class="color || 'bg-indigo-300'" v-if="startAndEnd.end < totalPage" @click="changeCurPage(totalPage)">{{ totalPage }}</button>
    <button class="inline-block w-20 h-12 text-center rounded-2xl bg-opacity-80 leading-10 cursor-pointer" :class="color || 'bg-indigo-300'" :disabled="cur === totalPage" @click="changeCurPage(cur + 1)">下一页</button>
    <button class="inline-block w-20 h-12 text-center rounded-2xl bg-opacity-80 leading-10 cursor-pointer" :class="color || 'bg-indigo-300'"> 共{{ total || 0 }}条 </button>
    <button class="inline-block w-20 h-12 text-center rounded-2xl bg-opacity-80 leading-10 cursor-pointer" :class="color || 'bg-indigo-300'"> 第{{ cur }}页 </button>
  </div>
</template>

<script>
export default {
  name: "pagination",
  props: ['total', 'cur', 'limit', 'continueNum', 'color'],
  computed: {
    totalPage() {
      // 向上取整
      return Math.ceil(this.total / this.limit)
    },
    startAndEnd() {
      const {cur, totalPage, continueNum} = this
      let start = 0, end = 0
      if (continueNum > totalPage) {
        start = 1
        end = totalPage
      } else {
        start = cur - continueNum/2
        end = cur + continueNum/2

        if (start < 1) {
          start = 1
          end = continueNum
        } else if (end > totalPage) {
          end = totalPage
          start = end - continueNum + 1
        }
      }
      return {start, end}
    }
  },
  methods: {
    changeCurPage(page) {
      this.$emit('changePage', page)
    }
  }
}
</script>

<style scoped>

</style>
