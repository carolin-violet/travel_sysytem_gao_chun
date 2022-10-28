<template>
  <div class="w-full h-auto flex justify-center">
    <div>
      <detailArticle/>
      <comment :mark_id="$route.params.id"/>
    </div>
  </div>
</template>

<script>
import detailArticle from "@/components/article";
import comment from "@/components/comment";

export default {
  name: "delicacy_detail",
  components: {
    detailArticle,
    comment
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
