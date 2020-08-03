<template>
  <div class="container-menu-bubble">
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
            <span>{{ isActive.link() ? "Update Link" : "Add Link" }}</span>
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
  </div>
</template>

<script>
import { Editor, EditorMenuBubble } from "tiptap";
import {
  Blockquote,
  BulletList,
  CodeBlock,
  HardBreak,
  Heading,
  ListItem,
  OrderedList,
  TodoItem,
  TodoList,
  Bold,
  Code,
  Italic,
  Link,
  History
} from "tiptap-extensions";

export default {
  components: {
    EditorMenuBubble
  },
  data() {
    return {
      editor: new Editor({
        extensions: [
          new Blockquote(),
          new BulletList(),
          new CodeBlock(),
          new HardBreak(),
          new Heading({ levels: [1, 2, 3] }),
          new ListItem(),
          new OrderedList(),
          new TodoItem(),
          new TodoList(),
          new Link(),
          new Bold(),
          new Code(),
          new Italic(),
          new History()
        ],
        content: `
          <h2>
            Links
          </h2>
          <p>
            Try to add some links to the <a href="https://en.wikipedia.org/wiki/World_Wide_Web">world wide web</a>. By default every link will get a <code>rel="noopener noreferrer nofollow"</code> attribute.
          </p>
        `
      }),
      linkUrl: null,
      linkMenuIsActive: false
    };
  },
  methods: {
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
  }
};
</script>

<style></style>
