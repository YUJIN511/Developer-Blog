<template>
  <div class="container-base">
    <div class="notification-list">
        <div class="row">
            <span id="all">내 알림</span>
            <router-link id="setting" to="/setting/alarm">알림 설정</router-link>
        </div>
        <hr>
        <template v-for="(data, id) in notifications">
          <SubNotification :key="id" v-bind:data="data" />
        </template>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapGetters } from "vuex";
import SubNotification from "@/components/notification/SubNotification2.vue"

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  name: "notification",
  components:{SubNotification},
  data() {
    return {
      notifications:[]
    };
  },
  methods: {
    ...mapGetters({
            getEmail: "user/getEmail"
    }),
  },
  created() {
    axios
      .get(`${SERVER_URL}/api/notification/all`,{
         params: {
            email: this.getEmail(),
          },
      })
      .then(res => {
        this.notifications = res.data;
      })
      .catch(err => {
        console.log(err);
      });
  },
 
};
</script>

<style scoped lang="scss">
   .row {
        width: 100%;
        display: inline-block;
        padding:10px;
        #all{
            float: left;
            font-size: 20px;
            font-weight: bold;
            color: #727272 ;
        }
        #setting{
          margin-top: 2px;
          float: right;
          text-decoration: none;
          color: #727272 ;
          cursor: pointer;
       }
   }
  .notification-list{
      width: 60%;
      margin: auto;
      overflow-y: auto;
  }
  .notification-list::-webkit-scrollbar {
        display: none; /* Chrome, Safari, Opera*/
  }
  hr {
        margin: 3px 0;
        border-bottom: 0px;
    }
  
@import "@/assets/common/Base.scss";
</style>
