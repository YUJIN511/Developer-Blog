<template>
  <div class="container-setting">
    <Navbar />
    <router-view></router-view>
  </div>
</template>

<script>
import Navbar from "@/components/user/setting/Navbar.vue";
export default {
  components: {
    Navbar
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
