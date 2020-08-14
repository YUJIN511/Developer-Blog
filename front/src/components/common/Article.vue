<template>
  <div class="article">
    <button @click="readArticle" class="btn-read">
      <div ref="headerArticle" class="header-article">
        <img :src="data.picture" alt v-if="data.picture !== ''" />
        <span :title="data.title" v-if="data.picture === ''">{{ data.title }}</span>
      </div>
      <div class="body-article">
        <h5 :title="data.title">{{ data.title }}</h5>
        <div class="description">{{ data.summary }}</div>
      </div>
    </button>
    <div class="footer-article">
      <img class="img-profile" :src="data.profile" v-if="data.profile" />
      <svg
        v-else
        xmlns="http://www.w3.org/2000/svg"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        viewBox="0 0 26 26"
      >
        <path
          d="M13,1C6.4,1,1,6.4,1,13s5.4,12,12,12s12-5.4,12-12S19.6,1,13,1z M13,4.6c2,0,3.6,1.6,3.6,3.6S15,11.8,13,11.8
	s-3.6-1.6-3.6-3.6S11,4.6,13,4.6z M13,21.6c-3,0-5.7-1.5-7.2-3.9c0-2.4,4.8-3.7,7.2-3.7c2.4,0,7.2,1.3,7.2,3.7
	C18.7,20.1,16,21.6,13,21.6z"
        />
      </svg>
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
      <div class="nickname" @click="moveToBlog()">{{ data.nickname }}</div>
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
    if (this.data.picture === "") {
      const thumbnail = this.$refs.headerArticle;
      const R = Math.random() * 155 + 100;
      const G = Math.random() * 155 + 100;
      const B = Math.random() * 155 + 100;
      thumbnail.setAttribute(
        "style",
        `background-color: rgb(${R}, ${G}, ${B});`
      );
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
      this.$router.push({
        name: "ArticleView",
        query: { id: this.data.id }
      });
    },
    moveToBlog() {
      this.$router.push({ name: "Blog", params: { email: this.data.writer } });
      window.scroll(0, 0);
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/Article.scss";
</style>
