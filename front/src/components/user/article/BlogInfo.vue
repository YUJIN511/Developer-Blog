<template>
  <div class="container-blog-info">
    <img :src="articleData.profile" alt />
    <div class="main-info">
      <div class="user-info">
        <span class="user-icon">😉</span>
        <span class="user-nickname">{{articleData.nickname}}</span>
      </div>
      <div class="blog-title">{{articleData.blogName}}</div>
      <div class="blog-description">{{articleData.blogDescription}}</div>
      <div class="follower-number">팔로우 {{articleData.followerNum}}명</div>
    </div>
    <button class="btn-follow">팔로우</button>
  </div>
</template>

<script>
// import axios from "axios";

export default {
  props: {
    articleData: {
      type: Object
    }
  }
};
</script>

<style lang="scss" scoped>
button:hover {
  opacity: 0.7;
}

.container-blog-info {
  display: flex;
  align-items: center;
  width: 100%;
  height: 170px;
  margin-top: 20px;
  padding: 20px 0px;
  img {
    width: 130px;
    height: 130px;
    border-radius: 50%;
    margin-right: 30px;
  }
  .main-info {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
    .user-icon {
      margin-right: 10px;
    }
    .user-nickname {
      font-weight: 900;
    }
    .blog-title {
      font-size: 1.5rem;
      font-weight: 900;
    }
    .blog-description {
      font-size: 1.1rem;
      overflow: hidden;
    }
    .follower-number {
      font-weight: 900;
    }
  }
  .btn-follow {
    flex-shrink: 0;
    margin-left: auto;
    font-size: 1.2em;
    color: dodgerblue;
    font-weight: 900;
  }
}
</style>
