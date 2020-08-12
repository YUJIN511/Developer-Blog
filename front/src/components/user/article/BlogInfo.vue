<template>
  <div class="container-blog-info">
    <img
      src="https://images.unsplash.com/photo-1586453728189-97b6a0e35c98?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
      alt=""
    />
    <div class="main-info">
      <div class="user-info">
        <span class="user-icon">😉</span
        ><span class="user-nickname">닉네임</span>
      </div>
      <div class="blog-title">블로그 제목</div>
      <div class="blog-description">
        Lorem ipsum, dolor sit amet consectetur adipisicing elit. Quaerat
        asperiores corrupti pariatur animi sit porro ut, ratione officia
        molestiae sequi harum veritatis iusto distinctio velit, quae tenetur
        possimus suscipit cumque nemo. Inventore, deserunt? Sequi, laudantium
        iste labore sunt quas laborum!
      </div>
      <div class="follower-number">팔로워 숫자</div>
    </div>
    <button class="btn-follow">
      팔로우
    </button>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
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
