<template>
  <div class="container-join hide">
    <div class="background" @click="closeJoin"></div>
    <div class="modal-join">
      <button class="btn-close" @click="closeJoin">✖</button>

      <h1>회원가입</h1>

      <form class="container-join-form" @submit.prevent="join">
        <label for="email">이메일</label>
        <input
          required
          v-model="email"
          id="email"
          placeholder="이메일을 입력해주세요"
          name="email"
          type="email"
        />

        <label for="password">비밀번호 (영문, 숫자 8~20자) </label>
        <input
          v-model="password"
          required
          minlength="8"
          maxlength="20"
          id="password"
          type="password"
          name="password"
          @keyup="passwordEqualCheck"
          placeholder="비밀번호를 입력해주세요"
          pattern="^((?=\S*?[A-Za-z0-9]).{0,})\S$"
          title="영문 숫자 혼합 8자 이상"
        />

        <label for="password-confirm">비밀번호 확인</label>
        <input
          v-model="passwordConfirm"
          required
          id="password-confirm"
          type="password"
          name="password-confirm"
          @keyup="passwordEqualCheck"
          placeholder="비밀번호를 한번 더 입력해주세요"
        />
        <div class="msg msg-password-confirm">
          비밀번호가 일치하지 않습니다.
        </div>

        <div class="container-term">
          <input v-model="isTerm" type="checkbox" id="term" />
          <span class="term">
            <a href="">약관</a> 및 <a href="">개인정보</a>처리 방침에
            동의합니다.
          </span>
        </div>

        <button class="btn btn-join" type="submit">
          가입하기
        </button>
        <div class="login-link">
          <span>계정이 있으신가요? </span
          ><a href="javascript:void(0)" @click="moveToLogin">로그인 하기 </a>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  mounted() {
    this.dom.passwordConfirmErrMsg = document.querySelector(
      ".msg-password-confirm"
    );
  },
  methods: {
    ...mapActions({
      Join: "user/join"
    }),
    closeJoin() {
      document.querySelector(".container-join").classList.add("hide");
    },
    moveToLogin() {
      this.closeJoin();
      document.querySelector(".container-login").classList.remove("hide");
    },
    passwordEqualCheck() {
      if (!(this.password === this.passwordConfirm)) {
        this.dom.passwordConfirmErrMsg.classList.add("show");
        return false;
      }
      this.dom.passwordConfirmErrMsg.classList.remove("show");
      return true;
    },
    async join() {
      const result = await this.Join({
        email: this.email,
        password: this.password
      });

      if (result) {
        this.closeJoin();
        var modal = document.querySelector(".container-emailsent");
        modal.classList.remove("hide");
      } else {
        alert("이미 사용중인 이메일 입니다.");
      }
    },
    openEmailSent() {
      this.closeJoin();
      document.querySelector(".container-emailsent").classList.remove("hide");
    }
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordConfirm: "",
      isTerm: false,
      dom: {
        passwordConfirmErrMsg: ""
      }
    };
  }
};
</script>

<style scoped>
/** base */
.container-join {
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  left: 0;
  bottom: 0;
  width: 100vw;
  height: 100vh;
  z-index: 99999;
}
.background {
  position: fixed;
  background-color: rgba(0, 0, 0, 0.4);
  min-width: 100vw;
  min-height: 100vh;
}
.modal-join {
  position: fixed;
  width: 350px;
  height: auto;
  background-color: rgb(240, 240, 240);
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  border-radius: 3%;
}
.hide {
  display: none;
}

/** join form */
h1 {
  margin: 30px 0px;
}
.container-join-form {
  display: flex;
  flex-direction: column;
  align-content: flex-start;
  margin: 0px 20px;
}
label {
  margin-top: 15px;
  margin-bottom: 0px;
  text-align: left;
  padding-left: 5px;
}
input {
  border: 1px solid gainsboro;
  border-radius: 0.25rem;
  padding: 7px 10px;
}
input:focus {
  outline: none !important;
  border: 1px solid cadetblue;
  box-shadow: 0 0 2px cadetblue;
}
.btn {
  background-color: pink;
  width: 100%;
  border-radius: 0.25em;
  padding: 7px 10px;
  color: white;
  border-radius: 0.25rem;
}
.btn-join {
  background-color: cadetblue;
}
.btn-join:hover {
  background-color: rgb(69, 181, 187);
}
.container-term {
  margin-top: 40px;
  text-align: left;
  padding-left: 5px;
}
.term {
  font-size: 0.9em;
  padding-left: 5px;
}
.login-link {
  margin-top: 10px;
  margin-bottom: 20px;
  text-align: center;
  font-size: 0.9em;
}
.btn-close {
  position: absolute;
  right: 20px;
  top: 10px;
  opacity: 0.8;
}

/** input check */

.msg {
  display: none;
  text-align: left;
  font-size: 0.9em;
  margin-left: 5px;
  color: crimson;
}
.show {
  display: block;
}
</style>
