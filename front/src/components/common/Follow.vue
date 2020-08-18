<template>
  <div class="profile">
    <div class="left">
      <button class="btn-profile">
        <img :src="data.profile" alt />
      </button>
    </div>
    <div class="right">
      <div class="name">{{ data.nickname }}</div>
      <div class="blog-name" v-if="data.blogname">{{ data.blogname }}</div>
      <div class="blog-name blog-name-empty" v-if="!data.blogname">
        이름 없음
      </div>
      <div class="footer">
        <button class="btn-alarm">
          <img src="@/assets/notification_icon.svg" alt="알림 설정" />
        </button>
        <button class="btn-follow" v-if="isFollowing" @click="unfollow">
          팔로우 취소
        </button>
        <button class="btn-follow" v-if="!isFollowing" @click="follow">
          팔로우
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  props: {
    data: {
      type: Object
    }
  },
  data() {
    return {
      isFollowing: true
    };
  },
  methods: {
    ...mapGetters({
      getEmail: "user/getEmail"
    }),
    unfollow() {
      this.isFollowing = !this.isFollowing;
      axios.delete(
        `${SERVER_URL}/api/follow/del?followed=${
          this.data.email
        }&follower=${this.getEmail()}`
      );
    },
    follow() {
      this.isFollowing = !this.isFollowing;
      axios.post(
        `${SERVER_URL}/api/follow/add?followed=${
          this.data.email
        }&follower=${this.getEmail()}`
      );
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/Profile.scss";
</style>
