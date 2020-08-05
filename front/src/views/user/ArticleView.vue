<template>
  <div class="container-article-view">
    <viewer></viewer>
  </div>
</template>

<script>
import Viewer from "@/components/user/article/Viewer.vue";
export default {
  components: {
    Viewer
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/_variables.scss";

.container-article-view {
  min-width: 100vw;
  min-height: 100vh;
  padding: 60px;
  background-color: $bgColor;
}
</style>
