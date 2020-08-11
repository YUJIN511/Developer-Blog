<template>
  <div class="container-comment-reply-list">
    <template v-for="(data, idx) in replyList">
      <Reply :replyData="data" :key="idx" />
    </template>
  </div>
</template>

<script>
import axios from "axios";
import Reply from "./CommentReply.vue";
export default {
  components: {
    Reply
  },
  props: ["commentId"],
  data: function() {
    return {
      replyList: ""
    };
  },
  methods: {
    async getData() {
      try {
        const res = await axios.get(
          `${this.$apiServer}/comment/allReply/?id=${this.commentId}&postid=${this.$route.query.id}`
        );
        this.replyList = res.data;
        console.dir(this.replyList);
      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.container-comment-reply-list {
  background-color: aliceblue;
}
</style>
