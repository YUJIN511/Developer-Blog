<template>
  <div class="container-base">
    <span class="title">{{ keyword }}에 대한 검색결과</span>
    <FlexArticles :datas="articleData" />
  </div>
</template>

<script>
/*
썸네일 URL
글 제목
내용
프로필사진
레벨아이콘
블로그 or 유저 이름
좋아요 눌렀었는지 여부
좋아요 숫자
*/

import axios from "axios";
import FlexArticles from "@/components/common/FlexArticles.vue";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  components: {
    FlexArticles,
  },
  data: function() {
    return {
      articleData: [],
      keyword: this.$route.params.keyword,
    };
  },
  methods: {
    fetchWordResult() {
      axios
        .get(`${SERVER_URL}/api/post/search/${this.keyword}`)
        .then((res) => {
          console.log(res);
          this.articleData = res.data;
          console.log(this.articleData);
        })
        .catch((err) => console.log(err));
    },
    fetchHashResult() {
      axios
        .get(`${SERVER_URL}/api/post/hashSearch/${this.keyword}`)
        .then((res) => {
          console.log(res);
          this.articleData = res.data;
        })
        .catch((err) => console.log(err));
    },
  },
  created() {
    console.log(this.keyword);
    // if (this.keyword.includes("#")) {
    //   this.fetchHashResult();
    // } else {
    //   this.fetchWordResult();
    // }
    this.articleData = [
      {
        thumbUrl:
          "https://images.unsplash.com/photo-1519052537078-e6302a4968d4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        title: "글 제목1",
        desc:
          "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Ea voluptates eos laboriosam quo eaque earum laudantium modi natus, mollitia animi tempore, sint iste velit voluptatum. Est possimus rem, nostrum numquam totam natus eaque, enim sit nisi earum accusantium aliquid tenetur?",
        profileUrl:
          "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        iconUrl: "@/assets/tree.svg",
        name: "닉네임1",
        isLiked: true,
        likeCnt: 10,
      },
      {
        thumbUrl:
          "https://images.unsplash.com/photo-1519052537078-e6302a4968d4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        title: "글 제목2",
        desc:
          "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Ea voluptates eos laboriosam quo eaque earum laudantium modi natus, mollitia animi tempore, sint iste velit voluptatum. Est possimus rem, nostrum numquam totam natus eaque, enim sit nisi earum accusantium aliquid tenetur?",
        profileUrl:
          "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=60",
        iconUrl: "@/assets/tree.svg",
        name: "닉네임2",
        isLiked: false,
        likeCnt: 9,
      },
    ];
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/_variables.scss";
@import "@/assets/common/Base.scss";
</style>
