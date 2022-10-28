<template>
  <div>
    <el-select v-model="selectRegion.province.code" clearable @clear="clearProvince" @change="changeProvince" placeholder="请选择所在省">
      <el-option
        v-for="(value, key, index) in regionData['86']"
        :key="index"
        :label="value"
        :value="key">
      </el-option>
    </el-select>
    <el-select v-model="selectRegion.city.code" clearable @clear="clearCity" @change="changeCity" placeholder="请选择所在市">
      <el-option
        v-for="(value, key, index) in regionData[selectRegion.province.code]"
        :key="index"
        :label="value"
        :value="key">
      </el-option>
    </el-select>
    <el-select v-model="selectRegion.county.code" clearable @clear="clearCounty" @change="changeCounty" placeholder="请选择所在区/县">
      <el-option
        v-for="(value, key, index) in regionData[selectRegion.city.code]"
        :key="index"
        :label="value"
        :value="key">
      </el-option>
    </el-select>
  </div>
</template>

<script>
import area from '@/assets/area.json'

export default {
  name: 'areaSelector',
  props:['echo'],
  data() {
    return {
      selectRegion: {
        province: {
          code: '',
          name: ''
        },
        city: {
          code: '',
          name: ''
        },
        county: {
          code: '',
          name: ''
        },
      },
      regionData: null
    }
  },
  model: {
    prop: 'myArea',
    event: 'myChange'
  },
  created() {
    this.regionData = area
  },
  mounted() {
    this.$nextTick(() => {
      this.echo ? this.selectRegion = this.echo : ""
    })
  },

  methods: {
    changeProvince(val) {
      this.selectRegion.province.name = this.regionData['86'][val]
      this.clearCity()
      this.$emit('myChange', JSON.stringify(this.selectRegion))
    },
    changeCity(val) {
      this.selectRegion.city.name = this.regionData[this.selectRegion.province.code][val]
      this.clearCounty()
      this.$emit('myChange', JSON.stringify(this.selectRegion))
    },
    changeCounty(val) {
      this.selectRegion.county.name = this.regionData[this.selectRegion.city.code][val]
      this.$emit('myChange', JSON.stringify(this.selectRegion))
    },

    clearProvince() {
      this.selectRegion = {
        province: {
          code: '',
          name: ''
        },
        city: {
          code: '',
          name: ''
        },
        county: {
          code: '',
          name: ''
        }
      }
      this.$emit('change', this.selectRegion)
    },
    clearCity() {
      this.selectRegion.city ={
          code: '',
          name: ''
      }
      this.selectRegion.county ={
          code: '',
          name: ''
      }
      this.$emit('change', this.selectRegion)
    },
    clearCounty() {
      this.selectRegion.county ={
          code: '',
          name: ''
      }
      this.$emit('change', this.selectRegion)
    }
  }
}
</script>
