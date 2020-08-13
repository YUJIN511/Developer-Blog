<template>
  <div class="profile">
    <div class="left">
      <button class="btn-profile">
        <img :src="data.profileUrl" alt="" />
      </button>
    </div>
    <div class="right">
      <div class="name">{{ data.userName }}</div>
      <div class="blog-name">{{ data.blogName }}</div>
      <div class="footer">
        <button class="btn-alarm">
          <img src="@/assets/notification_icon.svg" alt="알림 설정" />
        </button>
        <button class="btn-follow">
          팔로우 취소
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    data: {
      type: Object
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/Profile.scss";
</style>
