<template>
  <div class="container-emailsent hide">
    <div class="background-emailsent" @click="closeModal"></div>
    <div class="modal-emailsent">
      <h1>회원가입 완료</h1>
      <div class="modal-body">
        <p>회원가입이 완료되었습니다.</p>
        <p>계정 활성화를 위한 이메일이 가입하신 이메일로 발송되었습니다.</p>
        <p>계정을 활성화 해주세요.</p>
      </div>
      <!-- <button class="btn-close" @click="closeModal">✖</button> -->
    </div>
  </div>
</template>

<script>
export default {
  name: "EmailModal",
  methods: {
    closeModal() {
      document.querySelector(".container-emailsent").classList.add("hide");
    }
  }
};
</script>

<style scoped lang="scss">
.container-emailsent {
  position: fixed;
  z-index: 5;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0);
}

.background-emailsent {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
}

// modal

.modal-emailsent {
  position: fixed;
  width: 520px;
  height: 520px;
  background-color: rgb(240, 240, 240);
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  border-radius: 3%;
  display: flex;
  flex-direction: column;
}

.modal-body {
  padding-top: 100px;
}

h1 {
  margin: 30px 0px;
}

.hide {
  display: none;
}
</style>
