<template>
  <div class="container-blog">
    <header></header>
    <main>
      <TagList :email="userEmail" />
      <div class="content">
        <div class="content-header">
          <div class="profile-image"></div>
          <div class="profile">
            <div class="name">
              <span class="level">LV.1</span>
              <span>{{ getUserInfo().nickname }}</span>
            </div>
            <p class="blog-name">{{ getUserInfo().nickname }}님의 블로그</p>
            <p class="blog-ex">극한의 코딩충</p>
            <p class="blog-follower">팔로워 {{ followersCnt }}명</p>
          </div>
        </div>
        <div class="container-tabs">
          <button class="btn btn-article" @click="clickArticle">내 게시물</button>
          <button class="btn btn-Info" @click="clickInfo">정보</button>
        </div>
        <FlexArticles :datas="articleData" v-if="showArticle" />
        <!-- <Info /> -->
        <div class="content-body"></div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

import TagList from "@/components/blog/TagList.vue";
import FlexArticles from "@/components/common/FlexArticles.vue";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  components: {
    TagList,
    FlexArticles
  },
  data() {
    return {
      userEmail: this.$route.params.email,
      articleData: [],
      followersCnt: null,
      showArticle: true,
      showInfo: false
    };
  },
  methods: {
    ...mapGetters({
      getEmail: "user/getEmail",
      getUserInfo: "user/getUserInfo"
    }),
    clickArticle(event) {
      this.showArticle = true;
      this.showInfo = false;
      event.target.style.borderBottom = "3px solid #727272";
      document.querySelector(".btn-Info").style.borderBottom = "none";
    },
    clickInfo(event) {
      this.showInfo = true;
      this.showArticle = false;
      event.target.style.borderBottom = "3px solid #727272";
      document.querySelector(".btn-article").style.borderBottom = "none";
      this.document;
    },
    fetchArticles() {
      axios
        .get(`${SERVER_URL}/api/post/writer/${this.userEmail}`)
        .then(res => {
          this.articleData = res.data;
        })
        .catch(err => console.log(err));
    },
    fetchFollowersCnt() {
      axios
        .get(`${SERVER_URL}/api/follow/cnt/${this.userEmail}`)
        .then(res => {
          this.followersCnt = res.data;
        })
        .catch(err => console.log(err));
    }
  },
  created() {
    this.fetchArticles();
    this.fetchFollowersCnt();
  },
  mounted() {
    if (this.userEmail === this.getEmail()) {
      this.showFollowBtn = false;
    } else {
      this.showFollowBtn = true;
    }

    var profileImages = document.querySelectorAll(".profile-image");
    profileImages.forEach(profileImage => {
      profileImage.style.backgroundImage = `url('${
        this.getUserInfo().profile
      }')`;
    });
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/Base.scss";

.container-blog {
  position: relative;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-content: stretch;
  header {
    height: 320px;
    background-image: url(https://images.unsplash.com/photo-1589482736976-2cfd4400ae4f?ixlib=rb-1.2.1&auto=format&fit=crop&w=2614&q=80);
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }
}

main {
  padding: 30px 70px;
  // padding-right: 100px;
  display: flex;
  .content {
    // position: relative;
    margin: 40px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    .content-header {
      display: flex;
      flex-direction: row;
      margin: 24px;
      .profile {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: space-between;
        margin: 0px 24px;
        .name {
          margin-bottom: 5px;
          span {
            margin-right: 12px;
            font-weight: 1000;
          }
          .level {
            color: gold;
          }
        }
        p {
          margin-bottom: 5px;
        }
        .blog-name {
          font-size: 1.5rem;
          font-weight: 800;
        }
        .blog-follower {
          font-weight: 600;
        }
      }
    }
    .container-tabs {
      margin: 12px;
      .btn {
        border-radius: 0px;
        margin-right: 50px;
        &:focus {
          box-shadow: none;
        }
      }
    }
  }
}

.profile-image {
  position: relative;
  background-image: url(https://images.unsplash.com/photo-1517832207067-4db24a2ae47c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60);
  background-position: center;
  background-size: 150%;
  width: 150px;
  height: 150px;
  border-radius: 50%;
}
</style>
