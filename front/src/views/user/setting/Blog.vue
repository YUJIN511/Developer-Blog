<template>
  <div class="container-blog">
    <header>
      <button class="banner-image-edit">수정하기</button>
    </header>
    <main>
      <span class="rem2">블로그 이름</span>
      <textarea
        class="blog-title"
        v-model="title"
        placeholder="블로그 이름을 정해보세요."
      ></textarea>
      <span class="rem2">블로그 소개</span>
      <textarea
        class="blog-description"
        placeholder="블로그 소개를 해보세요."
        v-model="description"
      >
      </textarea>
      <div class="buttons">
        <button class="btn-submit" @click="updateBlogInfo">
          수정하기
        </button>
      </div>
    </main>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";

export default {
  data: function() {
    return {
      title: "",
      description: ""
    };
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    async updateBlogInfo() {
      console.log(this.description);
      console.log(this.getUserInfo().email);
      console.log(this.title);
      try {
        const res = await axios.post(`${this.$apiServer}/blog`, {
          description: this.description,
          email: this.getUserInfo().email,
          name: this.title
        });
        console.log(res);
      } catch (error) {
        console.log(error);
      }
    }
  },
  async created() {
    try {
      const res = await axios.get(
        `${this.$apiServer}/blog?email=${this.getUserInfo().email}`
      );
      console.dir(res);
      this.title = res.data.name;
      this.description = res.data.description;
    } catch (error) {
      console.log(error);
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/user/setting/Blog.scss";
</style>
