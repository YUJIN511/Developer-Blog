<template>
  <div class="container-base">
    <div class="container-hot-tags">
      <span class="title">인기 태그</span>
      <WordCloud :data="words" :fontSizeMapper="fontSizeMapper" />
    </div>
    <div>
      <span class="title">인기 게시물</span>
      <FlexAricles :datas="articleData" />
    </div>
  </div>
</template>

<script>
import WordCloud from "@/components/common/WordCloud.vue";
import FlexAricles from "@/components/common/FlexArticles.vue";
import { mapMutations } from "vuex";

export default {
  components: {
    WordCloud,
    FlexAricles
  },
  data() {
    return {
      words: [
        { text: "Vue", value: 1000 },
        { text: "Javascript", value: 200 },
        { text: "Spring", value: 800 },
        { text: "Django", value: 1000000 },
        { text: "Python", value: 100 },
        { text: "Java", value: 1000 },
        { text: "React", value: 150 },
        { text: "Ruby", value: 10000 },
        { text: "CSS", value: 100 },
        { text: "HTML", value: 2000 },
        { text: "AI", value: 600 },
        { text: "C++", value: 3000 },
        { text: "Web", value: 6000 }
      ],
      fontSizeMapper: word => Math.log2(word.value) * 5,
      articleData: []
    };
  },
  created() {
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
        likeCnt: 10
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
        likeCnt: 9
      }
    ];
  },
  mounted() {
    this.paintBtn(document.querySelector("#btn-trending"));
  },
  methods: {
    ...mapMutations({
      paintBtn: "navbarMini/paintBtn"
    }),
    clickLike(e) {
      const likeIcon = e.currentTarget.querySelector("svg");
      if (likeIcon.classList.contains("selected")) {
        likeIcon.classList.remove("selected");
      } else {
        likeIcon.classList.add("selected");
      }
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/_variables.scss";
@import "@/assets/common/Base.scss";
.container-hot-tags {
  display: flex;
  flex-direction: column;
  align-items: center;
  .title {
    align-self: start;
  }
}
</style>
