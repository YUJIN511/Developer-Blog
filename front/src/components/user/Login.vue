<template>
  <div class="container-login hide">
    <div class="background-login" @click="closeLogin"></div>
    <div class="modal-login">
      <button class="btn-close" @click="closeLogin">✖</button>
      <h1>로그인</h1>
      <form class="container-login-form" @submit.prevent="login">
        <label for="email">이메일</label>
        <input
          required
          name="email"
          type="email"
          placeholder="이메일을 입력해 주세요"
          v-model="userInfo.email"
        />
        <div class="password-label">
          <label for="password">비밀번호</label>
          <div class="repassword">
            <a href="javascript:void(0)" @click="moveToRepassword"
              >비밀번호 재설정</a
            >
          </div>
        </div>
        <input
          required
          name="password"
          type="password"
          minlength="8"
          placeholder="비밀번호를 입력해 주세요"
          v-model="userInfo.password"
        />
        <button class="btn btn-login" type="submit">
          로그인
        </button>
        <div class="join-link">
          <span>아직 계정이 없으신가요? </span
          ><a href="javascript:void(0)" @click="moveToJoin">계정 만들기 </a>
        </div>
        <hr width="100%" />
        <div class="social-login">
          <form>
            <button class="btn btn-github">
              <div>
                <svg
                  class="logo-github"
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"
                  />
                </svg>
                <span>Github으로 계속하기</span>
              </div>
            </button>
            <button class="btn btn-facebook">
              <div>
                <svg
                  class="logo-facebook"
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M22.675 0h-21.35c-.732 0-1.325.593-1.325 1.325v21.351c0 .731.593 1.324 1.325 1.324h11.495v-9.294h-3.128v-3.622h3.128v-2.671c0-3.1 1.893-4.788 4.659-4.788 1.325 0 2.463.099 2.795.143v3.24l-1.918.001c-1.504 0-1.795.715-1.795 1.763v2.313h3.587l-.467 3.622h-3.12v9.293h6.116c.73 0 1.323-.593 1.323-1.325v-21.35c0-.732-.593-1.325-1.325-1.325z"
                  />
                </svg>
                <span>Facebook으로 계속하기</span>
              </div>
            </button>
          </form>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "Login",
  data() {
    return {
      userInfo: {
        email: "",
        password: "",
      },
    };
  },
  methods: {
    ...mapGetters({
      getIsLogin: "user/getIsLogin",
      getEmail: "user/getEmail",
      getProfile: "user/getProfile",
    }),
    ...mapActions({
      Login: "user/login",
      fetchUserInfo: "user/fetchUserInfo",
    }),
    closeLogin() {
      this.email = "";
      this.password = "";
      document.querySelector(".container-login").classList.add("hide");
    },
    moveToJoin() {
      this.closeLogin();
      document.querySelector(".container-join").classList.remove("hide");
    },
    moveToRepassword() {
      this.closeLogin();
      document.querySelector(".container-repassword").classList.remove("hide");
    },
    async login() {
      const result = await this.Login(this.userInfo);

      if (result) {
        // location.href = "/";
        this.fetchUserInfo(this.getEmail());
        this.$router.go();
      } else {
        alert("아이디 혹은 비밀번호가 틀립니다.");
      }
    },
  },
};
</script>

<style scoped lang="scss">
/** base */
.container-login {
  position: fixed;
  z-index: 99999;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0);
}

.background-login {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-login {
  position: fixed;
  width: 350px;
  height: 520px;
  background-color: rgb(240, 240, 240);
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  border-radius: 3%;
}

hr {
  margin: 25px 0px;
}

.hide {
  display: none;
}

/** login form */
h1 {
  margin: 30px 0px;
}
.container-login-form {
  display: flex;
  flex-direction: column;
  align-content: flex-start;
  margin: 0px 20px;
}
label {
  text-align: left;
  padding-left: 5px;
}
input {
  border: 1px solid gainsboro;
  border-radius: 0.25rem;
  padding: 7px 10px;
  margin-bottom: 20px;
}
input:focus {
  outline: none !important;
  border: 1px solid cadetblue;
  box-shadow: 0 0 2px cadetblue;
}
.password-label {
  display: flex;
  align-items: baseline;
  .repassword {
    margin-left: auto;
    font-size: 0.8em;
  }
}
.btn {
  background-color: pink;
  width: 100%;
  border-radius: 0.25em;
  padding: 7px 10px;
  color: white;
  border-radius: 0.25rem;
}
.btn-login {
  background-color: cadetblue;
}
.btn-login:hover {
  background-color: rgb(69, 181, 187);
}
.btn-close {
  position: absolute;
  right: 20px;
  top: 10px;
  opacity: 0.8;
}
.join-link {
  margin-top: 10px;
  text-align: center;
  font-size: 0.9em;
}

/** social login form */
.btn-github {
  background-color: #383538;
  margin-bottom: 10px;
}
.btn-github:hover {
  background-color: #000000;
}
.logo-github {
  float: left;
  fill: white;
}
.btn-facebook {
  background-color: #2b4683;
}
.btn-facebook:hover {
  background-color: #1743ac;
}
.logo-facebook {
  float: left;
  fill: white;
}
</style>
