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
        <span class="create-date"> · createData가 안 넘어옴</span>
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
  </div>
</template>

<script>
import { Editor, EditorContent } from "tiptap";
import { mapGetters } from "vuex";
import axios from "axios";
import javascript from "highlight.js/lib/languages/javascript";
import css from "highlight.js/lib/languages/css";

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
    EditorContent
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
      title: "",
      createDate: "",
      tagList: [],
      profile: "",
      nickname: "",
      linkUrl: null,
      linkMenuIsActive: false,
      thumbnail: "",
      content: ""
    };
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    async getArticleData() {
      const articleId = this.$route.query.id;
      const email = this.getUserInfo().email;
      try {
        const res = await axios.get(
          `${this.$apiServer}/post/postDetail?email=${email}&id=${articleId}`
        );

        if (res.status === 200) {
          const articleData = res.data;
          //this.createDate = articleData.createdate.split("T")[0];
          this.title = articleData.title;
          this.content = articleData.content;
          this.editor.setContent(this.content);
          this.tagList = articleData.tag;
          this.profile = articleData.profile;
          this.nickname = articleData.nickname;
          this.thumbnail = articleData.picture;
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
            console.log(elem);
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
        console.log(elem.tagName);
        anchor.classList.add(elem.tagName);
        anchor.setAttribute("href", `#${elem.id}`);
        anchor.innerText = elem.innerText;
        navContent.appendChild(anchor);
      });
    },
    modifyAnchorDest() {
      window.addEventListener("hashchange", function() {
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

.container-article-view {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.viewer {
  text-align: left;
  width: 800px;
}

.title-line {
  display: flex;
  align-items: flex-start;
  color: #999999;
  justify-content: space-between;
  .btn-more {
    color: #999999;
    font-weight: 900;
  }
}

.view-title {
  width: 100%;
  border: none;
  font-weight: 700;
  background-color: white;
}

.article-info {
  display: flex;
  align-items: center;
  color: #999999;
  margin-bottom: 10px;
  .user-info {
    display: flex;
    align-items: center;
    margin-right: 0.3em;
    .img-profile {
      width: 20px;
      height: 20px;
      border-radius: 50%;
    }
    svg {
      width: 20px;
      height: 20px;
      fill: #999999;
    }
    span {
      margin-left: 10px;
      color: black;
    }
  }
}

.viewer-tags {
  margin-bottom: 20px;
  .btn-tag {
    color: dodgerblue;
    margin-right: 20px;
  }
}

.introduction {
  display: flex;
  justify-content: center;
  margin: 40px 0px;
  img {
    width: 600px;
    height: 400px;
  }
  .default-thumbnail {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    overflow: hidden;
    font-size: 3em;
    color: white;
    width: 600px;
    height: 400px;
  }
}

.article-nav {
  display: flex;
  justify-content: flex-end;
  width: 1100px;
  height: 10px;
  .article-nav-content {
    display: flex;
    flex-direction: column;
    width: 100px;
    height: 600px;
    font-size: 0.9em;
    span {
      font-weight: 900;
      margin-bottom: 20px;
    }
    a {
      color: #999999;
      font-weight: 100;
      &:hover {
        text-decoration: none;
        color: black;
      }
    }
    .H2 {
      margin-left: 15px;
    }
    .H3 {
      margin-left: 30px;
    }
  }
}

// svg sprite
body > svg,
.icon use > svg,
symbol {
  path,
  rect,
  circle,
  g {
    fill: currentColor;
    stroke: none;
  }

  *[d="M0 0h24v24H0z"] {
    display: none;
  }
}

// code highlight
pre {
  &::before {
    content: attr(data-language);
    text-transform: uppercase;
    display: block;
    text-align: right;
    font-weight: bold;
    font-size: 0.6rem;
  }

  code {
    .hljs-comment,
    .hljs-quote {
      color: #999999;
    }

    .hljs-variable,
    .hljs-template-variable,
    .hljs-attribute,
    .hljs-tag,
    .hljs-name,
    .hljs-regexp,
    .hljs-link,
    .hljs-name,
    .hljs-selector-id,
    .hljs-selector-class {
      color: #f2777a;
    }

    .hljs-number,
    .hljs-meta,
    .hljs-built_in,
    .hljs-builtin-name,
    .hljs-literal,
    .hljs-type,
    .hljs-params {
      color: #f99157;
    }

    .hljs-string,
    .hljs-symbol,
    .hljs-bullet {
      color: #99cc99;
    }

    .hljs-title,
    .hljs-section {
      color: #ffcc66;
    }

    .hljs-keyword,
    .hljs-selector-tag {
      color: #6699cc;
    }

    .hljs-emphasis {
      font-style: italic;
    }

    .hljs-strong {
      font-weight: 700;
    }
  }
}
</style>
