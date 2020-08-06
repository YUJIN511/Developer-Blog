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
      getEmail: "user/getEmail"
    })
  },
  mounted() {
    this.fetchUserInfo(this.getEmail()).then(res => console.log(res));
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
