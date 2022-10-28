<template>
  <div>
<!--   门票与拼团的总数占比饼图 -->
    <div ref="scenicAndRoute_ref" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script>
import statistics from "@/api/statistics";

export default {
  name: "scenicAndRoute",
  data () {
    return {
      chartInstance: null,
      titleFontSize: 0,
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
    window.addEventListener('resize', this.screenAdapter)
    this.getScenicAndRoute()
    this.screenAdapter()
  },

  beforeDestroy() {
    window.removeEventListener('resize', this.screenAdapter)
  },

  methods: {

    async getScenicAndRoute() {
      let res = await statistics.getScenicAndRoute()
      this.allData = res.data.scenicAndRoute
      this.updateChart()
    },

    initChart() {
      this.chartInstance = this.$echarts.init(this.$refs.scenicAndRoute_ref, 'chalk')
      const initOption = {
        title: {
          text: '门票和拼团的订单总数占比',
          left: 20,
          top: 20
        },
        legend: {
          top: '15%',
          icon: 'diamond',
          left: '20',
          orient: 'vertical'
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
                  if (arg.name === '景点门票') {
                    return '#D9AFD9'
                  } else {
                    return '#adcbff'
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
      this.titleFontSize = this.$refs.scenicAndRoute_ref.offsetWidth / 100 * 3.6
      const adapterOption = {
        title: {
          textStyle: {
            fontSize: this.titleFontSize * 1.3
          }
        },
        legend: {
          itemWidth: this.titleFontSize * 2,
          itemHeight: this.titleFontSize * 2,
          itemGap: this.titleFontSize,
          textStyle: {
            fontSize: this.titleFontSize
          }
        },
        series: [
          {
            radius: this.titleFontSize * 8,
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
