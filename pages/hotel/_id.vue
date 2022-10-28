<template>
  <div class="w-full h-auto flex justify-center">
    <div>
      <detailArticle/>
      <comment :mark_id="$route.params.id"/>
      <MyMap :mark_id="$route.params.id" style="width: 100%;height: 500px;" class="my-3"/>
    </div>
  </div>
</template>

<script>
import detailArticle from "@/components/article";
import comment from "@/components/comment";
import MyMap from "@/components/MyMap";


export default {
  name: "hotel_detail",
  components: {
    detailArticle,
    comment,
    MyMap
  },
  data() {
    return {
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
