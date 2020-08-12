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
          </div>
        </button>
        <button class="btn-login" @click="openLogin" v-if="!getIsLogin()">
          LOG . IN
        </button>
        <router-link
          tag="button"
          class="btn-newpost"
          to="/article"
          v-if="getIsLogin()"
          >새 글쓰기</router-link
        >
        <div class="notification-icon" v-if="getIsLogin()">
          <svg
            id="notification-icon"
            data-name="notification-icon"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 21.34 24"
          >
            <path
              d="M6.15,23.69a2.46,2.46,0,0,1-.57.13,2.4,2.4,0,0,0,.91-.23l5.33-2Z"
              transform="translate(-2.33 -1)"
            />
            <path
              d="M17.76,22.2a2.9,2.9,0,0,1-2.15,2.66,2.83,2.83,0,0,1-3.12-.94c-.2-.27-.23-.44.14-.57l4.58-1.76C17.65,21.43,17.79,21.59,17.76,22.2Z"
              transform="translate(-2.33 -1)"
            />
            <path
              d="M22.65,17.42c-2.19.85-4.39,1.68-6.59,2.52l-3.31,1.27,9.58-3.63a2.43,2.43,0,0,0,.81-.43A1.93,1.93,0,0,1,22.65,17.42Z"
              transform="translate(-2.33 -1)"
            />
            <path
              d="M22.59,14.43A5.48,5.48,0,0,1,20,11.61a1.89,1.89,0,0,1-.11-.25c-.4-1-.76-2-1.14-3a8.29,8.29,0,0,0-7.38-5.79c-.32,0-.65-.05-1-.05h-.08c-.22,0-.35-.11-.49-.34A2.31,2.31,0,0,0,8.43,1.07L8.16,1a1.51,1.51,0,0,0-.3,0L7.57,1l-.32.07a2.19,2.19,0,0,0-.33.12l-.32.17a1.24,1.24,0,0,0-.23.17l-.18.17A2.34,2.34,0,0,0,5.6,3.8c.05.24.05.41-.11.54,0,0,0,0,0,0l-.06.06c-.24.22-.47.44-.68.66A8.29,8.29,0,0,0,3,14.34c.38,1,.77,2,1.14,3a2.76,2.76,0,0,1,.08.27,5.52,5.52,0,0,1,0,3.8,1.65,1.65,0,0,0,1.25,2.4h.12a2.46,2.46,0,0,0,.57-.13l5.67-2.13.93-.35,3.31-1.27c2.2-.84,4.4-1.67,6.59-2.52a1.93,1.93,0,0,0,.49-.27l.13-.12A1.65,1.65,0,0,0,22.59,14.43ZM5.17,12.85c-.2,0-.27-.11-.32-.26a2.71,2.71,0,0,1-.09-.77c0-.21-.14-.58.23-.63s.29.36.34.59.06.46.08.69S5.38,12.82,5.17,12.85ZM7.83,6.26A6.45,6.45,0,0,0,6.62,7.5a6.2,6.2,0,0,0-1,1.91,2.67,2.67,0,0,1-.09.26.29.29,0,0,1-.35.22C5,9.85,5,9.7,5,9.5l.08-.28A7.6,7.6,0,0,1,6.21,7,6.18,6.18,0,0,1,7.43,5.81c.2-.15.42-.38.64-.09S8,6.12,7.83,6.26Z"
              transform="translate(-2.33 -1)"
            />
          </svg>
        </div>
        <!-- <router-link class="profile-icon" v-if="getIsLogin()" to="/setting"> -->
        <div class="profile-image" v-if="getIsLogin()">
          <button class="banner-image-edit" @click="moveToProfile"></button>
        </div>
        <!-- </router-link> -->
      </div>
    </div>
    <Navbar />
    <Login />
    <Join />
    <Repassword />
    <EmailModal />
    <UnregisterModal />
    <ProfilePicModal />
  </div>
</template>

<script>
import Navbar from "@/components/common/Navbar.vue";
import Login from "@/components/user/Login.vue";
import Join from "@/components/user/Join.vue";
import Repassword from "@/components/user/Repassword.vue";
import EmailModal from "@/components/user/EmailModal.vue";
import UnregisterModal from "@/components/user/setting/UnregisterModal.vue";
import ProfilePicModal from "@/components/user/setting/ProfilePicModal.vue";

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
    UnregisterModal,
    ProfilePicModal,
  },
  data() {
    return {
      searchWord: "",
    };
  },
  methods: {
    ...mapActions({
      Logout: "user/logout",
      fetchUserInfo: "user/fetchUserInfo",
    }),
    ...mapGetters({
      getIsLogin: "user/getIsLogin",
      getUserInfo: "user/getUserInfo",
      getEmail: "user/getEmail",
    }),
    showNavBar() {
      const navbar = document.querySelector(".navbar");
      const background = document.querySelector(".navbar-background");
      const body = document.querySelector("body");

      body.classList.add("hide-scroll");
      navbar.setAttribute("style", "left: 0px");
      //navbar.style.left = "0px";
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
        params: { keyword: this.searchWord },
      });
    },
    moveToProfile() {
      this.$router.push({ name: "Setting" });
    },
  },
  created() {
    this.fetchUserInfo(this.getEmail());
  },
  mounted() {
    var profileImages = document.querySelectorAll(".profile-image");
    profileImages.forEach((profileImage) => {
      profileImage.style.backgroundImage = `url('${
        this.getUserInfo().profile
      }')`;
      console.log(this.getUserInfo());
    });
  },
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
  width: auto;
  margin-left: auto;
  margin-right: 30px;
  svg {
    width: 24px;
  }
}

.btn-login {
  // padding: 4px 16px;
  border: none;
  border-radius: 5%;
  color: #1a7cff;
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
.btn-newpost {
  border-radius: 25px;
  color: #727272;
  border: 2px solid #727272;
  padding: 0px 20px;
  margin-right: 24px;
}

.notification-icon {
  margin-right: 24px;
  #notification-icon {
    fill: #727272;
  }
}

.profile-image {
  background-position: center;
  background-image: url(https://images.unsplash.com/photo-1517832207067-4db24a2ae47c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60);
  background-size: 150%;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  button {
    width: 100%;
    height: 100%;
    font-size: 1.4rem;
    color: rgba(0, 0, 0, 0);
    background-color: rgba(0, 0, 0, 0);
    border-radius: 50%;
    &:hover {
      color: rgba(255, 255, 255, 0.8);
      background-color: rgba(0, 0, 0, 0.6);
    }
  }
}
</style>
