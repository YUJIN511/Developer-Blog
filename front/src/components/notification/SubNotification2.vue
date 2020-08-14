<template>
    <div class="container-notification">
        <div class="sub-notification" @click="moveUrl">
            <div class="profile-image"></div>
            <div class="notification-msg">{{data.message}} </div>
            <span v-if="data.isread==false">1</span>
        </div>
        <button class="btn-delete" @click="deletenotification">✖</button>
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
        deletenotification(){
            axios
                .delete(`${SERVER_URL}/api/notification/`,{
                    params: {
                        id: this.data.id,
                    },
                })
                .then(() => {
                    location.reload();
                })
                .catch(err => {
                    console.log(err);
                });
        },
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
                            params: { email: this.getEmail()}
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
.container-notification{
    display: flex;
}
.sub-notification {
  flex-direction: column;
  height: 40px;
  font-size: 12px;
  padding :5px;
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
    margin-right: 10px;
  }
.notification-msg{
    min-width: 220px;
    max-width: 700px;
    display: table-cell;
    vertical-align: middle;
    text-align: center;

    background-color: rgba(0, 0, 0, 0.05);
    border-radius: 6px;
    padding:10px;
}

span{
    padding: 8px;
    color: #727272;
}
.btn-delete{
    display : none;
}
.container-notification:hover .btn-delete{
    display : inline;
    margin :  0 15px;
    color: #727272;
}
</style>