<template>
  <div class="container-blog">
    <header>
      <button class="banner-image-edit">수정하기</button>
    </header>
    <main>
      <div class="name">
        <span>블로그 이름</span>
        <div class="content">{{ getUserInfo().nickname }} 의 블로그</div>
      </div>
      <div class="description">
        <span>블로그 소개</span>
        <div class="content">
          Lorem ipsum dolor sit, amet consectetur adipisicing elit. Cum
          blanditiis inventore pariatur vel reiciendis voluptatum rem facilis
          nisi repellat, dolorem illo, porro odit officia numquam? Iusto fugiat
          ex vero nostrum explicabo id laboriosam, cumque quia aut blanditiis
          culpa ad delectus?
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    })
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/user/setting/Blog.scss";
</style>
