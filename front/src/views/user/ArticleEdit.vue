<template>
  <div class="container-article-edit">
    <Tiptap></Tiptap>
  </div>
</template>

<script>
import Tiptap from "@/components/user/editor/Editor.vue";
export default {
  components: {
    Tiptap
  }
};
</script>

<style lang="scss" scoped>
.container-article-edit {
  min-width: 100vw;
  min-height: 100vh;
  padding: 60px;
  background-color: #f1f4f7;
}
</style>
