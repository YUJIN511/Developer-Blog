<template>
    <div class="sub-notification" @click="moveUrl">
        <div class="profile-image"> 
            <img :src="data.profile" v-if="data.profile" />
             <svg
            v-else
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            viewBox="0 0 26 26"
            >
            <path
            d="M13,1C6.4,1,1,6.4,1,13s5.4,12,12,12s12-5.4,12-12S19.6,1,13,1z M13,4.6c2,0,3.6,1.6,3.6,3.6S15,11.8,13,11.8
            s-3.6-1.6-3.6-3.6S11,4.6,13,4.6z M13,21.6c-3,0-5.7-1.5-7.2-3.9c0-2.4,4.8-3.7,7.2-3.7c2.4,0,7.2,1.3,7.2,3.7
            C18.7,20.1,16,21.6,13,21.6z"
            />
            </svg>
        </div>
        <div class="notification-msg">{{data.message}} </div>
    </div>
</template>

<script>
import axios from 'axios';
import { mapGetters } from "vuex";
const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
    props: {
        data: { }
    },
    methods:{
        ...mapGetters({
            getEmail: "user/getEmail"
        }),
        moveUrl(){
            document.querySelector(".container-notification").classList.add("hide");
            axios
                .get(`${SERVER_URL}/api/notification/updateIsread`,{
                    params: {
                       id: this.data.id,
                    },
                    withCredentials: true,
                })
                .then(() => {
                    if(this.data.type == 16){
                        this.$router.push({ 
                            name: "Blog", 
                            params: { email: this.data.actionuser } 
                        });
                    } else{
                        this.$router.push({
                            name: "ArticleView",
                            query: { id: this.data.postid }
                        });
                    }
                })
                .catch(err => {
                    console.log(err);
                });    
        }
    }
}
</script>

<style scoped>
.sub-notification {
  flex-direction: column;
  height: 40px;
  font-size: 12px;
  margin: auto;
  padding :5px;
  display: table;
  cursor: pointer;
}

svg, img{
    width: 36px;
    height: 36px;
    border-radius: 50%;
    fill: gray;
    margin-right: 7px;
}

.notification-msg{
    max-width: 210px;
    min-width: 210px;
    display: table-cell;
    vertical-align: middle;

    background-color: rgba(0, 0, 0, 0.05);
    border-radius: 6px;
    padding:5px;
}
</style>
