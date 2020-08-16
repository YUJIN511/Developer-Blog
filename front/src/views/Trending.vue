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
import axios from "axios";

export default {
  components: {
    WordCloud,
    FlexAricles
  },
  data() {
    return {
      words: [],
      fontSizeMapper: word => Math.log2(word.value) * 5,
      articleData: []
    };
  },

  async created() {
    await this.initTagData(15);

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
    },
    async initTagData(limit) {
      try {
        const res = await axios.get(`${this.$apiServer}/main/popularTag`);
        const datas = res.data;
        let value = 100000;

        for (let i = 0; i < limit && datas.length; i++) {
          const word = {
            text: datas[i].tagname,
            value
          };
          value /= 2;
          this.words.push(word);
        }
      } catch (error) {
        console.log(error);
      }
    }
  }
};
// 50000 10000 2000 400 80
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
