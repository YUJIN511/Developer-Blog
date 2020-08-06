<template>
  <div class="container-unregister hide">
    <div class="background-unregister" @click="closeModal"></div>
    <div class="modal-unregister">
      <div class="modal-head">
        <svg class="logo" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 154.66 51.58">
          <polygon
            points="81.22 48.29 66.59 48.29 66.59 3.29 70.66 3.29 70.66 44.76 81.22 44.76 81.22 48.29 81.22 48.29 81.22 48.29"
          />
          <path
            d="M127.49,49.31a3,3,0,0,1-.92,2.29,3.26,3.26,0,0,1-2.35.9H111.4a3.13,3.13,0,0,1-2.34-.9,3.07,3.07,0,0,1-.91-2.29V10.69a3,3,0,0,1,.91-2.29,3.13,3.13,0,0,1,2.34-.9h12.81a3.17,3.17,0,0,1,2.35.9,3,3,0,0,1,.92,2.29V49.31ZM123.43,49V11H112.22V49h11.21Z"
            transform="translate(-21.37 -4.21)"
          />
          <path
            d="M153.33,49.31a3,3,0,0,1-.91,2.29,3.21,3.21,0,0,1-2.33.9h-12a3.1,3.1,0,0,1-3.25-3.19V10.69a3,3,0,0,1,.91-2.29,3.14,3.14,0,0,1,2.34-.9h12a3.13,3.13,0,0,1,2.34.9,3.07,3.07,0,0,1,.91,2.29V21.16h-4.1V11H138.87V49h10.35V33.41h-4.89V30h9V49.31Z"
            transform="translate(-21.37 -4.21)"
          />
          <polygon
            points="154.66 48.29 150.21 48.29 150.21 43.23 154.66 43.23 154.66 48.29 154.66 48.29 154.66 48.29"
          />
          <path
            class="cls-1"
            d="M35.23,40.66C34,39.4,35.1,36,36,34.74a36.18,36.18,0,0,1,4.56-5.42A85.34,85.34,0,0,0,53.1,14a41.26,41.26,0,0,0,4.39-9.13c.07-.22.09-.48.37-.61C55.3,16.14,47,29.26,35.23,40.66Z"
            transform="translate(-21.37 -4.21)"
          />
          <path
            class="cls-1"
            d="M27.18,52.14,70.43,52c1.26,0,1.26,1.26,0,1.26s-43.91-.57-43.91-.57Z"
            transform="translate(-21.37 -4.21)"
          />
          <path
            class="cls-1"
            d="M60.16,16.3,57.86,18l2.21-1.11a23.4,23.4,0,0,1-1,3.62c-1.39,4.11-2.7,8.8-6.28,13.83-.37.5-.71,1-1,1.43l-5.25,2.34,4.16-.92a12,12,0,0,1-4.16,3.44c-3.25,1.58-4.16.2-7.54,1.25-5.8,1.83-6.16,6.85-15.09,12.57-1.52,1-2.74,1.48-2.51,1.26,8.06-7.5,33.94-31.43,36.46-50.29a18,18,0,0,1,2.51,8.8C60.3,15,60.26,15.59,60.16,16.3Z"
            transform="translate(-21.37 -4.21)"
          />
        </svg>
      </div>
      <div class="modal-body">
        <hr />
        <h5>회원 탈퇴 하시겠습니까?</h5>
        <p>회원 탈퇴를 하시면 모든 회원 정보와 게시물들은 삭제되며, 복구 할 수 없습니다.</p>
      </div>
      <div class="container-button">
        <button class="btn btn-primary" @click="unregister">탈퇴</button>
        <button class="btn btn-cancel" @click="closeModal">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UnregisterModal",
  methods: {
    closeModal() {
      document.querySelector(".container-unregister").classList.add("hide");
    },
    unregister() {
      this.Unregister(this.email).then(() => {
        alert("회원 탈퇴가 완료되었습니다.");
        this.$router.push({ name: "Main" });
      });
    }
  }
};
</script>

<style scoped lang="scss">
.logo {
  width: 154px;
  height: 51px;
}

.container-unregister {
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

.background-unregister {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
}

// modal

.modal-unregister {
  position: relative;
  width: 380px;
  height: 350px;
  background-color: rgb(240, 240, 240);
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  border-radius: 3%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.modal-head {
  position: absolute;
  top: 0;
  width: 100%;
  padding: 16px;
}

.modal-body {
  padding-top: 0;
  position: absolute;
}

.container-button {
  position: absolute;
  bottom: 0;
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 16px;
}

h5 {
  margin: 30px 0px;
}

.hide {
  display: none;
}

.btn {
  width: 100%;
}

.btn-cancel {
  color: #007bff;
}
</style>
