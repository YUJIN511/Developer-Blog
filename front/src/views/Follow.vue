<template>
  <div class="container-base">
    <div class="container-title">
      <span class="title">내가 팔로우 하는 사람들</span>
      <router-link tag="a" to="/main/followadditional">더보기</router-link>
    </div>
    <FlexFollow :isWarp="false" :datas="data" />
    <span class="title">팔로우 한 사람들의 최신 게시물</span>
    <FlexArticles :datas="articleData" />
  </div>
</template>

<script>
import FlexFollow from "@/components/common/FlexFollow.vue";
import FlexArticles from "@/components/common/FlexArticles.vue";
import { mapMutations } from "vuex";

export default {
  components: {
    FlexFollow,
    FlexArticles
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
        },
        {
          profileUrl:
            "https://images.unsplash.com/photo-1444703686981-a3abbc4d4fe3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
          userName: "사용자 이름2",
          blogName: "블로그 이름12"
        },
        {
          profileUrl:
            "https://images.unsplash.com/photo-1444703686981-a3abbc4d4fe3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
          userName: "사용자 이름2",
          blogName: "블로그 이름12"
        }
      ],
      articleData: [
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
      ]
    };
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
  },
  mounted() {
    this.paintBtn(document.querySelector("#btn-follow"));
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/_variables.scss";
@import "@/assets/common/Base.scss";

body {
  overflow-x: hidden;
}
.container-title {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
}
</style>
