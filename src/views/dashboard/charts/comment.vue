<template>
  <div>
<!--    所有景点、美食、旅馆、线路的好评数，悬浮可显示好评与差评数量 -->
    <div ref="comment_ref" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script>
import statistics from "@/api/statistics";

export default {
  name: "comment",
  data() {
    return {
      chartInstance: null,
      startValue: 0,
      endValue: 3,
      timerId: null,
      allData: []
    }
  },

  mounted() {
    this.initChart()
    this.getComment()
    this.screenAdapter()
    this.startInterval()
    window.addEventListener('resize', this.screenAdapter)
  },

  beforeDestroy() {
    window.removeEventListener('resize', this.screenAdapter)
    clearInterval(this.timerId)
  },

  methods: {

    async getComment() {
      let res = await statistics.getComment()
      this.allData = res.data.comment
      this.updateChart()
    },

    initChart() {
      this.chartInstance = this.$echarts.init(this.$refs.comment_ref, 'chalk')
      const initOption = {
        title: {
          text: '评论对比',
          top: 20,
          left: 20
        },
        legend: {
          top: '10%',
          left: '100'
        },
        grid: {
          top: '35%',
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis'
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
          }
        ]
      }
      this.chartInstance.setOption(initOption)
      this.chartInstance.on('mouseover', () => {
        clearInterval(this.timerId)
      })
      this.chartInstance.on('mouseout', () => {
        this.startInterval()
      })
    },

    updateChart() {
      const colorArr = [
        ['#fbc2eb', '#a6c1ee'],
        ['#d4fc79', '#96e6a1'],
        ['#5ee7df', '#b490ca'],
        ['#b3ffab', '#12fff7']
      ]
      this.allData = this.allData.sort((a, b) => (b.value[0] + b.value[1]) - (a.value[0] + a.value[1]))
      const nameList = this.allData.map(item => item.name)
      const sumCommentList = this.allData.map(item => item.value[0] + item.value[1])
      const dataOption = {
        tooltip: {
          formatter: arg => {
            const itemObj = this.allData.find(item => item.name === arg[0].name)
            return `${itemObj.name}(${itemObj.mark})的好评率为：${this.getCommentRate(itemObj.value[0], itemObj.value[1]) * 100}%,好评数：${itemObj.value[0]},差评数：${itemObj.value[1]}`
          }
        },
        dataZoom: {
          show: false,
          startValue: this.startValue,
          endValue: this.endValue
        },
        xAxis: {
          data: nameList
        },
        series: [
          {
            data: sumCommentList,
            label: {
              show: true,
              position: 'top'
            },
            itemStyle: {
              color: arg => {
                let targetArr = null
                const itemObj = this.allData.find(item => item.name === arg.name)
                switch (itemObj.mark) {
                  case '线路' : targetArr = colorArr[3];break;
                  case '景点' : targetArr = colorArr[1];break;
                  case '美食' : targetArr = colorArr[0];break;
                  case '旅馆' : targetArr = colorArr[2];break;
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
      const titleFontSize = this.$refs.comment_ref.offsetWidth / 100 * 3.6
      const adapterOption = {
        title: {
          textStyle: {
            fontSize: titleFontSize
          }
        },
        series: [
          {
            barWidth: titleFontSize * 1.5,
            itemStyle: {
              borderRadius: [titleFontSize, titleFontSize, 0, 0], // 重点
            },
          }
        ]
      }
      this.chartInstance.setOption(adapterOption)
      this.chartInstance.resize()
    },

    // 计算好评率
    getCommentRate(positive, negative) {
      let res = null
      if (negative === 0 && positive !== 0) {
        res = 1
      } else if (positive === 0) {
        res = 0
      } else {
        res = positive / (positive + negative)
      }
      return res.toFixed(6)
    },

    // 启动计数器
    startInterval () {
      if (this.timerId) {
        clearInterval(this.timerId)
      }
      this.timerId = setInterval(() => {
        this.startValue++
        this.endValue++
        if (this.endValue > this.allData.length - 1) {
          this.startValue = 0
          this.endValue = 3
        }
        this.updateChart()
      }, 3000)
    }
  },

}
</script>

<style scoped>

</style>
