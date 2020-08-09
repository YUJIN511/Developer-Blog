<template>
  <div class="container-setting">
    <Navbar />
    <router-view></router-view>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

import Navbar from "@/components/user/setting/Navbar.vue";
export default {
  components: {
    Navbar
  },
  methods: {
    ...mapActions({
      fetchUserInfo: "user/fetchUserInfo"
    }),
    ...mapGetters({
      getEmail: "user/getEmail",
      getUserInfo: "user/getUserInfo"
    })
  },
  mounted() {
    this.fetchUserInfo(this.getEmail());
    var profileImages = document.querySelectorAll(".profile-image");
    profileImages.forEach(profileImage => {
      profileImage.style.backgroundImage = `url('${
        this.getUserInfo().profile
      }')`;
    });
  }
};
</script>

<style lang="scss" scoped>
.container-setting {
  min-height: 100vh;
  padding-top: 60px;
  display: flex;
}
</style>
