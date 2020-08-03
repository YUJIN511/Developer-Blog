<template>
  <div class="container-repassword hide">
    <div class="background" @click="closeModal"></div>
    <div class="modal-repassword">
      <button class="btn-close" @click="closeModal">✖</button>
      <h1>비밀번호 재설정</h1>

      <form class="modal-body">
        <span
          >이메일을 입력해 주세요. 비밀번호 재설정 링크를
          보내드리겠습니다.</span
        >
        <input required type="email" placeholder="이메일" v-model="email" />
        <button class="btn btn-repassword" type="button" @click="sendMail">
          비밀번호 재설정 메일 보내기
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      email: "",
    };
  },
  methods: {
    closeModal() {
      document.querySelector(".container-repassword").classList.add("hide");
    },
    sendMail() {
      axios
        .get(
          `http://i3a604.p.ssafy.io:8081/api/auth/passwordEmail?email=${this.email}`
        )
        .then((res) => {
          console.log(res);
          alert("메일 발송 완료");
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>

<style lang="scss" scoped>
.container-repassword {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
}
.background {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
}
.modal-repassword {
  position: fixed;
  display: flex;
  flex-direction: column;
  width: 500px;
  height: 280px;
  background-color: rgb(240, 240, 240);
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  border-radius: 10px;
  padding: 10px;
}

h1 {
  margin-top: 10px;
  margin-bottom: 15px;
}

input {
  border: 1px solid gainsboro;
  border-radius: 0.25rem;
  padding: 7px 10px;
  margin-top: 40px;
  margin-bottom: 10px;
  width: 100%;
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
.btn-close {
  position: absolute;
  right: 20px;
  top: 10px;
  opacity: 0.8;
}
.btn-repassword {
  background-color: cadetblue;
}
.btn-repassword:hover {
  background-color: rgb(69, 181, 187);
}
.hide {
  display: none;
}
</style>
