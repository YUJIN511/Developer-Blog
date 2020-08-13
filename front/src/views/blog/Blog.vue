<template>
  <div class="container-setting">
    <Navbar />
    <UserBlog />
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

import Navbar from "@/components/common/Navbar-mini.vue";
import UserBlog from "@/views/blog/UserBlog.vue";
export default {
  components: {
    Navbar,
    UserBlog
  },
  methods: {
    ...mapActions({}),
    ...mapGetters({})
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
