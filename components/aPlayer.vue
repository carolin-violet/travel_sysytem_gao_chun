<template>
  <div>
    <aplayer v-if="flag" :audio="audio" :lrcType="3" fixed />
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      flag: false,
      audio: []
    }
  },
  mounted() {
    this.getMusicList()
  },
  methods: {
    async getMusicList() {
      let self = this
      let { status, data: audio } = await axios.get(
        'https://api.i-meto.com/meting/api',
        {
          params: {
            server: 'netease',
            type: 'playlist',
            id: '7464949042'  // 网易云歌单id
          }
        }
      )
      if (status === 200 && audio) {
        self.audio = audio.map(item => {
          return {
            name: item.title,
            artist: item.author,
            url: item.url,
            cover: item.pic,
            lrc: item.lrc
          }
        })
        self.flag = true
      } else {
        console.log(`请求失败`)
      }
    }
  }
}
</script>

<style lang='scss' scoped>

</style>
