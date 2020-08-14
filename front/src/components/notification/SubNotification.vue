<template>
    <div class="sub-notification" @click="moveUrl">
        <div class="profile-image"></div>
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
            axios
                .get(`${SERVER_URL}/api/notification/updateIsread`,{
                    params: {
                       id: this.data.id,
                    },
                    withCredentials: true,
                })
                .then(() => {
                    if(this.data.type == 16){
                        this.$router.push({name: "Blog", params: { email: this.getEmail()}});
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
            
            document.querySelector(".container-notification").classList.add("hide");
        }
    }
}
</script>

<style scoped>
.sub-notification {
  flex-direction: column;
  height: 40px;
  font-size: 12px;
  margin: 10px 0;
  display: table;
  cursor: pointer;
}
.profile-image {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 150%;
    background-image: url("https://images.unsplash.com/photo-1517832207067-4db24a2ae47c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
    margin: 0 10px;
  }
.notification-msg{
    width:210px;
    display: table-cell;
    vertical-align: middle;

    background-color: rgba(0, 0, 0, 0.05);
    border-radius: 6px;
    padding:2px;
}
</style>