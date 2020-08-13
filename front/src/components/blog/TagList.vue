<template>
  <div class="container-tags">
    <p class="list-title">TAG</p>
    <hr />
    <div class="list-items">
      <p class="list-item">
        <span @click="selectAll()">전체보기</span>
        <span>({{ totalcnt }})</span>
      </p>
      <p class="list-item" v-for="tag in tagData" :key="tag.id" @click="selectTag(tag[0])">
        <span>{{ tag[0] }}</span>
        <span>({{ tag[1] }})</span>
      </p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  name: "TagList",
  props: {
    email: String
  },
  data() {
    return {
      tagData: [],
      totalcnt: null
    };
  },
  methods: {
    fetchTags() {
      axios
        .get(`${SERVER_URL}/api/post/mycategory/${this.email}`)
        .then(res => {
          this.tagData = res.data;
        })
        .catch(err => console.log(err));
    },
    fetchTotalCnt() {
      axios
        .get(`${SERVER_URL}/api/post/mycategory/totcnt?email=${this.email}`)
        .then(res => {
          this.totalcnt = res.data;
        })
        .catch(err => console.log(err));
    },
    selectTag(tag) {
      this.$emit("select-tag", tag);
    },
    selectAll() {
      this.$emit("select-all");
    }
  },
  mounted() {
    this.fetchTags();
    this.fetchTotalCnt();
  }
};
</script>

<style scoped lang="scss">
.container-tags {
  width: 180px;
  margin-top: 280px;
  margin-left: 150px;
  margin-right: 0;
  color: #727272;
  float: left;
  .list-title {
    margin: 12px 0px;
  }
  hr {
    width: 180px;
    margin: 0px;
  }
  .list-items {
    margin: 12px 0px;
  }
  p {
    text-align: left;
    margin: 5px 0px;
  }
}
</style>
