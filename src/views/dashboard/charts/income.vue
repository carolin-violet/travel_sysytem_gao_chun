<template>
  <div>
<!--    近12个月的收益统计-->
    <div ref="income_ref" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script>
import statistics from "@/api/statistics";

export default {
  name: "income",
  data() {
    return {
      chartInstance: null,
      allData: []
    }
  },

  mounted() {
    this.initChart()
    this.getIncome()
    window.addEventListener('resize', this.screenAdapter)
    this.screenAdapter()
  },

  beforeDestroy() {
    window.removeEventListener('resize', this.screenAdapter)
  },

  methods: {

    async getIncome() {
      let res = await statistics.getIncome()
      const tempData = res.data.income
      for (const key in tempData) {
        this.allData.push({
          name: key,
          value: tempData[key].toFixed(2)
        })
      }
      this.allData.sort((a, b) => new Date(a.name) - new Date(b.name))
      this.updateChart()
    },

    initChart() {
      this.chartInstance = this.$echarts.init(this.$refs.income_ref, 'chalk')
      const initOption = {
        title: {
          text: '近一年收入统计',
          top: 20,
          left: 20
        },
        grid: {
          top: '20%',
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        tooltip: {
          show: true
        },
        xAxis: {
          type: 'category'
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            type: 'bar',
            label: {
              show: true,
              position: 'top'
            }
          }
        ]
      }
      this.chartInstance.setOption(initOption)
    },

    updateChart() {
      const colorArr = [
        ['#0BA82C', '#4FF778'],
        ['#2E72BF', '#23E5E5'],
        ['#5052EE', '#AB6EE5'],
      ]
      const nameList = this.allData.map(item => item.name)
      const valueList = this.allData.map(item => item.value)
      const dataOption = {
        xAxis: {
          data: nameList
        },
        series: [
          {
            data: valueList,
            itemStyle: {
              color: arg => {
                let targetArr = null
                if (arg.value > 20000) {
                  targetArr = colorArr[0]
                } else if (arg.value > 10000) {
                  targetArr = colorArr[1]
                } else {
                  targetArr = colorArr[2]
                }
                return new this.$echarts.graphic.LinearGradient(0,0,0,1, [
                  {
                    offset: 0,
                    color: targetArr[0]
                  },
                  {
                    offset: 1,
                    color: targetArr[1]
                  }
                ])
              }
            }
          }
        ]
      }
      this.chartInstance.setOption(dataOption)
    },

    screenAdapter() {
      const titleFontSize = this.$refs.income_ref.offsetWidth / 100 * 3.6
      const adapterOption = {
        title: {
          textStyle: {
            fontSize: titleFontSize * 0.7
          }
        },
        series: [
          {
            barWidth: titleFontSize,
            itemStyle: {
              barBorderRadius: [titleFontSize / 2, titleFontSize / 2, 0, 0]
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
