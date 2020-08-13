<template>
    <div class="container-notification hide">
        <div class="background-notification" @click="closeNotification"></div>
        <div class="modal-notification">
            <div class="navbar-item" >
                <div>
                    <span>알림</span>
                    <svg @click="moveSettingAlarm"
                        xmlns="http://www.w3.org/2000/svg"
                        xmlns:xlink="http://www.w3.org/1999/xlink"
                        viewBox="0 0 26 26"
                    >
                    <path
                        d="M22.2,14.2c0.1-0.4,0.1-0.8,0.1-1.2c0-0.4,0-0.8-0.1-1.2l2.6-2C25,9.6,25.1,9.2,24.9,9l-2.5-4.3c-0.2-0.3-0.5-0.4-0.8-0.3
                l-3.1,1.2c-0.6-0.5-1.3-0.9-2.1-1.2l-0.5-3.3c-0.1-0.3-0.3-0.5-0.6-0.5h-4.9c-0.3,0-0.6,0.2-0.6,0.5L9.5,4.5C8.7,4.8,8,5.2,7.4,5.7
                L4.3,4.4C4,4.3,3.7,4.4,3.6,4.7L1.1,9C0.9,9.2,1,9.6,1.3,9.8l2.6,2c-0.1,0.4-0.1,0.8-0.1,1.2s0,0.8,0.1,1.2l-2.6,2
                C1,16.4,0.9,16.8,1.1,17l2.5,4.3c0.2,0.3,0.5,0.4,0.8,0.3l3.1-1.2c0.6,0.5,1.3,0.9,2.1,1.2l0.5,3.3c0.1,0.3,0.3,0.5,0.6,0.5h4.9
                c0.3,0,0.6-0.2,0.6-0.5l0.5-3.3c0.8-0.3,1.5-0.7,2.1-1.2l3.1,1.2c0.3,0.1,0.6,0,0.8-0.3l2.5-4.3c0.2-0.3,0.1-0.6-0.2-0.8L22.2,14.2z
                M13,17.6c-2.5,0-4.6-2.1-4.6-4.6s2.1-4.6,4.6-4.6s4.6,2.1,4.6,4.6S15.5,17.6,13,17.6z"
                    />
                    </svg>
                </div>
            </div>
            <hr>
            <div class="notification-list">

            </div>

            <hr>
            <div>
                <p @click="moveNotificationDetail">모두보기</p>
            </div>
        </div>
    </div> 
</template>

<script>

export default {
    methods: {
        closeNotification(){
            document.querySelector(".container-notification").classList.add("hide");
        },
        moveSettingAlarm(){
            this.$router.push({ path: "/setting/alarm" });
            this.closeNotification();
        },
        moveNotificationDetail(){
            this.$router.push({ name: "Notification" });
            this.closeNotification();
        }
    }
}
</script>

<style scoped lang="scss">
    @import "@/assets/common/Navbar.scss";

    .container-notification {
        position: fixed;
        z-index: 99999;
        top: 0;
        left: 0;
        width: 100vw;
        height: 100vh;
        display: flex;
        justify-content: flex-end;
        background-color: rgba(0, 0, 0, 0);
    }
    .background-notification {
        position: fixed;
        width: 100vw;
        height: 100vh;
        background-color: rgba(0, 0, 0, 0.4);
    }

    .modal-notification{
        margin: 56px 105px 0 0;
        position: fixed;
        width: 280px;
        height: 416px;
        background-color: white;
        box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
    }

    .hide{
        display: none;
    }

    hr {
        margin: 5px 0;
    }
    p {
        color: #1A7CFF;
        font-size: 14px;
        font-weight: 600;
    }
    .navbar-item{
        margin : 7px 0px;
        padding: 0px 10px;
    }

    .navbar-item span{
       margin-right: 75%;
       font-size: 20px;
       font-weight: bold;
       color: #727272 ;
    }            

    .notification-list{
       height: 79%;
    }

</style>