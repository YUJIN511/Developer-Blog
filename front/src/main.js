import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueCryptojs from "vue-cryptojs";
import "@/assets/sass/main.scss";

Vue.use(VueCryptojs);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
