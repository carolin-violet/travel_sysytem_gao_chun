<template>
  <div>
    <baidu-map :center="center" :zoom="zoom" @ready="handler" class="map">
      <bm-marker :position="center" animation="BMAP_ANIMATION_BOUNCE">
      </bm-marker>
    </baidu-map>
  </div>
</template>

<script>

export default{
  name: 'MyMap',
  props:['mark_id'],
  data() {
    return {
      center: {
        lng: 118.883914,
        lat: 31.347058
      },
      zoom: 15,
    }
  },
  methods: {
    handler ({BMap, map}) {
      console.log(BMap, map)
      this.getCoordinate()
      // this.zoom = 15
    },

    async getCoordinate() {
      const res = await this.$axios.get(`/coordinate/${this.mark_id}`)
      if (res.data.coordinate) {
        this.center.lng = res.data.coordinate.longitude
        this.center.lat = res.data.coordinate.latitude
        this.zoom = 20
      }
    }
  }
}
</script>

<style scoped>
.map {
  width: 100%;
  height: 100%;
}
</style>
