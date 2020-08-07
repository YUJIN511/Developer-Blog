<template>
  <div class="editor">
    <ImageModal ref="ytmodal" @onConfirm="addCommand" />
    <SummaryModal ref="smodal" />
    <input
      type="text"
      :maxlength="20"
      v-model="title"
      class="title"
      placeholder="제목"
    />
    <div class="container-tags">
      <input
        type="text"
        v-model="tagName"
        class="input-tags"
        placeholder="태그명 + Enter"
        @keydown="makeTags"
      />
    </div>
    <editor-menu-bar :editor="editor" v-slot="{ commands, isActive }">
      <div class="menubar">
        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.bold() }"
          @click="commands.bold"
        >
          <img class="icon" src="@/assets/images/icons/bold.svg" alt />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.italic() }"
          @click="commands.italic"
        >
          <img class="icon" src="@/assets/images/icons/italic.svg" alt />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.strike() }"
          @click="commands.strike"
        >
          <img class="icon" src="@/assets/images/icons/strike.svg" alt />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.underline() }"
          @click="commands.underline"
        >
          <img class="icon" src="@/assets/images/icons/underline.svg" alt />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.code() }"
          @click="commands.code"
        >
          <img class="icon" src="@/assets/images/icons/code.svg" alt />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.paragraph() }"
          @click="commands.paragraph"
        >
          <img class="icon" src="@/assets/images/icons/paragraph.svg" alt />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.heading({ level: 1 }) }"
          @click="commands.heading({ level: 1 })"
        >
          H1
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.heading({ level: 2 }) }"
          @click="commands.heading({ level: 2 })"
        >
          H2
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.heading({ level: 3 }) }"
          @click="commands.heading({ level: 3 })"
        >
          H3
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.bullet_list() }"
          @click="commands.bullet_list"
        >
          <img class="icon" src="@/assets/images/icons/ul.svg" alt />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.ordered_list() }"
          @click="commands.ordered_list"
        >
          <img class="icon" src="@/assets/images/icons/ol.svg" alt />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.blockquote() }"
          @click="commands.blockquote"
        >
          <img class="icon" src="@/assets/images/icons/quote.svg" alt />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.code_block() }"
          @click="commands.code_block"
        >
          <img class="icon" src="@/assets/images/icons/code.svg" alt />
        </button>

        <button class="menubar__button" @click="commands.horizontal_rule">
          <img class="icon" src="@/assets/images/icons/hr.svg" alt />
        </button>

        <button class="menubar__button" @click="commands.undo">
          <img class="icon" src="@/assets/images/icons/undo.svg" alt />
        </button>

        <button class="menubar__button" @click="commands.redo">
          <img class="icon" src="@/assets/images/icons/redo.svg" alt />
        </button>

        <button class="menubar__button" @click="openImgModal(commands.image)">
          <img class="icon" src="@/assets/images/icons/image.svg" alt />
        </button>
      </div>
    </editor-menu-bar>

    <editor-menu-bubble
      class="menububble"
      :editor="editor"
      @hide="hideLinkMenu"
      v-slot="{ commands, isActive, getMarkAttrs, menu }"
    >
      <div
        class="menububble"
        :class="{ 'is-active': menu.isActive }"
        :style="`left: ${menu.left}px; bottom: ${menu.bottom}px;`"
      >
        <form
          class="menububble__form"
          v-if="linkMenuIsActive"
          @submit.prevent="setLinkUrl(commands.link, linkUrl)"
        >
          <input
            class="menububble__input"
            type="text"
            v-model="linkUrl"
            placeholder="https://"
            ref="linkInput"
            @keydown.esc="hideLinkMenu"
          />
          <button
            class="menububble__button"
            @click="setLinkUrl(commands.link, null)"
            type="button"
          >
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
              <title>delete-2-alternate</title>
              <path
                d="M20.485,3.511A12.01,12.01,0,1,0,24,12,12.009,12.009,0,0,0,20.485,3.511Zm-1.767,15.21A9.51,9.51,0,1,1,21.5,12,9.508,9.508,0,0,1,18.718,18.721Z"
              />
              <path
                d="M16.987,7.01a1.275,1.275,0,0,0-1.8,0l-3.177,3.177L8.829,7.01A1.277,1.277,0,0,0,7.024,8.816L10.2,11.993,7.024,15.171a1.277,1.277,0,0,0,1.805,1.806L12.005,13.8l3.177,3.178a1.277,1.277,0,0,0,1.8-1.806l-3.176-3.178,3.176-3.177A1.278,1.278,0,0,0,16.987,7.01Z"
              />
            </svg>
          </button>
        </form>

        <template v-else>
          <button
            class="menububble__button"
            @click="showLinkMenu(getMarkAttrs('link'))"
            :class="{ 'is-active': isActive.link() }"
          >
            <span>
              {{ isActive.link() ? "링크 수정하기" : "링크 추가하기" }}
            </span>
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
              <title>hyperlink-2</title>
              <path
                d="M12.406,14.905a1,1,0,0,0-.543,1.307,1,1,0,0,1-.217,1.09L8.818,20.131a2,2,0,0,1-2.828,0L3.868,18.01a2,2,0,0,1,0-2.829L6.7,12.353a1.013,1.013,0,0,1,1.091-.217,1,1,0,0,0,.763-1.849,3.034,3.034,0,0,0-3.268.652L2.454,13.767a4.006,4.006,0,0,0,0,5.657l2.122,2.121a4,4,0,0,0,5.656,0l2.829-2.828a3.008,3.008,0,0,0,.651-3.27A1,1,0,0,0,12.406,14.905Z"
              />
              <path
                d="M7.757,16.241a1.011,1.011,0,0,0,1.414,0L16.95,8.463a1,1,0,0,0-1.414-1.414L7.757,14.827A1,1,0,0,0,7.757,16.241Z"
              />
              <path
                d="M21.546,4.574,19.425,2.453a4.006,4.006,0,0,0-5.657,0L10.939,5.281a3.006,3.006,0,0,0-.651,3.269,1,1,0,1,0,1.849-.764A1,1,0,0,1,12.354,6.7l2.828-2.828a2,2,0,0,1,2.829,0l2.121,2.121a2,2,0,0,1,0,2.829L17.3,11.645a1.015,1.015,0,0,1-1.091.217,1,1,0,0,0-.765,1.849,3.026,3.026,0,0,0,3.27-.651l2.828-2.828A4.007,4.007,0,0,0,21.546,4.574Z"
              />
            </svg>
          </button>
        </template>
      </div>
    </editor-menu-bubble>

    <editor-content class="editor__content" :editor="editor" />
  </div>
</template>

<script>
import { Editor, EditorContent, EditorMenuBar, EditorMenuBubble } from "tiptap";
import javascript from "highlight.js/lib/languages/javascript";
import css from "highlight.js/lib/languages/css";
import ImageModal from "./ImageModal";
import SummaryModal from "./SummaryModal";
import { mapGetters } from "vuex";

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
    EditorMenuBar,
    EditorMenuBubble,
    ImageModal,
    SummaryModal
  },
  data() {
    return {
      title: "",
      tagName: "",
      tagList: [],
      linkUrl: null,
      linkMenuIsActive: false,
      editor: new Editor({
        autoFocus: true,
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

        content: `
          <h2>
            Hi there,
          </h2>
          <p>
            this is a very <em>basic</em> example of tiptap.
          </p>
          <pre><code>body { display: none; }</code></pre>
          <ul>
            <li>
              A regular list
            </li>
            <li>
              With regular items
            </li>
          </ul>
          <blockquote>
            It's amazing 👏
            <br />
            – mom
          </blockquote>
        `,
        onUpdate: ({ getHTML }) => {
          this.html = getHTML();
        }
      }),
      html: ""
    };
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    makeTags(e) {
      const curKey = e.key;
      if (curKey === "Enter") {
        if (this.tagList.includes(this.tagName)) {
          this.tagName = "";
          return;
        }

        const removeBtn = document.createElement("button");
        removeBtn.addEventListener("click", e => {
          const selectedTag = e.currentTarget.parentElement;
          const tagName = selectedTag.querySelector("span").innerText;
          const idx = this.tagList.indexOf(tagName);
          this.tagList.splice(idx, 1);
          console.dir(this.tagList);
          selectedTag.remove();
        });
        removeBtn.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><title>delete-2-alternate</title><path d="M20.485,3.511A12.01,12.01,0,1,0,24,12,12.009,12.009,0,0,0,20.485,3.511Zm-1.767,15.21A9.51,9.51,0,1,1,21.5,12,9.508,9.508,0,0,1,18.718,18.721Z"/><path d="M16.987,7.01a1.275,1.275,0,0,0-1.8,0l-3.177,3.177L8.829,7.01A1.277,1.277,0,0,0,7.024,8.816L10.2,11.993,7.024,15.171a1.277,1.277,0,0,0,1.805,1.806L12.005,13.8l3.177,3.178a1.277,1.277,0,0,0,1.8-1.806l-3.176-3.178,3.176-3.177A1.278,1.278,0,0,0,16.987,7.01Z"/></svg>
`;
        const span = document.createElement("span");
        span.innerText = this.tagName;
        this.tagList.push(this.tagName);
        this.tagName = "";

        const newTag = document.createElement("div");
        newTag.appendChild(span);
        newTag.appendChild(removeBtn);

        const container = document.querySelector(".container-tags");
        container.insertBefore(newTag, e.path[0]);
      }
    },
    openImgModal(command) {
      this.$refs.ytmodal.showModal(command);
    },
    openSummaryModal() {
      let tagString = "";
      this.tagList.forEach(elem => {
        tagString += `tag=${elem}&`;
      });
      const articleData = {
        title: this.title,
        tagString,
        content: this.html,
        writer: this.getUserInfo().email,
        summary: "",
        picture: ""
      };
      this.$refs.smodal.showModal(articleData);
    },
    addCommand(data) {
      if (data.command !== null) {
        data.data.forEach(elem => {
          data.command(elem);
        });
      }
    },
    showLinkMenu(attrs) {
      this.linkUrl = attrs.href;
      this.linkMenuIsActive = true;
      this.$nextTick(() => {
        this.$refs.linkInput.focus();
      });
    },
    hideLinkMenu() {
      this.linkUrl = null;
      this.linkMenuIsActive = false;
    },
    setLinkUrl(command, url) {
      command({ href: url });
      this.hideLinkMenu();
    }
  },
  beforeDestroy() {
    this.editor.destroy();
  },
  mounted() {
    const menubar = document.querySelector(".menubar");

    window.addEventListener("scroll", () => {
      const scrollY = window.scrollY;
      if (scrollY > 0) {
        menubar.classList.add("stick-top");
      } else {
        menubar.classList.remove("stick-top");
      }
    });
  }
};
</script>

<style lang="scss" scope>
@import "@/assets/sass/main.scss";

.editor {
  text-align: left;
}

input {
  caret-color: #999999;
}

.title {
  width: 100%;
  border: none;
  font-size: 2em;
  font-weight: 700;
  border-bottom: 3px solid #f1f4f7;
  padding: 10px;
  padding-bottom: 20px;
  margin-bottom: 10px;
}

.container-tags {
  display: flex;
  flex-wrap: wrap;

  width: 100%;
  div {
    display: flex;
    align-items: center;
    height: 1.8em;
    background-color: darkcyan;
    border-radius: 15px;
    padding: 0px 10px;
    margin-right: 10px;
    margin-bottom: 10px;
    color: white;
    button {
      display: flex;
      align-items: center;
      justify-content: center;
      margin-left: 9px;
      margin-top: 2px;
    }
    svg {
      width: 16px;
      height: 16px;
      fill: rgb(230, 230, 230);
    }
  }
}

.input-tags {
  width: 100%;
  height: 2em;
  border-radius: 5px;
  padding: 0px 10px;
  margin-bottom: 20px;
  border: none;
  background-color: $bgColor;
}

// icon design

.icon {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  width: 0.8rem;
  height: 0.8rem;
  margin: 0 0.3rem;
  top: -0.05rem;
  fill: currentColor;

  // &.has-align-fix {
  // 	top: -.1rem;
  // }

  &__svg {
    display: inline-block;
    vertical-align: top;
    width: 100%;
    height: 100%;
  }

  &:first-child {
    margin-left: 0;
  }

  &:last-child {
    margin-right: 0;
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
