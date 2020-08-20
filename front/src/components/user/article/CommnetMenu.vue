<template>
  <div ref="commentMenu" class="container-comment-menu" v-show="isShow">
    <div class="absolute">
      <button @click="editStart">
        수정
      </button>
      <button @click="deleteComment">
        삭제
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: ["content", "commentId", "btnMore"],
  data: function() {
    return {
      isShow: false
    };
  },
  methods: {
    async deleteComment() {
      if (confirm("정말 삭제하시겠습니까")) {
        await axios.delete(`${this.$apiServer}/comment?id=${this.commentId}`);
        this.$router.go();
      }
    },
    editStart() {
      this.$emit("editStart");
      this.isShow = false;
    },
    toggleMenu() {
      this.isShow = !this.isShow;
    },
    showMenu() {
      this.isShow = true;
    },
    closeMenu() {
      this.isShow = false;
    }
  },
  mounted() {
    const commentMenu = this.$refs.commentMenu;

    window.addEventListener("click", e => {
      if (
        !commentMenu.contains(e.target) &&
        e.target !== document.querySelector(".container-comment .btn-more")
      ) {
        this.closeMenu();
      }
    });
  }
};
</script>

<style lang="scss" scoped>
.container-comment-menu {
  position: relative;
  width: 0;
  height: 0;
  left: 5px;
  top: 30px;
  .absolute {
    border-radius: 2px;
    border: 1px solid #c0c0c0;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.19), 0 1px 1px rgba(0, 0, 0, 0.23);
    width: 90px;
    position: absolute;
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    button {
      color: #727272;
      font-size: 0.8em;
      padding: 7px 10px;
      &:hover {
        background-color: aliceblue;
      }
    }
  }
}
</style>
