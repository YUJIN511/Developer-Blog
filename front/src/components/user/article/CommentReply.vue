<template>
  <div class="container-comment-reply">
    <header>
      <div>
        <img :src="replyData.profile" alt="" />
      </div>
      <div class="info">
        <div class="user-info">
          <span>😀</span><span>{{ replyData.nickname }}</span>
          <button class="btn-more">⫶</button>
        </div>
        <!-- <span class="date">{{ replyData.createdate.split("T")[0] }}</span> -->
      </div>
    </header>
    <main>
      {{ replyData.content }}
    </main>
    <footer>
      <button class="btn-like">
        <svg
          class="icon-like"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
        >
          <path
            d="M12 4.248c-3.148-5.402-12-3.825-12 2.944 0 4.661 5.571 9.427 12 15.808 6.43-6.381 12-11.147 12-15.808 0-6.792-8.875-8.306-12-2.944z"
          />
        </svg>
      </button>
      <span class="like-count">{{ replyData.likes }}</span>
      <button class="btn-reply">답글</button>
    </footer>
  </div>
</template>

<script>
export default {
  props: ["replyData"]
};
</script>

<style lang="scss" scoped>
.container-comment-reply {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 10px;
  margin-bottom: 30px;
  header {
    display: flex;
    align-items: center;
    width: 100%;
    img {
      width: 60px;
      height: 60px;
      border-radius: 50%;
    }
    .info {
      display: flex;
      width: 100%;
      flex-direction: column;
      margin-left: 15px;
      .user-info {
        display: flex;
        justify-content: stretch;
        span {
          margin-right: 6px;
        }
        .btn-more {
          margin-left: auto;
          &:hover {
            opacity: 0.5;
          }
        }
      }
      .date {
        color: rgb(179, 179, 179);
      }
    }
  }
  main {
    color: rgb(80, 80, 80);
    margin: 1rem 0;
  }
  footer {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    .btn-like {
      display: flex;
      align-items: center;
      margin-right: 5px;
      svg {
        width: 20px;
        height: 20px;
        fill: rgb(165, 165, 165);
      }
    }
    .btn-reply {
      margin-left: 15px;
      color: gray;
      &:hover {
        color: black;
      }
    }
  }
}
</style>
