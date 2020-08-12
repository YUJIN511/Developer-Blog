<template>
  <div class="container-comment-list">
    <template v-for="(data, idx) in commentList">
      <Comment :commentData="data" :key="idx" />
    </template>
  </div>
</template>

<script>
import Comment from "./Comment";

export default {
  props: ["commentList"],
  components: {
    Comment
  }
};
</script>

<style></style>
