<template>
  <div class="editor">
    <input
      type="text"
      v-model="title"
      class="title"
      placeholder="제목"
      readonly
    />
    <editor-content class="editor__content" :editor="editor" />
  </div>
</template>

<script>
import { Editor, EditorContent } from "tiptap";
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

        content: ""
      }),
      articleId: 0,
      title: "",
      linkUrl: null,
      linkMenuIsActive: false
    };
  },
  methods: {
    async getArticleData() {
      const articleId = this.$route.query.articleId;
      console.log(articleId);
      try {
        const res = await axios.get(`${this.$apiServer}/post/${articleId}`);

        if (res.status === 200) {
          const articleData = res.data;
          this.title = articleData.title;
          console.dir(articleData.content);
          this.editor.setContent(articleData.content);
          console.log("done");
        }
      } catch (error) {
        console.log(error);
      }
    }
  },
  beforeDestroy() {
    this.editor.destroy();
  },
  created() {
    this.getArticleData();
  },
  mounted() {
    //console.log(this.$route.params.articleId);
  }
};
</script>

<style lang="scss" scope>
.editor {
  text-align: left;
}

.title {
  width: 100%;
  border: none;
  font-size: 2em;
  font-weight: 700;
  border-bottom: 3px solid #f1f4f7;
  padding: 10px;
  padding-bottom: 20px;
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
