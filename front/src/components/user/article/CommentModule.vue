<template>
  <div class="container-module-comment">
    <span>댓글 {{ commentCnt }}개</span>
    <textarea
      class="input-comment"
      v-model="commentContent"
      placeholder="댓글 입력하기"
    >
    </textarea>
    <div class="flex-end">
      <button>댓글 작성</button>
    </div>
    <Comment />
  </div>
</template>

<script>
import axios from "axios";
import Comment from "./Comment.vue";
import { mapGetters } from "vuex";

export default {
  props: ["postId"],
  data: function() {
    return {
      commentCnt: 0,
      commentContent: ""
    };
  },
  components: {
    Comment
  },
  method: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    submitComment() {
      axios.post(`${this.$apiServer}/comment/write`, {
        content: this.commentContent,
        email: this.getUserInfo,
        postid: this.postId
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.container-module-comment {
  margin-top: 40px;
}
.input-comment {
  margin: 20px 10px;
  margin-bottom: 5px;
  margin-right: 0px;
  width: 98%;
  height: 8rem;
  resize: none;
  border-color: rgb(211, 211, 211);
  padding: 0.5rem 0.7rem;
  &::placeholder {
    color: rgb(197, 197, 197);
  }
}
.flex-end {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  button {
    color: dodgerblue;
    margin-right: 10px;
  }
}
</style>
