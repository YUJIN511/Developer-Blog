<template>
  <div ref="navbarUser" class="container-navbar-userinfo hide">
    <div class="make-absolute">
      <header>
        <div class="profile-image">
          <img
            :src="
              getUserInfo().profile === null
                ? 'https://cdns.iconmonstr.com/wp-content/assets/preview/2012/240/iconmonstr-user-20.png'
                : getUserInfo().profile
            "
            alt=""
          />
        </div>
        <div class="userinfo">
          <span>{{ getUserInfo().nickname }} </span>
          <span>{{ getUserInfo().email }} </span>
        </div>
      </header>
      <main>
        <button @click="$router.push(`/${getUserInfo().email}`)">
          <svg
            width="24"
            height="24"
            xmlns="http://www.w3.org/2000/svg"
            fill-rule="evenodd"
            clip-rule="evenodd"
          >
            <path
              d="M6.623 18.244l-2.285.728c-.63.194-.936-.751-.303-.954l2.284-.727c.635-.198.931.753.304.953zm-.291-1.718l-3.47-1c-.641-.183-.352-1.15.277-.961l3.471 1c.642.185.351 1.147-.278.961zm13.331 2.446l-2.285-.728c-.63-.201-.329-1.15.303-.953l2.284.727c.633.203.326 1.151-.302.954zm1.476-3.446l-3.471 1c-.632.185-.915-.777-.277-.961l3.471-1c.635-.185.913.779.277.961zm-4.639-3.526c-.551 0-1-.448-1-1s.449-1 1-1c.552 0 1 .448 1 1s-.448 1-1 1zm2-1c0-1.105-.896-2-2-2-1.103 0-2 .895-2 2 0 1.104.897 2 2 2 1.104 0 2-.896 2-2zm-11 1c-.551 0-1-.448-1-1s.449-1 1-1c.552 0 1 .448 1 1s-.448 1-1 1zm2-1c0-1.105-.896-2-2-2s-2 .895-2 2c0 1.104.896 2 2 2s2-.896 2-2zm5.956 7.35c-.547 1.215-2.47 1.831-3.456.543-.987 1.289-2.91.671-3.456-.543-.271-.6.64-1.014.912-.41.336.746 2.034 1.301 2.044-.797v-.504c-.615-.218-1.061-.798-1.061-1.313 0-.646.699-.936 1.561-.936.863 0 1.562.29 1.562.936 0 .515-.446 1.095-1.062 1.313v.504c.009 2.12 1.713 1.533 2.044.797.271-.602 1.184-.192.912.41zm-3.456 4.65c-7.093 0-11-3.351-11-9.435 0-3.774 1.563-8.027 4.419-12.072 1.746 1.658 2.505 2.723 3.958 4.91 2.418-.609 3.786-.361 5.251-.004 1.431-2.167 2.219-3.304 3.944-4.914 2.825 4.032 4.428 8.385 4.428 12.08 0 6.084-3.906 9.435-11 9.435zm6.728-23c-2.082 1.814-3.081 3.044-4.546 5.261-1.289-.316-3.281-.274-4.363 0-1.402-2.11-2.405-3.344-4.546-5.261-3.069 4.042-5.273 8.939-5.273 13.565 0 5.759 3.397 10.435 12 10.435 8.604 0 12-4.676 12-10.435 0-4.578-2.207-9.502-5.272-13.565z"
            />
          </svg>
          내 블로그
        </button>
        <button @click="$router.push('/setting/blog')">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            viewBox="0 0 26 26"
          >
            <path
              d="M22.2,14.2c0.1-0.4,0.1-0.8,0.1-1.2c0-0.4,0-0.8-0.1-1.2l2.6-2C25,9.6,25.1,9.2,24.9,9l-2.5-4.3c-0.2-0.3-0.5-0.4-0.8-0.3
	l-3.1,1.2c-0.6-0.5-1.3-0.9-2.1-1.2l-0.5-3.3c-0.1-0.3-0.3-0.5-0.6-0.5h-4.9c-0.3,0-0.6,0.2-0.6,0.5L9.5,4.5C8.7,4.8,8,5.2,7.4,5.7
	L4.3,4.4C4,4.3,3.7,4.4,3.6,4.7L1.1,9C0.9,9.2,1,9.6,1.3,9.8l2.6,2c-0.1,0.4-0.1,0.8-0.1,1.2s0,0.8,0.1,1.2l-2.6,2
	C1,16.4,0.9,16.8,1.1,17l2.5,4.3c0.2,0.3,0.5,0.4,0.8,0.3l3.1-1.2c0.6,0.5,1.3,0.9,2.1,1.2l0.5,3.3c0.1,0.3,0.3,0.5,0.6,0.5h4.9
	c0.3,0,0.6-0.2,0.6-0.5l0.5-3.3c0.8-0.3,1.5-0.7,2.1-1.2l3.1,1.2c0.3,0.1,0.6,0,0.8-0.3l2.5-4.3c0.2-0.3,0.1-0.6-0.2-0.8L22.2,14.2z
	 M13,17.6c-2.5,0-4.6-2.1-4.6-4.6s2.1-4.6,4.6-4.6s4.6,2.1,4.6,4.6S15.5,17.6,13,17.6z"
            /></svg
          >계정 설정
        </button>
        <button @click="logout">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 24 24"
          >
            <path
              d="M16 9v-4l8 7-8 7v-4h-8v-6h8zm-2 10v-.083c-1.178.685-2.542 1.083-4 1.083-4.411 0-8-3.589-8-8s3.589-8 8-8c1.458 0 2.822.398 4 1.083v-2.245c-1.226-.536-2.577-.838-4-.838-5.522 0-10 4.477-10 10s4.478 10 10 10c1.423 0 2.774-.302 4-.838v-2.162z"
            />
          </svg>
          로그아웃
        </button>
      </main>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";

export default {
  data: function() {
    return {
      isShow: false
    };
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    ...mapMutations({
      setLogout: "user/setLogout"
    }),
    logout() {
      this.setLogout();
      location.href = "/";
    },
    toggle() {
      const navbarUser = document.querySelector(".container-navbar-userinfo");
      if (navbarUser.classList.contains("hide")) {
        navbarUser.classList.remove("hide");
      } else {
        navbarUser.classList.add("hide");
      }
    }
  },
  mounted() {
    const navbarUser = document.querySelector(".container-navbar-userinfo");
    window.addEventListener("click", function(e) {
      if (
        !navbarUser.contains(e.target) &&
        !e.target.classList.contains("banner-image-edit")
      ) {
        navbarUser.classList.add("hide");
      }
    });
  }
};
</script>

<style lang="scss" scoped>
.container-navbar-userinfo {
  position: relative;
  width: 0px;
  height: 0px;
  left: -180px;
  top: 5px;
  &.hide {
    display: none;
  }
  .make-absolute {
    display: flex;
    flex-direction: column;
    position: absolute;
    background-color: white;
    border: 1px solid rgb(233, 233, 233);
    width: 240px;
    header {
      display: flex;
      border-bottom: 1px rgb(233, 233, 233) solid;
      .profile-image {
        display: flex;
        justify-content: center;
        align-items: center;
        background-image: none !important;
        img {
          width: 36px;
          height: 36px;
          border-radius: 50%;
          margin: 10px;
        }
      }
      .userinfo {
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        align-items: flex-start;
        span:nth-child(2) {
          color: rgb(179, 179, 179);
          font-size: 0.9em;
        }
      }
    }
    main {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      button {
        display: flex;
        align-items: center;
        width: 100%;
        padding: 12px 15px;
        &:hover {
          background-color: aliceblue;
        }
        svg {
          width: 24px;
          height: 24px;
          margin-right: 15px;
        }
      }
    }
  }
}
</style>
