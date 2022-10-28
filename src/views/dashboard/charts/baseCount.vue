<template>
  <div>
<!--    景点、美食、旅馆、线路统计-->
    <div ref="baseCount_ref" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script>
import statistics from "@/api/statistics";

export default {
  name: "baseCount",
  data() {
    return {
      chartInstance: null,
      allData: []
    }
  },

  mounted() {
    this.initChart()
    this.getBaseCount()
    window.addEventListener('resize', this.screenAdapter)
    this.screenAdapter()
  },

  beforeDestroy() {
    window.removeEventListener('resize', this.screenAdapter)
  },

  methods: {

    async getBaseCount() {
      let res = await statistics.getBaseCount()
      this.allData = res.data.baseCount
      this.allData.sort((a, b) => a.value - b.value)
      this.updateChart()
    },

    initChart() {
      this.chartInstance = this.$echarts.init(this.$refs.baseCount_ref, 'chalk')
      const initOption = {
        title: {
          text: '景点、美食、旅馆、线路统计',
          left: 20,
          top: 20
        },
        grid: {
          top:'20%',
          left:'3%',
          right:'6%',
          bottom:'3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line',
            z: 0,
            lineStyle: {
              color: '#2D3443'
            }
          }
        },
        series: [
          {
            type: 'bar',
            label: {
              show: true,
              position: 'right'
            },
            itemStyle: {
              color: new this.$echarts.graphic.LinearGradient(0,0,1,0, [
                {
                  offset: 0,
                  color: '#5052EE'
                },
                {
                  offset: 1,
                  color: '#AB6EE5'
                }
              ])
            }
          }
        ]
      }
      this.chartInstance.setOption(initOption)
    },

    updateChart() {
      const nameList = this.allData.map(item => item.name)
      const valueList = this.allData.map(item => item.value)
      const dataOption = {
        yAxis: {
          data: nameList
        },
        series: [
          {
            data: valueList
          }
        ]
      }
      this.chartInstance.setOption(dataOption)
    },

    screenAdapter() {
      const titleFontSize = this.$refs.baseCount_ref.offsetWidth / 100 * 3.6
      const adapterOption = {
        title: {
          textStyle: {
            fontSize: titleFontSize
          }
        },
        series: [
          {
            barWidth: titleFontSize * 2,
            itemStyle: {
              borderRadius: [0, titleFontSize, titleFontSize, 0]
            }
          }
        ]
      }
      this.chartInstance.setOption(adapterOption)
      this.chartInstance.resize()
    }
  }
}
</script>

<style scoped>

</style>
