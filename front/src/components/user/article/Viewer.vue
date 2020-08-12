<template>
  <div class="viewer">
    <header>
      <div class="title-line">
        <h1 type="text" class="view-title" placeholder="제목" readonly>
          {{ title }}
        </h1>
        <button class="btn-more">...</button>
      </div>
      <div class="article-info">
        <div class="user-info">
          <img class="img-profile" :src="profile" v-if="profile !== ''" />
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
          <span>{{ nickname }}</span>
        </div>
        <span class="create-date"> · {{ createDate }}</span>
      </div>

      <div class="viewer-tags">
        <button class="btn-tag" :key="idx" v-for="(tag, idx) in tagList">
          #{{ tag }}
        </button>
      </div>
      <div class="article-nav">
        <div ref="navContent" class="article-nav-content">
          <span>책갈피</span>
        </div>
      </div>
      <div class="introduction">
        <img :src="thumbnail" alt="" v-if="thumbnail !== ''" />
        <div ref="defaultThumbnail" class="default-thumbnail" v-else>
          {{ title }}
        </div>
      </div>
    </header>
    <editor-content class="editor__content" :editor="editor" />
    <div class="container-small-buttons">
      <div class="small-buttons">
        <button class="btn-like" @click="toggleLikeBtn">
          <svg
            class="icon-like"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
          >
            <path
              d="M12 4.248c-3.148-5.402-12-3.825-12 2.944 0 4.661 5.571 9.427 12 15.808 6.43-6.381 12-11.147 12-15.808 0-6.792-8.875-8.306-12-2.944z"
            />
          </svg>
          <span>{{ like }}</span>
        </button>
        <button class="btn-library">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            viewBox="0 0 26 26"
          >
            <g>
              <g>
                <path
                  d="M10.3,3.2v1.7c-1-0.4-2.1-0.6-3.2-0.6C4.9,4.3,2.7,5.1,1,6.8l4.1,4.1h1.4v1.4c1.1,1.1,2.4,1.6,3.8,1.7v2.8H6.6v3.7
			c0,1.4,1.1,2.5,2.5,2.5h12.3c2,0,3.7-1.6,3.7-3.7v-16H10.3z M8.9,11V8.4H6.1L4.8,7.1c0.7-0.3,1.5-0.4,2.2-0.4
			c1.6,0,3.2,0.6,4.3,1.8l1.7,1.7l-0.2,0.2c-0.6,0.6-1.5,1-2.4,1C10,11.5,9.4,11.3,8.9,11z M22.5,19.1c0,0.7-0.6,1.2-1.2,1.2
			c-0.7,0-1.2-0.6-1.2-1.2v-2.5h-7.4v-3.2c0.7-0.3,1.4-0.7,1.9-1.3l0.2-0.2l3.5,3.5h1.7v-1.7l-7.4-7.3V5.6h9.8
			C22.5,5.6,22.5,19.1,22.5,19.1z"
                />
              </g>
            </g>
          </svg>
        </button>
      </div>
    </div>
    <div class="container-blog-info">
      <div class="blog-image"></div>
      <div class="main-info">
        <img src="" alt="" />
        <div class="blog-title"></div>
        <div class="blog-description"></div>
        <div class="follower-number"></div>
      </div>
      <div class="container-btn-follow">
        <button class="btn-follow">
          팔로우
        </button>
      </div>
    </div>
    <Comment :postId="postId" />
  </div>
</template>

<script>
import { Editor, EditorContent } from "tiptap";
import { mapGetters } from "vuex";
import axios from "axios";
import javascript from "highlight.js/lib/languages/javascript";
import css from "highlight.js/lib/languages/css";
import Comment from "./CommentModule.vue";

import {
  CodeBlockHighlight,
  Blockquote,
  CodeBlock,
  HardBreak,
  Heading,
  HorizontalRule,
  OrderedList,
  BulletList,
  ListItem,
  TodoItem,
  TodoList,
  Bold,
  Code,
  Italic,
  Link,
  Strike,
  Underline,
  History,
  Image
} from "tiptap-extensions";
export default {
  components: {
    EditorContent,
    Comment
  },
  data() {
    return {
      editor: new Editor({
        editable: false,
        extensions: [
          new CodeBlockHighlight({
            languages: {
              javascript,
              css
            }
          }),
          new Blockquote(),
          new BulletList(),
          new CodeBlock(),
          new HardBreak(),
          new Heading({ levels: [1, 2, 3] }),
          new HorizontalRule(),
          new ListItem(),
          new OrderedList(),
          new TodoItem(),
          new TodoList(),
          new Link(),
          new Bold(),
          new Code(),
          new Italic(),
          new Strike(),
          new Underline(),
          new History(),
          new Image()
        ],

        content: "",
        onUpdate: ({ getHTML }) => {
          this.html = getHTML();
          console.log(this.html);
        }
      }),
      postId: "",
      title: "",
      createDate: "",
      tagList: [],
      profile: "",
      nickname: "",
      linkUrl: null,
      linkMenuIsActive: false,
      thumbnail: "",
      content: "",
      like: 0,
      isLike: false
    };
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    async getArticleData() {
      const articleId = (this.postId = this.$route.query.id);
      const email = this.getUserInfo().email;

      try {
        const res = await axios.get(
          `${this.$apiServer}/post/postDetail?email=${email}&id=${articleId}`
        );

        if (res.status === 200) {
          const articleData = res.data;
          this.createDate = articleData.createdate.split(" ")[0];
          this.title = articleData.title;
          this.content = articleData.content;
          this.editor.setContent(this.content);
          this.tagList = articleData.tag;
          this.profile = articleData.profile;
          this.nickname = articleData.nickname;
          this.thumbnail = articleData.picture;
          this.like = articleData.good;
          this.isLike = articleData.isgood;
          console.log(articleData.isgood);
          this.setLikeBtn();
        }
      } catch (error) {
        console.log(error);
      }
    },
    initNav() {
      const navContent = this.$refs.navContent;
      document.addEventListener("scroll", function() {
        const yOffset = window.pageYOffset;
        if (yOffset > 0) {
          navContent.setAttribute("style", "position: fixed; top: 340px;");
        } else {
          navContent.removeAttribute("style");
        }
      });
      this.setNavContent();
    },
    setNavContent() {
      let id = window.setInterval(() => {
        // 컨텐츠 불러오는 속도를 기다려줘야 함
        if (this.content !== "") {
          window.clearInterval(id);
          const hList = document.querySelectorAll(
            ".editor__content h1, h2, h3"
          );

          hList.forEach(elem => {
            elem.id = elem.innerText;
          });

          this.setNavAnchor(hList);
        }
      }, 300);
    },
    setNavAnchor(tagList) {
      const navContent = this.$refs.navContent;
      tagList.forEach(elem => {
        const anchor = document.createElement("a");
        anchor.classList.add(elem.tagName);
        anchor.setAttribute("href", `#${elem.id}`);
        anchor.innerText = elem.innerText;
        navContent.appendChild(anchor);
      });
    },
    modifyAnchorDest() {
      window.addEventListener("hashchange", function(e) {
        console.log(e.oldURL);
        console.log(e.newURL);
        window.scrollTo(window.scrollX, window.scrollY - 100);
      });
    },
    setDefaultThumbnail() {
      const thumbnail = this.$refs.defaultThumbnail;
      const R = Math.random() * 155 + 100;
      const G = Math.random() * 155 + 100;
      const B = Math.random() * 155 + 100;
      thumbnail.setAttribute(
        "style",
        `background-color: rgb(${R}, ${G}, ${B});`
      );
    },
    setLikeBtn() {
      const likeIcon = document.querySelector(".icon-like");
      if (this.isLike) {
        likeIcon.classList.add("fill-blue");
      } else {
        likeIcon.classList.remove("fill-blue");
      }
    },
    toggleLikeBtn() {
      // isGood이 갱신이 안 되는거 같음
      this.isLike = !this.isLike;
      if (this.isLike) {
        axios.put(
          `${this.$apiServer}/post/good?email=${this.getUserInfo().email}&id=${
            this.$route.query.id
          }`,
          {}
        );
        this.like++;
      } else {
        axios.put(
          `${this.$apiServer}/post/ungood?email=${
            this.getUserInfo().email
          }&id=${this.$route.query.id}`
        );
        this.like--;
      }
      this.setLikeBtn();
    }
  },
  beforeDestroy() {
    this.editor.destroy();
  },

  mounted() {
    this.setDefaultThumbnail();
    this.modifyAnchorDest();
    this.initNav();
  },
  created() {
    this.getArticleData();
  }
};
</script>

<style lang="scss" scope>
@import "@/assets/sass/main.scss";
@import "@/assets/sass/viewer.scss";
</style>
