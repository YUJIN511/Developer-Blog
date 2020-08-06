<template>
  <div class="container-article-edit">
    <editor ref="editor"></editor>
    <footer>
      <button class="btn btn-cancel">취소</button>
      <button class="btn btn-submit" @click="done">작성 완료</button>
    </footer>
  </div>
</template>

<script>
import Editor from "@/components/user/article/Editor.vue";
export default {
  components: {
    Editor
  },
  methods: {
    done() {
      this.$refs.editor.openSummaryModal();
    }
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
.btn {
  height: 40px;
  border-radius: 5px;
}
.btn-submit {
  background: #6699cc;
  color: white;
  margin-left: 10px;
}
.btn-cancel {
  background: rgb(185, 185, 185);
  color: white;
}
</style>
