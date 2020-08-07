<template>
  <div class="container-blog">
    <header></header>
    <main></main>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  methods: {
    ...mapGetters({}),
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/user/blog/Blog.scss";
</style>
