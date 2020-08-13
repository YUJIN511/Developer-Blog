<template>
  <div>
    <LimitedAccess v-if="!getIsLogin()" />
    <div class="container-base" v-if="getIsLogin()">
      <span class="title">방문 기록</span>
      <FlexArticles :datas="articleData" />
    </div>
  </div>
</template>

<script>
import FlexArticles from "@/components/common/FlexArticles.vue";
import LimitedAccess from "@/components/user/LimitedAccess.vue";
import axios from "axios";

import { mapMutations, mapGetters } from "vuex";

export default {
  components: {
    FlexArticles,
    LimitedAccess
  },
  data: function() {
    return {
      articleData: []
    };
  },
  methods: {
    ...mapMutations({
      paintBtn: "navbarMini/paintBtn"
    }),
    ...mapGetters({
      getIsLogin: "user/getIsLogin",
      getUserInfo: "user/getUserInfo"
    })
  },
  async created() {
    try {
      const res = await axios.get(
        `${this.$apiServer}/main/historyList?email=${this.getUserInfo().email}`
      );
      this.articleData = res.data;
    } catch (error) {
      console.log(error);
    }
  },
  mounted() {
    this.paintBtn(document.querySelector("#btn-history"));
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/_variables.scss";
@import "@/assets/common/Base.scss";
</style>
