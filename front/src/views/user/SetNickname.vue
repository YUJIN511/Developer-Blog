<template>
  <div id="outerdiv">
    <div class="container-nickname">
      <div></div>
      <div>
        <input
          class="input-nickname"
          v-model="nickname"
          placeholder="닉네임 입력 (영문)"
        />
      </div>
      <div class="more-arrow" @click="setNickname()"></div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import axios from "axios";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  data() {
    return {
      email: this.$route.params.email,
      token: this.$route.params.token,
      nickname: "",
    };
  },
  methods: {
    ...mapActions({
      ReceiveToken: "user/receiveToken",
    }),
    setNickname() {
      axios
        .get(`${SERVER_URL}/api/auth/nicknameUpdate`, {
          params: {
            email: this.email,
            nickname: this.nickname,
          },
        })
        .then(() => {
          alert("회원가입이 완료되었습니다.");
          this.$router.push({ name: "Main" });
        });
    },
  },
  created() {
    this.ReceiveToken({ token: this.token, email: this.email });
  },
};
</script>

<style scoped lang="scss">
#outerdiv {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
}

.container-nickname {
  margin: 0;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 80px;
}

// 화살표

$background: whitesmoke;
.more-arrow {
  transform: rotate(270deg);
  background-color: transparent;
  border: transparent;
  border-radius: 50%;
  width: 80px;
  height: 80px;
  position: relative;

  &:after,
  &:before {
    content: "";
    width: 0;
    height: 0;
    border-style: solid;
    border-color: transparent;
    border-left-width: 40px;
    border-right-width: 40px;
    border-top-width: 30px;
    position: absolute;
    left: 10px;
  }
  &:before {
    border-top-color: darken($background, 25%);
    top: 30px;
  }
  &:after {
    border-top-color: white;
    top: 26px;
  }
  &:hover {
    // background: darken($background, 5%);
    // border-color: darken($background, 15%);
    &:before {
      border-top-color: darken($background, 50%);
    }
    // &:after {
    //   border-top-color: darken($background, 5%);
    // }
  }
}

// 입력창

.input-nickname {
  background-image: linear-gradient(lightgrey, lightgrey);
  background-position: bottom 5px center;
  background-size: calc(100% - 10px) 1px;
  background-repeat: no-repeat;
  width: 500px;
  border: 0;
  padding: 10px;
  padding-bottom: 15px;
  justify-self: center;
  font-size: 25px;
}

input:focus {
  outline: none;
}
</style>
