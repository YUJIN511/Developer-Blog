<template>
  <div class="container-update-modal">
    <div ref="updateModal" class="update-modal">
      <button class="btn-delete" @click="deletePost">
        게시글
        <span>삭제</span> ❌
      </button>
      <button class="btn-update" @click="updatePost">
        게시글
        <span>수정</span> 📝
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: ["articleId"],
  methods: {
    async deletePost() {
      alert("정말 삭제 하시겠습니까?");
      try {
        await axios.delete(`${this.$apiServer}/post?id=${this.articleId}`);
        location.href = "/";
      } catch (error) {
        console.log(error);
      }
    },
    updatePost() {
      this.$router.push({
        name: "ArticleEdit",
        params: { targetId: this.articleId }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.container-update-modal {
  position: relative;
  top: -70px;
  left: 20px;
  width: 0;
  height: 0;
  .update-modal {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: absolute;
    background-color: white;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.19), 0 1px 1px rgba(0, 0, 0, 0.23);
    width: 150px;
    button {
      color: rgb(0, 0, 0);
      padding: 5px 10px;
      width: 100%;

      &:hover {
        background-color: rgb(250, 250, 250);
      }
    }
    .btn-delete {
      span {
        color: crimson;
      }
    }
    .btn-update {
      span {
        color: teal;
      }
    }
  }
}
</style>
