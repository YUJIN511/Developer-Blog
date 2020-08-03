<template>
  <div id="outerdiv">
    <div class="container">
      <div class="row">
        <div class="col-10">
          <input id="nickname" v-model="nickname" placeholder="닉네임 입력" />
        </div>
        <div class="col-2">
          <i class="fas fa-angle-right" @click="setNickname"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  data() {
    return {
      email: this.$route.params.email,
      nickname: "",
    };
  },
  methods: {
    setNickname() {
      axios
        .get(`${SERVER_URL}/api/auth/nicknameUpdate`, {
          params: {
            email: this.email,
            nickname: this.nickname,
          },
        })
        .then((res) => {
          console.log(res);
          alert("환영합니다.");
          this.$router.push({
            name: "LoginPassword",
            params: { email: this.email },
          });
        });
    },
  },
};
</script>

<style scoped>
#outerdiv {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
.container {
  margin: 0;
  width: 100%;
}
i {
  font-size: 2rem;
}
input {
  background-image: linear-gradient(lightgrey, lightgrey);
  background-position: bottom 5px center;
  background-size: calc(100% - 10px) 1px;
  background-repeat: no-repeat;
  width: 350px;
  border: 0;
  padding: 10px;
  padding-bottom: 15px;
}
input:focus {
  outline: none;
}
</style>
