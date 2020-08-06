<template>
  <div class="article">
    <button @click="readArticle" class="btn-read">
      <div class="header-article">
        <img :src="data.picture" alt />
      </div>
      <div class="body-article">
        <h5>{{ data.title }}</h5>
        <div class="description">{{ data.summary }}</div>
      </div>
    </button>
    <div class="footer-article">
      <img class="img-profile" :src="data.profileUrl" />
      <div class="level-icon">
        <img
          class="level-icon"
          width="12px"
          height="12px"
          viewBox="0 0 24 24"
          src="@/assets/tree.svg"
          alt="level icon"
        />
      </div>
      <div class="nickname">{{ data.nickname }}</div>
      <div class="like">
        <button class="btn-like" @click="clickLike">
          <svg
            class="icon-like"
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 24 24"
          >
            <path
              d="M12 4.248c-3.148-5.402-12-3.825-12 2.944 0 4.661 5.571 9.427 12 15.808 6.43-6.381 12-11.147 12-15.808 0-6.792-8.875-8.306-12-2.944z"
            />
          </svg>
        </button>
        <span class="like-count">{{ data.good }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    data: {
      type: Object
    }
  },
  mounted() {
    if (this.data.isLiked) {
      document.querySelector(".icon-like").classList.add("selected");
    }
  },
  methods: {
    clickLike(e) {
      const likeIcon = e.currentTarget.querySelector("svg");
      if (likeIcon.classList.contains("selected")) {
        likeIcon.classList.remove("selected");
      } else {
        likeIcon.classList.add("selected");
      }
    },
    readArticle() {
      console.dir(this.data);
      this.$router.push({
        name: "ArticleView",
        query: { articleData: this.data }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/Article.scss";
</style>
