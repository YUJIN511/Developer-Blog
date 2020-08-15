<template>
  <div>
    <LimitedAccess v-if="!getIsLogin()" />
    <div class="container-base" v-if="getIsLogin()">
      <div class="container-title">
        <span class="title">내가 팔로우 하는 사람들</span>
        <router-link tag="a" to="/main/followadditional">더보기</router-link>
      </div>
      <FlexFollow :isWarp="false" :datas="data" />
      <span class="title">팔로우 한 사람들의 최신 게시물</span>
      <FlexArticles :datas="articleData" />
    </div>
  </div>
</template>

<script>
import FlexFollow from "@/components/common/FlexFollow.vue";
import FlexArticles from "@/components/common/FlexArticles.vue";
import LimitedAccess from "@/components/user/LimitedAccess.vue";
import { mapMutations, mapGetters } from "vuex";
import axios from "axios";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  components: {
    FlexFollow,
    FlexArticles,
    LimitedAccess
  },
  data: function() {
    return {
      data: [],
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
    ...mapGetters({
      getIsLogin: "user/getIsLogin",
      getEmail: "user/getEmail"
    }),
    clickLike(e) {
      const likeIcon = e.currentTarget.querySelector("svg");
      if (likeIcon.classList.contains("selected")) {
        likeIcon.classList.remove("selected");
      } else {
        likeIcon.classList.add("selected");
      }
    },
    async fetchFollowerData() {
      await axios
        .get(`${SERVER_URL}/api/follow/list?email=${this.getEmail()}`)
        .then(res => {
          this.data = res.data.slice(0, 4);
          console.log(this.data);
        })
        .catch(err => console.log(err));
    }
  },
  mounted() {
    this.paintBtn(document.querySelector("#btn-follow"));
  },
  created() {
    this.fetchFollowerData();
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
