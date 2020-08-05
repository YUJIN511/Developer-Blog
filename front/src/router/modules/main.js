export default [
  {
    path: "home",
    name: "Home",
    component: () => import("@/views/Home.vue"),
  },
  {
    path: "about",
    name: "About",
    component: () => import("@/views/About.vue"),
  },
  {
    path: "follow",
    name: "Follow",
    component: () => import("@/views/Follow.vue"),
  },
  {
    path: "followadditional",
    name: "FollowAdditional",
    component: () => import("@/views/FollowAdditional.vue"),
  },
  {
    path: "trending",
    name: "Trending",
    component: () => import("@/views/Trending.vue"),
  },
  {
    path: "history",
    name: "History",
    component: () => import("@/views/History.vue"),
  },
  {
    path: "library",
    name: "Library",
    component: () => import("@/views/Library.vue"),
  },
  {
    path: "search/:keyword",
    name: "Search",
    component: () => import("@/views/Search.vue"),
  },
];
