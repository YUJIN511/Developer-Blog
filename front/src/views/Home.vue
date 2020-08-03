<template>
  <div class="container-base">
    <span class="title">팔로우한 사람들의 최신 게시물</span>
    <FlexArticles :datas="articleData" />
    <span class="title">추천 게시물</span>
    <FlexArticles :datas="articleData" />
  </div>
</template>

<script>
import FlexArticles from "@/components/common/FlexArticles.vue";
import axios from "axios";
import { mapMutations } from "vuex";
const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  components: {
    FlexArticles
  },
  created() {
    axios
      .get(`${SERVER_URL}/api/post/all`)
      .then(res => {
        this.setArticleData(res.data);
      })
      .catch(err => {
        console.log(err);
      });
  },
  mounted() {
    this.paintBtn(document.querySelector("#btn-home"));
  },
  methods: {
    ...mapMutations({
      paintBtn: "navbarMini/paintBtn"
    }),
    setArticleData(datas) {
      datas.forEach(data => {
        const input = {
          thumbUrl:
            "https://images.unsplash.com/photo-1519052537078-e6302a4968d4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
          title: data.title,
          desc: data.content,
          profileUrl:
            "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
          iconUrl: "@/assets/tree.svg",
          name: "닉네임1",
          isLiked: true,
          likeCnt: 10
        };
        this.articleData.push(input);
      });

      this.articleData.push({
        thumbUrl:
          "https://images.unsplash.com/photo-1524503033411-c9566986fc8f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
        title: "제목",
        desc: "본문",
        profileUrl:
          "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        iconUrl: "@/assets/tree.svg",
        name: "닉네임1",
        isLiked: false,
        likeCnt: 10
      });

      this.articleData.push({
        thumbUrl:
          "https://images.unsplash.com/photo-1505377059067-e285a7bac49b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
        title: "제목",
        desc: "본문",
        profileUrl:
          "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        iconUrl: "@/assets/tree.svg",
        name: "닉네임1",
        isLiked: false,
        likeCnt: 10
      });

      this.articleData.push({
        thumbUrl:
          "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
        title: "제목",
        desc: "본문",
        profileUrl:
          "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        iconUrl: "@/assets/tree.svg",
        name: "닉네임1",
        isLiked: false,
        likeCnt: 10
      });

      this.articleData.push({
        thumbUrl:
          "https://images.unsplash.com/photo-1472491235688-bdc81a63246e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
        title: "제목",
        desc: "본문",
        profileUrl:
          "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        iconUrl: "@/assets/tree.svg",
        name: "닉네임1",
        isLiked: false,
        likeCnt: 10
      });

      this.articleData.push({
        thumbUrl:
          "https://images.unsplash.com/photo-1562165742-5fb25d795480?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
        title: "제목",
        desc: "본문",
        profileUrl:
          "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        iconUrl: "@/assets/tree.svg",
        name: "닉네임1",
        isLiked: false,
        likeCnt: 10
      });

      this.articleData.push({
        thumbUrl:
          "https://images.unsplash.com/photo-1516590914727-51e55df118d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
        title: "제목",
        desc: "본문",
        profileUrl:
          "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        iconUrl: "@/assets/tree.svg",
        name: "닉네임1",
        isLiked: false,
        likeCnt: 10
      });
    }
  },
  data: function() {
    return {
      articleData: []
    };
  }
};
</script>

<style scoped lang="scss">
@import "@/assets/_variables.scss";
@import "@/assets/common/Base.scss";
</style>
