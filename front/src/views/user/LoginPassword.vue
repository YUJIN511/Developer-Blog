<template>
  <div id="outerdiv">
    <div class="container">
      <div class="row">
        <div class="col-10">
          <input
            id="password"
            v-model="userInfo.password"
            type="password"
            placeholder="비밀번호를 입력하세요"
          />
        </div>
        <div class="col-2">
          <i class="fas fa-angle-right" @click="login"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  name: "LoginPassword",
  data() {
    return {
      userInfo: {
        email: this.$route.params.email,
        password: "",
      },
    };
  },
  methods: {
    ...mapActions({
      Login: "user/login",
    }),
    login() {
      this.Login(this.userInfo)
        .then(() => {
          this.$router.push({ name: "Home" });
        })
        .catch((err) => console.log(err));
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
