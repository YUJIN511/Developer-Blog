import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueCryptojs from "vue-cryptojs";
import vuetify from "./plugins/vuetify";
import "@babel/polyfill";

Vue.use(VueCryptojs);

Vue.config.productionTip = false;
Vue.prototype.$apiServer = "http://i3a604.p.ssafy.io:8081/api";

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");
