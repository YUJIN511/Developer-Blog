<template>
  <div class="container-base">
    <h3>로그인하세요</h3>
    <p>설정을 보시려면 로그인하세요</p>
    <button class="btn-login" @click="openLogin">
      로그인
    </button>
  </div>
</template>

<script>
export default {
  name: "LimitedAccess",
  methods: {
    openLogin() {
      document.querySelector(".container-login").classList.remove("hide");
    },
  },
};
</script>

<style scoped lang="scss">
.container-base {
  background-color: rgb(241, 244, 247);
  min-width: 100vw;
  min-height: 100vh;
  text-align: left;
  padding: 20px;
  padding-top: 86px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.btn-login {
  padding: 4px 16px;
  border: 1px solid dodgerblue;
  border-radius: 5%;
  color: dodgerblue;
  font-weight: 600;
  margin: 10px 10px;
}
</style>
