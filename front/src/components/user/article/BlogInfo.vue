<template>
  <div class="container-blog-info">
    <button
      class="btn-moveto-info"
      @click="$router.push(`/${articleData.writer}`)"
    >
      <img :src="articleData.blogPicture" alt />
      <div class="main-info">
        <div class="user-info">
          <LevelIcon :score="articleData.score" />
          <span class="user-nickname">{{ articleData.nickname }}</span>
        </div>
        <div class="blog-title">{{ articleData.blogName }}</div>
        <div class="blog-description">{{ articleData.blogDescription }}</div>
        <div class="follower-number">
          팔로우 {{ articleData.followerNum }}명
        </div>
      </div>
    </button>
    <button class="btn-follow" v-if="!isFollowed" @click="addFollow()">
      팔로우
    </button>
    <button class="btn-follow" v-else @click="cancelFollow()">
      팔로우 취소
    </button>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
import LevelIcon from "@/components/user/LevelIcon.vue";

export default {
  props: {
    articleData: {
      type: Object
    }
  },
  components: {
    LevelIcon
  },
  data: function() {
    return {
      isFollowed: false
    };
  },
  created() {
    this.isFollow();
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    addFollow() {
      axios
        .post(
          `${this.$apiServer}/follow/add?follower=${
            this.getUserInfo().email
          }&followed=${this.articleData.writer}`
        )
        .then(() => {
          this.isFollowed = true;
        })
        .catch(error => {
          console.log(error);
        });
    },
    cancelFollow() {
      axios
        .delete(
          `${this.$apiServer}/follow/del?follower=${
            this.getUserInfo().email
          }&followed=${this.articleData.writer}`
        )
        .then(() => {
          this.isFollowed = false;
        })
        .catch(error => {
          console.log(error);
        });
    },
    isFollow() {
      // articleData를 늦게 가져와서 기다려야 함
      const intervalID = window.setInterval(async () => {
        if (this.articleData !== undefined) {
          clearInterval(intervalID);
          try {
            const res = await axios.get(
              `${this.$apiServer}/follow/isfollow?follower=${
                this.getUserInfo().email
              }&followed=${this.articleData.writer}`
            );
            this.isFollowed = res.data;
          } catch (error) {
            console.log(error);
            return false;
          }
        }
      }, 300);
    }
  }
};
</script>

<style lang="scss" scoped>
button:hover {
  opacity: 0.7;
}

.container-blog-info {
  display: flex;
  align-items: center;
  width: 100%;
  height: 170px;
  margin-top: 20px;
  padding: 20px 0px;
  .btn-moveto-info {
    display: flex;
    align-items: stretch;
    font-size: 1em;
    width: 100%;
    img {
      width: 130px;
      height: 130px;
      border-radius: 50%;
      margin-right: 30px;
    }
    .main-info {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      align-items: flex-start;
      .user-info {
        display: flex;
        .level-icon {
          margin-right: 10px;
        }
      }

      .user-nickname {
        font-size: 1rem;
      }
      .blog-title {
        font-size: 1.6rem;
        font-weight: 600;
      }
      .blog-description {
        font-size: 1rem;
        overflow: hidden;
      }
      .follower-number {
        font-size: 1rem;
        font-weight: 600;
      }
    }
  }
  .btn-follow {
    flex-shrink: 0;
    margin-left: auto;
    font-size: 1.2em;
    color: dodgerblue;
    font-weight: 900;
  }
}
</style>
