<template>
  <div class="container-account">
    <header>
      <div class="profile-image">
        <button class="banner-image-edit">수정하기</button>
      </div>
    </header>
    <main>
      <div class="p-nickname" @click="enableNickname()">
        <span>닉네임</span>
        <p class="nickname">{{ getUserInfo().nickname }}</p>
      </div>
      <div class="edit-nickname hide">
        <div class="background" @click="disableNickname()"></div>
        <span>닉네임</span>
        <div class="container-inputNickname">
          <input class="input-nickname" v-model="nickname" placeholder="영문" />
          <button class="btn btn-edit" @click="updateNickname">수정하기</button>
        </div>
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
          <button class="btn-circle btn-google"></button>
          <button class="btn-circle btn-facebook"></button>
          <button class="btn-circle btn-github"></button>
        </div>
      </div>
      <div class="level-icon">
        <span class="span-password" @click="enablePassword()"
          >비밀번호 바꾸기</span
        >
        <div class="edit-password hide">
          <div class="background" @click="disablePassword()"></div>
          <div class="container-inputPassword">
            <input
              class="input-password"
              v-model="currentPassword"
              type="password"
              placeholder="현재 비밀번호"
            />
            <input
              class="input-password"
              v-model="newPassword"
              type="password"
              placeholder="새 비밀번호"
            />
            <input
              class="input-password"
              v-model="newPasswordConfirm"
              type="password"
              placeholder="새 비밀번호 확인"
            />
          </div>
          <button class="btn btn-pw" @click="updatePassword">변경하기</button>
        </div>
      </div>
      <div class="container-unregister">
        <span class="span-unregister" @click="openUnregisterModal()"
          >회원 탈퇴</span
        >
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
  data() {
    return {
      email: this.getUserInfo().email,
      nickname: this.getUserInfo().nickname,
      password: ""
    };
  },
  methods: {
    ...mapActions({
      fetchUserInfo: "user/fetchUserInfo",
      UpdateNickname: "user/updateNickname",
      Unregister: "user/unregister"
    }),
    ...mapGetters({
      getUserInfo: "user/getUserInfo",
      getEmail: "user/getEmail",
      getIsLogin: "user/getIsLogin"
    }),
    enableNickname() {
      document.querySelector(".p-nickname").classList.add("hide");
      document.querySelector(".edit-nickname").classList.remove("hide");
      this.nickname = this.getUserInfo().nickname;
    },
    disableNickname() {
      document.querySelector(".p-nickname").classList.remove("hide");
      document.querySelector(".edit-nickname").classList.add("hide");
    },
    updateNickname() {
      this.UpdateNickname({ email: this.email, nickname: this.nickname })
        .then(() => {})
        .catch(err => console.log(err));
      this.$router.go();
    },
    enablePassword() {
      document.querySelector(".edit-password").classList.remove("hide");
    },
    disablePassword() {
      console.log("background");
      document.querySelector(".edit-password").classList.add("hide");
    },
    openUnregisterModal() {
      document.querySelector(".container-unregister").classList.remove("hide");
    }
  },
  mounted() {
    this.fetchUserInfo(this.getEmail()).then(res => console.log(res));
  },
  updated() {}
};
</script>

<style lang="scss" scoped>
@import "@/assets/user/setting/Account.scss";
</style>
