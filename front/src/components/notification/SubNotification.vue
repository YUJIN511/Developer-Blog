<template>
    <div class="sub-notification" @click="moveUrl">
        <div class="profile-image"></div>
        <div class="message">{{data.message}} </div>
    </div>
</template>

<script>
export default {
    props: {
        data: { }
    },
    methods:{
        moveUrl(){
            window.location.href =this.data.notiurl;
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
.message{
    width:210px;
    display: table-cell;
    vertical-align: middle;
}
</style>