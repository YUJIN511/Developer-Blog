<template>
  <div>
    <div class="header-container">
      <div class="header-item header-front">
        <div class="btn-navbar-toggle" v-if="$route.meta.header !== 0">
          <button @click="showNavBar">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              viewBox="0 0 26 26"
            >
              <path
                d="M1,17.8v2.4h24v-2.4H1z M1,11.8v2.4h24v-2.4H1z M1,5.8v2.4h24V5.8H1z"
              />
            </svg>
          </button>
        </div>
        <router-link to="/">
          <svg
            class="logo"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 154.66 51.58"
          >
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
        </router-link>
      </div>

      <div class="header-item header-center" v-if="$route.meta.header !== 0">
        <div class="container-main-search">
          <input type="search" class="input-main-search" v-model="searchWord" />
          <button @click="search" class="btn-main-search desktop">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              viewBox="0 0 26 26"
            >
              <path
                d="M18.1,16H17l-0.3-0.3c1.3-1.6,2.1-3.5,2.1-5.8c0-5-4-9-9-9S1,4.9,1,9.9s4,9,9,9c2.2,0,4.3-0.8,5.8-2.1l0.3,0.3v1.1l6.9,6.9
	L25,23L18.1,16z M10,16c-3.4,0-6.2-2.7-6.2-6.2S6.4,3.5,10,3.5s6.2,2.7,6.2,6.2S13.3,16,10,16z"
              />
            </svg>
          </button>
        </div>
      </div>

      <div class="header-item header-end" v-if="$route.meta.header !== 0">
        <button class="btn-main-search mobile">
          <div class="icon-main-search">
            <img
              class="svg"
              width="20px"
              height="20px"
              viewBox="0 0 24 24"
              src="@/assets/icons/Main_search_icon.svg"
              alt="level icon"
            />
          </div>
        </button>
        <button class="btn-login" @click="openLogin" v-if="!getIsLogin()">
          로그인
        </button>
        <router-link
          tag="button"
          class="btn-login"
          to="/article"
          v-if="getIsLogin()"
          >글쓰기</router-link
        >
        <router-link class="profile-icon" v-if="getIsLogin()" to="/setting">
          <img
            class="svg"
            width="24px"
            height="24px"
            viewBox="0 0 24 24"
            src="@/assets/icons/Main_account_icon.svg"
            alt="level icon"
          />
        </router-link>
      </div>
    </div>
    <Navbar />
    <Login />
    <Join />
    <Repassword />
    <EmailModal />
    <UnregisterModal />
  </div>
</template>

<script>
import Navbar from "@/components/common/Navbar.vue";
import Login from "@/components/user/Login.vue";
import Join from "@/components/user/Join.vue";
import Repassword from "@/components/user/Repassword.vue";
import EmailModal from "@/components/user/EmailModal.vue";
import UnregisterModal from "@/components/user/setting/UnregisterModal.vue";

import { mapActions, mapGetters } from "vuex";
// import NavbarMini from "@/components/common/Navbar-mini.vue";

export default {
  name: "Header",
  components: {
    Navbar,
    Login,
    Join,
    Repassword,
    EmailModal,
    UnregisterModal
  },
  data() {
    return {
      searchWord: ""
    };
  },
  methods: {
    ...mapActions({
      Logout: "user/logout"
    }),
    ...mapGetters({
      getIsLogin: "user/getIsLogin"
    }),
    showNavBar() {
      const navbar = document.querySelector(".navbar");
      const background = document.querySelector(".navbar-background");
      const body = document.querySelector("body");

      body.classList.add("hide-scroll");
      navbar.style.left = "0";
      background.classList.remove("hide");
    },
    openLogin() {
      document.querySelector(".container-login").classList.remove("hide");
    },

    logout() {
      this.Logout();
      // this.$router.go();
      console.log(this.getIsLogin());
    },
    search() {
      this.$router.push({
        name: "Search",
        params: { keyword: this.searchWord }
      });
    }
  },
  computed: {}
};
</script>

<style scoped lang="scss">
@import "@/assets/_variables.scss";

.header-container {
  position: fixed;
  display: flex;
  align-items: center;
  width: 100vw;
  height: 56px;
  background-color: white;
  z-index: 10;
}
.header-front,
.header-end {
  display: flex;
  align-items: center;
  margin: 0px 20px;
}

// front

.btn-navbar-toggle {
  display: none;
}
.logo {
  width: 154px * 0.5;
  height: 51px * 0.5;
}
// center

.header-center {
  display: none;
}

/** end */
.header-end {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 200px;
  margin-left: auto;
}

.btn-login {
  padding: 4px 16px;
  border: 1px solid dodgerblue;
  border-radius: 5%;
  color: dodgerblue;
  font-weight: 600;
  margin: 0 10px;
}

.user-profile {
  border-radius: 50%;
}

.btn-main-search.mobile {
  background: rgba(0, 0, 0, 0);
  width: 30px;
  height: 30px;
  border: none;
  margin-left: auto;
  svg {
    fill: rgb(150, 150, 150);
  }
}

.profile-icon:hover {
  opacity: 0.7;
}

@media (min-width: $threeArticles) {
  .header-container {
    position: fixed;
    display: flex;
    align-items: center;
    justify-content: space-between;
    top: 0;
    left: 0;
    padding: 0 23px;
  }

  /** front */
  .header-front {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 150px;
    margin-right: auto;
    margin-left: 0;
  }
  .btn-navbar-toggle {
    width: 26px;
    height: 26px;
    display: block;
    button {
      width: 26px;
      height: 26px;
    }
    svg {
      width: 26px;
      height: 26px;
      fill: #555555;
    }
  }

  /* center */
  .header-center {
    display: flex;
    margin: 0px 20px;
    flex: 1 1 0;
    max-width: 600px;
  }
  .container-main-search {
    display: flex;
    align-items: baseline;
    width: 100%;
    color: rgb(162, 163, 163);
  }
  .icon-main-search {
    width: 20px;
    height: 25px;
  }
  .btn-main-search.desktop {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 40px;
    height: 40px;
    border: none;
    margin-left: auto;
    svg {
      width: 24px;
      height: 24px;
      fill: #999999;
    }
  }

  .btn-main-search {
    img {
      width: 16px;
      height: 16px;
    }
  }

  .mobile {
    display: none;
  }
  .input-main-search {
    flex: 1 1 0;
    background: white;
    height: 30px;
    border: none;
    border-bottom: 2px solid #bbbbbb;
    &:focus {
      outline: none !important;
      box-shadow: 0 0 4px rgb(137, 197, 199);
    }
  }
}
</style>
