<template>
  <div>
<!--    注册游客性别占比的占比饼图-->
    <div ref="gender_ref" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script>
import statistics from "@/api/statistics";

export default {
  name: "gender",
  data () {
    return {
      chartInstance: null,
      allData: []
    }
  },

  computed: {
    total() {
      let total = 0
      this.allData.forEach(item => {
        total += item.value
      })
      return total
    }
  },

  mounted() {
    this.initChart()
    this.getGender()
    window.addEventListener('resize', this.screenAdapter)
    this.screenAdapter()
  },

  beforeDestroy() {
    window.removeEventListener('resize', this.screenAdapter)
  },

  methods: {

    async getGender() {
      let res = await statistics.getGender()
      this.allData = res.data.list
      this.updateChart()
    },

    initChart() {
      this.chartInstance = this.$echarts.init(this.$refs.gender_ref, 'chalk')
      const initOption = {
        title: {
          text: '注册游客性别占比',
          left: 20,
          top: 20
        },
        legend: {
          top: '15%',
          icon: 'circle',
          right: '10'
        },
        tooltip: {
          show: true,
          formatter: arg => {
            return `总数：${arg.value}, 占比：${arg.percent}%`
          }
        },
        series: [
          {
            type: 'pie',
            // 默认不显示标签
            label: {
              show: true
            },
            // 悬浮显示标签信息
            emphasis: {
              label: {
                show: true
              },
              itemStyle: {
                shadowBlur: 5
              }
            }
          }
        ]
      }
      this.chartInstance.setOption(initOption)
    },

    updateChart() {
      const legendData = this.allData.map(item => item.name)
      const dataOption = {
        legend: {
          data: legendData
        },
        series: [
          {
            data: this.allData,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.3)'
              },
              normal: {
                color: arg => {
                  if (arg.name === '男') {
                    return '#adffd6'
                  } else {
                    return '#bba8ff'
                  }
                }
              }
            }
          }
        ]
      }
      this.chartInstance.setOption(dataOption)
    },

    screenAdapter() {
      const titleFontSize = this.$refs.gender_ref.offsetWidth / 100 * 3.6
      const adapterOption = {
        title: {
          textStyle: {
            fontSize: titleFontSize * 1.3
          }
        },
        legend: {
          itemWidth: titleFontSize * 2,
          itemHeight: titleFontSize * 2,
          itemGap: titleFontSize,
          textStyle: {
            fontSize: titleFontSize
          }
        },
        series: [
          {
            radius: titleFontSize * 7,
            center: ['50%', '60%']
          }
        ]
      }
      this.chartInstance.setOption(adapterOption)
      this.chartInstance.resize()
    },

  }
}
</script>

<style scoped>

</style>
