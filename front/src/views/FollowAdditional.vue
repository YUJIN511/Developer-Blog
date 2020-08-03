<template>
  <div class="container-base">
    <span class="title">내가 팔로우 하는 사람들</span>
    <FlexFollow :datas="data" />
  </div>
</template>

<script>
import FlexFollow from "@/components/common/FlexFollow.vue";

export default {
  components: {
    FlexFollow
  },
  data: function() {
    return {
      data: [
        {
          profileUrl:
            "https://images.unsplash.com/photo-1577703451648-77e854069658?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
          userName: "사용자 이름1",
          blogName: "블로그 이름1"
        },
        {
          profileUrl:
            "https://images.unsplash.com/photo-1444703686981-a3abbc4d4fe3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
          userName: "사용자 이름2",
          blogName: "블로그 이름12"
        }
      ]
    };
  },
  created() {},
  methods: {
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
</style>
