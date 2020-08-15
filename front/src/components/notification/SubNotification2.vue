<template>
    <div class="container-notification">
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
            <span v-if="data.isread==false">1</span>
        </div>
        <svg class="btn-delete" @click="deletenotification"
                viewBox="0 0 512 512" 
                xmlns="http://www.w3.org/2000/svg">
                <path d="m256 512c-141.160156 0-256-114.839844-256-256s114.839844-256 256-256 256 114.839844 256 256-114.839844 
                256-256 256zm0-475.429688c-120.992188 0-219.429688 98.4375-219.429688 219.429688s98.4375 219.429688 219.429688 
                219.429688 219.429688-98.4375 219.429688-219.429688-98.4375-219.429688-219.429688-219.429688zm0 0"/>
                <path d="m347.429688 365.714844c-4.679688 0-9.359376-1.785156-12.929688-5.359375l-182.855469-182.855469c-7.144531-7.144531-7.144531-18.714844 0-25.855469 7.140625-7.140625 18.714844-7.144531 25.855469 0l182.855469 182.855469c7.144531 7.144531 7.144531 18.714844 0 25.855469-3.570313 3.574219-8.246094 5.359375-12.925781 5.359375zm0 0"/>
                <path d="m164.570312 365.714844c-4.679687 0-9.355468-1.785156-12.925781-5.359375-7.144531-7.140625-7.144531-18.714844 0-25.855469l182.855469-182.855469c7.144531-7.144531 18.714844-7.144531 25.855469 0 7.140625 7.140625 7.144531 18.714844 0 25.855469l-182.855469 182.855469c-3.570312 3.574219-8.25 5.359375-12.929688 5.359375zm0 0"/>
        </svg>
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
.container-notification{
    display: flex;
}
.sub-notification {
  flex-direction: column;
  height: 40px;
  font-size: 12px;
  margin :8px;
  display: table;
  cursor: pointer;
}
.profile-image  {
  height: 15%;
  padding: 5px 16px;
  align-items: center;
  border-radius: 0 0 5px 5px;
}

svg, img{
    width: 36px;
    height: 36px;
    border-radius: 50%;
    fill: gray;
}

.notification-msg{
    min-width: 220px;
    max-width: 600px;
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
    width: 15px;
    align-self: center;
    fill: #FF6D6D;
    cursor: pointer;
}
.container-notification:hover .btn-delete{
    display : inline;
}
</style>