import Vue from "vue";
import VueRouter from "vue-router";
import mainPageRouter from "@/router/modules/main.js";
import settingPageRouter from "@/router/modules/setting.js";
import accountPageRouter from "@/router/modules/account.js";
import articlePageRouter from "@/router/modules/article.js";
// import userRouter from "@/router/modules/userRouter.js";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      redirect: "/main"
    },
    {
      path: "/main",
      redirect: "/main/home",
      name: "Main",
      component: () => import("@/views/Main.vue"),
      children: mainPageRouter
    },
    {
      path: "/setting",
      redirect: "/setting/blog",
      name: "Setting",
      component: () => import("@/views/Setting.vue"),
      children: settingPageRouter
    },
    {
      path: "/account",
      redirect: "/main/home",
      name: "Account",
      component: () => import("@/views/Main.vue"),
      children: accountPageRouter
    },
    {
      path: "/article",
      redirect: "/article/edit",
      name: "Article",
      component: () => import("@/views/Article.vue"),
      children: articlePageRouter
    },
    {
      path: '/notification',
      name: 'notification',
      component: notification
    }
  ]
});

export default router;
