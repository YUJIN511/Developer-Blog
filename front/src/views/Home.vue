<template>
  <div class="container-base">
    <span class="title">팔로우한 사람들의 최신 게시물</span>
    <FlexArticles :datas="followerArticleData" />
    <span class="title">추천 게시물</span>
    <FlexArticles :datas="articleData" />
  </div>
</template>

<script>
import FlexArticles from "@/components/common/FlexArticles.vue";
import axios from "axios";
import { mapMutations, mapGetters } from "vuex";
const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  components: {
    FlexArticles
  },
  created() {
    axios
      .get(`${SERVER_URL}/api/post/all?email=${this.getUserInfo().email}`)
      .then(res => {
        this.articleData = res.data;
      })
      .catch(err => {
        console.log(err);
      });
    this.fetchFollowerArticles();
  },
  mounted() {
    this.paintBtn(document.querySelector("#btn-home"));
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    ...mapMutations({
      paintBtn: "navbarMini/paintBtn"
    }),
    async fetchFollowerArticles() {
      await axios
        .get(
          `${SERVER_URL}/api/main/followLatest?email=${
            this.getUserInfo().email
          }`
        )
        .then(res => {
          this.followerArticleData = res.data;
        })
        .catch(err => console.log(err));
    }
  },
  data: function() {
    return {
      articleData: [],
      followerArticleData: []
    };
  }
};
</script>

<style scoped lang="scss">
@import "@/assets/_variables.scss";
@import "@/assets/common/Base.scss";
</style>
