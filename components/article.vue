<template>
  <div class="my-36 pt-6 shadow-2xl" style="max-width: 1000px; min-width: 800px; min-height: 100vh;background-color:#f6f6f6">
    <h1 class="text-center text-4xl">{{ article.title }}</h1>
    <article v-html="article.content_html" class="article py-12 px-16"></article>
  </div>
</template>

<script>

export default{
  name: 'detailArticle',
  data() {
    return {
      article: {
        title: '',
        content_html: ''
      }
    }
  },
  created() {
    this.getArticle()
  },
  methods: {
    async getArticle() {
      let res = await this.$axios.get(`/article/${this.$route.params.id}`)
      this.article = res.data.article
    }
  }
}
</script>

<style scope lang="less">
.article{
  p{
    margin-top: 10px;
    font-size: 16px;
    font-family: YaKuHan;
    img{
      width: 800px;
      height: auto;
      margin: 0 auto;
    }
  }
}
</style>
