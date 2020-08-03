export default [
  {
    path: "setNickname/:email",
    name: "setNickname",
    component: () => import("@/views/user/SetNickname.vue"),
  },
  {
    path: "resetpassword/:email",
    name: "resetPassword",
    component: () => import("@/views/user/RepasswordLink.vue"),
  },
  {
    path: "loginPassword/:email",
    name: "LoginPassword",
    component: () => import("@/views/user/LoginPassword.vue"),
  },
];
