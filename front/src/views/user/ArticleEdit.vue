<template>
  <div class="container-article-edit">
    <form action="javascript:void(0)">
      <input type="text" placeholder="글 제목" v-model="title" />
      <textarea
        name
        id
        cols="60"
        rows="10"
        placeholder="글 내용"
        v-model="content"
      ></textarea>
      <input type="text" placeholder="태그1, 태그2" v-model="tags" />
      <button @click="submit">확인</button>
    </form>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";
export default {
  data: function() {
    return {
      title: "",
      content: "",
      tags: ""
    };
  },

  //   {
  //   "content": "string",
  //   "id": 0,
  //   "title": "string",
  //   "writer": "string"
  // }
  methods: {
    ...mapGetters({
      getEmail: "user/getEmail"
    }),
    submit() {
      //console.log(this.title, this.content, this.tags, this.getEmail());
      const tagList = this.tags.split("#");
      let tagQuery = "";
      for (let i = 1; i < tagList.length; i++) {
        tagQuery += `hashtagList=${tagList[i]}&`;
      }
      tagQuery = tagQuery.substring(0, tagQuery.length - 1);
      //console.log(tagQuery.substring(0, tagQuery.length - 1));
      axios
        .post(`${SERVER_URL}/api/post?${tagQuery}`, {
          title: this.title,
          content: this.content,
          id: 0,
          writer: this.getEmail()
        })
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
      alert("작성 완료");
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/_variables.scss";

.container-article-edit {
  background-color: $bgColor;
  min-width: 100vw;
  min-height: 100vh;
  padding-top: 60px;
  display: flex;
  justify-content: center;
}
form {
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-top: 1px solid #cccccc;
  width: 700px;
  min-height: 100%;
  input[type="text"] {
    width: 500px;
  }
}
button {
  border: 2px solid dodgerblue;
  padding: 5px 10px;
  width: 500px;
}
</style>
