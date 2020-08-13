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
        this.articleData = res.data;
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
    })
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
