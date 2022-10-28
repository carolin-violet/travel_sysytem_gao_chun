<template>
  <div class="app-container">
    <h1>{{ $route.query.name }}</h1>
<!--    地图-->
    <baidu-map class="bm-view" :center="center" :zoom="zoom"  scroll-wheel-zoom @ready="handleMap">
      <bm-marker :position="center" :dragging="true" animation="BMAP_ANIMATION_DROP" :icon="subwayIcon" @dragend="handleDragend"></bm-marker>
      <bm-marker :position="address" :dragging="true" animation="BMAP_ANIMATION_BOUNCE" :icon="siteIcon" @dragend="handleDragend"></bm-marker>
      <bm-control :offset="{ width:'10px', height: '20px'}">
        <el-autocomplete
          v-model="searchKey"
          :fetch-suggestions="querySearch"
          placeholder="请输入详细地址"
          :trigger-on-focus="false"
          @select="handleSelect"
          clearable
          class="search-input"
        />
        <el-button @click="changeGeoInfo">保存变化坐标</el-button>
        <el-button @click="resetMap">重置</el-button>
      </bm-control>
      <bm-local-search
        :keyword="searchKey"
        :panel="isShowPanel"
        auto-viewport
        :zoom="zoom"
        style="display: none;"
        :location="center"
        @markersset="setAddressList"
      ></bm-local-search>
    </baidu-map>
  </div>
</template>

<script>
import coordinate from "@/api/coordinate";

export default {
  name: "coordinate",
  data() {
    return {
      mark_id: "",
      name: "",
      searchKey: "",
      zoom: 15,
      BMap: null,
      map: null,
      subwayIcon: {
        url: require('@/assets/subwayIcon.png'),
        size: {
          width: 100,
          height: 100
        },
        opts: {
          imageOffset: {
            width: 60,
            height: 30
          },
          imageSize: {
            width: 50,
            height: 40
          }
        }
      },
      siteIcon: {
        url: require('@/assets/site.png'),
        size: {
          width: 100,
          height: 100
        },
        opts: {
          imageOffset: {
            width: 50,
            height: 30
          },
          imageSize: {
            width: 50,
            height: 50
          }
        }
      },
      center: {
        lng: 118.883914,
        lat: 31.347058
      },
      address: {},
      isShowPanel: true,
      coordinateObj: {},
    }
  },

  created() {
    this.mark_id = this.$route.query.mark_id
    this.name = this.$route.query.name
    this.getGeoInfo()
  },

  methods: {

    // 调用接口获取当前站点坐标
    async getGeoInfo() {
      let res = await coordinate.getCoordinate(this.mark_id)
      if (res.code === 20000) {
        if (res.data.coordinate) {
          this.center.lng = res.data.coordinate.longitude
          this.center.lat = res.data.coordinate.latitude
          this.address.lat = res.data.coordinate.latitude
          this.address.lat = res.data.coordinate.latitude
          this.coordinateObj = {...res.data.coordinate}
          const point = new this.BMap.Point(this.center.lng, this.center.lat)
          this.map.panTo(point)
        }
      }
    },

    // 调用接口改变坐标信息
    async changeGeoInfo() {
      const data = {...this.coordinateObj}
      data.longitude = this.address.lng
      data.latitude = this.address.lat
      if (this.coordinateObj.hasOwnProperty("id")) {
        let res = await coordinate.updateCoordinate(data)
        if (res.code === 20000) this.$message.success('修改坐标成功')
      } else {
        data.markId = this.mark_id
        let res = await coordinate.addCoordinate(data)
        if (res.code === 20000) this.$message.success('添加坐标成功')
      }
    },

    // 地图渲染完后的回调
    handleMap({BMap, map}) {
      this.map = map
      this.BMap = BMap
    },

    handleSelect(site) {
      console.log(site)
      this.address = {...site.point}
    },

    querySearch(queryString, cb) {
      var that = this
      var myGeo = new this.BMap.Geocoder()
      myGeo.getPoint(queryString, function(point) {
        if (point) {
          that.addShopRuleForm.coordinate = point
          that.makerCenter(point)
        } else {
          that.addShopRuleForm.coordinate = null
        }
      }, this.locationCity)
      var options = {
        onSearchComplete: function(results) {
          if (local.getStatus() === 0) {
            // 判断状态是否正确
            var s = []
            for (var i = 0; i < results.getCurrentNumPois(); i++) {
              var x = results.getPoi(i)
              var item = { value: x.address + x.title, point: x.point }
              s.push(item)
              cb(s)
            }
          } else {
            cb()
          }
        }
      }
      var local = new this.BMap.LocalSearch(this.map, options)
      local.search(queryString)
    },

    // 進行搜索后获取所有相关信息，并将第一个作为结果坐标返回
    setAddressList(e) {
      // console.log('search-res:', e);
      this.address = {...e[0]['point']}
    },

    // 站点鼠标拖动结束后的回调
    handleDragend(e) {
      this.address = {...e['point']}
    },

    // 重置
    resetMap() {
      this.address = {}
      this.searchKey = ""
      this.map.setZoom(this.zoom)
      const point = new this.BMap.Point(this.center.lng, this.center.lat)
      this.map.panTo(point)
    }
  }
}
</script>

<style scoped>
.bm-view{
  width: 100%;
  height: 1080px;
  max-height: 1440px;
}

.search-input{
  width: 300px;
  height: 40px;
  margin: 0 20px;
}
</style>
