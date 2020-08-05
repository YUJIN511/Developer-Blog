<template>
  <div class="container-article-edit">
    <editor></editor>
  </div>
</template>

<script>
import Editor from "@/components/user/article/Editor.vue";
export default {
  components: {
    Editor
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
input {
  padding: 10px;
}
</style>
