import axios from "axios";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  namespaced: true,

  state: {
    userInfo: {
      email: "",
      nickname: "",
    },

    isLogin: false,
    token: "",
  },

  getters: {
    getIsLogin(state) {
      return state.isLogin;
    },
    getAuthHeader(state) {
      const config = {
        headers: {
          Authorization: "Bearer " + state.token,
        },
      };
      return config;
    },
    getUserInfo(state) {
      return state.userInfo;
    },
    getEmail(state) {
      return state.userInfo.email;
    },
    getProfile(state) {
      return state.userInfo.profile;
    },
  },

  mutations: {
    setIsLogin(state, isLogin) {
      state.isLogin = isLogin;
    },
    setToken(state, token) {
      state.token = token;
    },
    setEmail(state, email) {
      state.userInfo.email = email;
    },
    setLogout(state) {
      state.userInfo = {};
      state.isLogin = false;
      state.token = "";
    },
    setNickname(state, nickname) {
      state.userInfo.nickname = nickname;
    },
    setRole(state, role) {
      state.userInfo.role = role;
    },
    setProfile(state, profile) {
      state.userInfo.profile = profile;
    },
  },

  actions: {
    async login(context, userInfo) {
      try {
        const res = await axios.post(`${SERVER_URL}/api/auth/signin`, userInfo);
        if (res.status === 200) {
          context.commit("setIsLogin", true);
          context.commit("setToken", res.data.accessToken);
          context.commit("setEmail", userInfo.email);
          return true;
        }
      } catch (error) {
        console.log(error);
        return false;
      }
    },
    async join(context, userInfo) {
      try {
        const res = await axios.post(`${SERVER_URL}/api/auth/signup`, userInfo);
        if (res.status === 200) {
          return true;
        }
      } catch (error) {
        console.log(error);
        return false;
      }
    },
    logout(context) {
      context.commit("setLogout");
    },
    fetchUserInfo(context, email) {
      axios
        .get(`${SERVER_URL}/api/auth/userInfo`, {
          params: {
            email: email,
          },
        })
        .then((res) => {
          // console.log(res);
          context.commit("setNickname", res.data.nickname);
          context.commit("setRole", res.data.roles["name"]);
          context.commit("setProfile", res.data.profile);
        })
        .catch((err) => console.log(err));
    },
    receiveToken(context, { token, email }) {
      context.commit("setToken", token);
      context.commit("setIsLogin", true);
      context.commit("setEmail", email);
    },
    updateNickname({ commit, getters }, userInfo) {
      axios
        .post(
          `${SERVER_URL}/api/auth/nicknameUpdate`,
          userInfo,
          getters.getAuthHeader
        )
        .then((res) => {
          console.log(res);
          commit("setNickname", res.config.data.nickname);
        })
        .catch((err) => console.log(err));
    },
    updatePassword(getters, userInfo) {
      axios
        .post(
          `${SERVER_URL}/api/auth/passwordUpdate`,
          userInfo,
          getters.getAuthHeader
        )
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    },
    unregister({ commit, getters }) {
      axios
        .get(`${SERVER_URL}/api/auth/unregister`, {
          params: {
            email: getters.getEmail,
          },
        })
        .then((res) => {
          console.log(res);
          alert("회원 탈퇴가 완료되었습니다.");
          commit("setLogout", false);
        })
        .catch((err) => console.log(err));
    },
  },
};
