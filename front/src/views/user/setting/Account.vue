<template>
  <div class="container-account">
    <header>
      <div class="profile-image">
        <button class="banner-image-edit">수정하기</button>
      </div>
    </header>
    <main>
      <div class="nickname">
        <span>닉네임</span>
        <p class="nickname">{{ getUserInfo().nickname }}</p>
      </div>
      <div class="level-icon">
        <span>레벨티콘 설정</span>
        <button class="btn-circle btn-level-icon">
          <img
            class="level-icon"
            width="16px"
            height="16px"
            viewBox="0 0 18 18"
            src="@/assets/tree.svg"
            alt="level icon"
          />
        </button>
      </div>
      <div class="level-icon">
        <span>연동된 SNS계정</span>
        <div class="container-btn-social">
          <button class="btn-circle btn-google"></button
          ><button class="btn-circle btn-facebook"></button
          ><button class="btn-circle btn-github"></button>
        </div>
      </div>
      <div class="level-icon">
        <span>비밀번호 바꾸기</span>
      </div>
    </main>
    <!-- 블러효과 용 -->
    <svg version="1.1" xmlns="http://www.w3.org/2000/svg">
      <filter id="blur">
        <feGaussianBlur stdDeviation="20" />
      </filter>
    </svg>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  methods: {
    ...mapActions({
      fetchUserInfo: "user/fetchUserInfo"
    }),
    ...mapGetters({
      getUserInfo: "user/getUserInfo",
      getEmail: "user/getEmail",
      getIsLogin: "user/getIsLogin"
    }),
    guestBlur() {
      if (!this.getIsLogin()) {
        document.querySelector(".container-account").classList.add("blurred");
      }
    }
  },
  mounted() {
    this.fetchUserInfo(this.getEmail()).then(res => console.log(res));
    this.guestBlur();
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/user/setting/Account.scss";
</style>
