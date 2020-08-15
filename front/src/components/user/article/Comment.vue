<template>
  <div class="container-comment">
    <header>
      <div class="profile-img-box">
        <img :src="commentData.profile" alt />
      </div>
      <div class="info">
        <div class="user-info">
          <span>😀</span>
          <span>{{ commentData.nickname }}</span>
          <button class="btn-more">⫶</button>
        </div>
        <span class="date">{{ commentData.createdate.split("T")[0] }}</span>
      </div>
    </header>
    <main ref="main"></main>
    <footer>
      <button class="btn-like" @click="toggleLikeButton">
        <svg
          class="icon-like-comment"
          ref="likeIcon"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
        >
          <path
            d="M12 4.248c-3.148-5.402-12-3.825-12 2.944 0 4.661 5.571 9.427 12 15.808 6.43-6.381 12-11.147 12-15.808 0-6.792-8.875-8.306-12-2.944z"
          />
        </svg>
      </button>
      <span class="like-count">{{ commentData.likes }}</span>
      <button class="btn-reply-write" @click="toggleReplyTextarea">
        답글
      </button>
      <div class="container-reply-toggle" v-show="isReplyTextareaShow">
        <div
          class="textarea"
          contenteditable="true"
          placeholder="답글을 입력하세요..."
          ref="textarea"
        ></div>
        <button class="btn-cancel" @click="toggleReplyTextarea">
          취소
        </button>
        <button class="btn-submit-reply" @click="submitReply">
          답글작성
        </button>
      </div>
    </footer>
    <template v-if="!isReply">
      <button
        class="btn-reply-toggle"
        v-if="commentData.replycount !== 0"
        @click="toggleReply"
      >
        <svg
          ref="replySvg"
          xmlns="http://www.w3.org/2000/svg"
          width="6"
          height="6"
          viewBox="0 0 24 24"
        >
          <path d="M24 22h-24l12-20z" />
        </svg>
        <span>답글 {{ commentData.replycount }}개 보기</span>
      </button>
      <ReplyList
        ref="replyList"
        :commentId="commentData.id"
        v-show="isReplyShow"
        :key="replyListKey"
        @reloadReply="reloadReply"
      />
    </template>
  </div>
</template>

<script>
import ReplyList from "./CommentReplyList.vue";
import axios from "axios";
import { mapGetters } from "vuex";
export default {
  props: ["isReply", "commentData"],
  components: {
    ReplyList
  },
  data: function() {
    return {
      isReplyShow: false,
      isReplyTextareaShow: false,
      isReplyFirstCall: true,
      isLike: 0,
      replyContent: "",
      replyListKey: 0
    };
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    toggleReply() {
      this.isReplyShow = !this.isReplyShow;
      const svg = this.$refs.replySvg;
      if (this.isReplyShow) {
        if (this.isReplyFirstCall) {
          this.isReplyFirstCall = false;
          this.$refs.replyList.getData();
        }
        svg.classList.add("upper");
        svg.nextSibling.innerText = "답글 숨기기";
      } else {
        svg.classList.remove("upper");
        svg.nextSibling.innerText = `답글 ${this.commentData.replycount}개 보기`;
      }
    },
    toggleReplyTextarea() {
      this.isReplyTextareaShow = !this.isReplyTextareaShow;
      if (this.isReplyTextareaShow) {
        this.openReplyTextArea();
      } else {
        this.closeReplyTextArea();
      }
    },
    openReplyTextArea() {
      if (this.isReply) {
        this.$refs.textarea.innerHTML = `<a contenteditable="false" style="color: dodgerblue; margin-right:10px" href="/${this.commentData.email}">@${this.commentData.nickname} </a>`;
      }
    },
    closeReplyTextArea() {
      this.$refs.textarea.innerHTML = "";
    },
    setLikeBtn() {
      const likeIcon = this.$refs.likeIcon;
      if (this.isLike) {
        likeIcon.classList.add("fill-lightred");
      } else {
        likeIcon.classList.remove("fill-lightred");
      }
    },
    toggleLikeButton() {
      this.isLike = !this.isLike;
      if (this.isLike) {
        axios.put(
          `${this.$apiServer}/comment/like?email=${
            this.getUserInfo().email
          }&id=${this.commentData.id}`,
          {}
        );
        this.commentData.likes++;
      } else {
        axios.put(
          `${this.$apiServer}/comment/unlike?email=${
            this.getUserInfo().email
          }&id=${this.commentData.id}`
        );
        this.commentData.likes--;
      }
      this.setLikeBtn();
    },
    async submitReply() {
      try {
        let replyto = this.commentData.id;
        if (this.isReply) {
          replyto = this.commentData.replyto;
        }
        await axios.post(`${this.$apiServer}/comment`, {
          content: this.$refs.textarea.innerHTML,
          email: this.getUserInfo().email,
          postid: this.$route.query.id,
          replyto
        });
        this.reloadReply();
        if (this.isReply) {
          this.emitReload();
        }
      } catch (error) {
        console.log(error);
      }
    },
    emitReload() {
      this.$emit("reload");
    },
    reloadReply() {
      this.isReplyFirstCall = true; // 리로드 해서 내용물이 없어져서 다시 불러와야함
      this.replyListKey++;
      this.$refs.textarea.innerHTML = "";
      this.commentData.replycount++;
      this.isReplyShow = false;
      if (!this.isReply) {
        const svg = this.$refs.replySvg;
        svg.classList.remove("upper");
        svg.nextSibling.innerText = `답글 ${this.commentData.replycount}개 보기`;
      }
    }
  },
  mounted() {
    this.isLike = this.commentData.islike;
    this.setLikeBtn();
    this.$refs.main.innerHTML = this.commentData.content;
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/_variables.scss";
.container-comment {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 15px;
  header {
    display: flex;
    align-items: center;
    width: 100%;
    .profile-img-box {
      flex-shrink: 0;
      img {
        width: 60px;
        height: 60px;
        border-radius: 50%;
      }
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
          font-size: 1em;
          font-weight: 900;
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
    flex-wrap: wrap;
    width: 100%;
    .btn-like {
      display: flex;
      align-items: center;
      margin-right: 5px;
      .icon-like-comment {
        width: 20px;
        height: 20px;
        fill: rgb(165, 165, 165);
      }
      .fill-lightred {
        fill: $lightRed;
      }
    }
    .btn-reply-write {
      margin-left: 15px;
      color: gray;
      &:hover {
        color: black;
      }
    }
    .container-reply-toggle {
      display: flex;
      justify-content: flex-end;
      flex-wrap: wrap;
      width: 100%;
      [contenteditable="true"]:empty:before {
        content: attr(placeholder);
        display: block;
      }
      .textarea {
        margin-top: 20px;
        margin-bottom: 10px;
        padding: 10px;
        width: 100%;
        height: 100px;
        resize: none;
        border: 1px lightgray solid;
        &::before {
          color: #c5c5c5;
        }
      }
      .btn-cancel {
        color: $gray;
        margin-right: 20px;
      }
      .btn-submit-reply {
        &:disabled {
          color: rgb(216, 216, 216);
        }
        color: dodgerblue;
      }
    }
  }
  .btn-reply-toggle {
    display: flex;
    align-items: center;
    color: dodgerblue;
    margin-bottom: 20px;
    &:hover {
      opacity: 0.7;
    }
    svg {
      transform: rotate(180deg);
      fill: dodgerblue;
      margin-right: 10px;
      &.upper {
        transform: rotate(0deg);
      }
    }
  }
}
</style>
