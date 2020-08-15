<template>
  <div class="container-profilepic hide">
    <div class="background-profilepic" @click="closeModal"></div>
    <div class="modal-profilepic">
      <div class="modal-head">
        <div class="preview-image"></div>
        <input
          type="file"
          id="file"
          style="display: none;"
          accept="image/png, image/jpeg"
          @change="previewFile"
        />
        <button @click="clickInput" class="btn btn-upload">사진 업로드</button>
        <button @click="setDefaultImage" class="btn btn-setdefault">기본 프로필 이미지로 변경</button>
      </div>
      <div class="modal-body">
        <hr />
        <div class="container-images">
          <div
            class="previous-image"
            v-for="(image, i) in images"
            :key="image"
            @click="selectImage(image)"
            @mouseover="showDeleteButton(i)"
            @mouseout="hideDeleteButton(i)"
          >
            <div class="banner-image-delete" @click="deleteImage(image)">✖</div>
            <img :src="image" />
            <button class="banner-image-select"></button>
          </div>
        </div>
      </div>
      <div class="container-btns">
        <button class="btn btn-save" @click="saveChanges">저장</button>
        <button class="btn btn-close" @click="closeModal">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import axios from "axios";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  name: "ProfilePicModal",
  data() {
    return {
      url: this.getProfile(),
      images: [],
      file: ""
    };
  },
  methods: {
    ...mapActions({
      defaultProfile: "user/defaultProfile"
    }),
    ...mapGetters({
      getEmail: "user/getEmail",
      getProfile: "user/getProfile"
    }),
    closeModal() {
      document.querySelector(".container-profilepic").classList.add("hide");
    },
    previewFile(event) {
      this.file = event.target.files[0];
      console.log(this.file);
      this.url = URL.createObjectURL(this.file);
      document.querySelector(
        ".preview-image"
      ).style.backgroundImage = `url('${this.url}')`;
    },
    saveChanges() {
      let formData = new FormData();
      formData.append("filename", this.file);
      formData.append("email", this.getEmail());
      axios
        .post(`${SERVER_URL}/api/auth/profile`, formData, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(() => {})
        .catch(err => console.log(err));
      this.$router.go();
    },
    clickInput() {
      document.querySelector("#file").click();
    },
    fetchPictures() {
      axios
        .get(`${SERVER_URL}/api/auth/pflist?email=${this.getEmail()}`)
        .then(res => {
          this.images = res.data;
        })
        .catch(err => console.log(err));
    },
    setDefaultImage() {
      let defaultImage = "http://i3a604.p.ssafy.io/images/profile/basic.png";
      this.$el.querySelector(
        ".preview-image"
      ).style.backgroundImage = `url('${defaultImage}')`;
      this.defaultProfile();
    },
    deleteImage(url) {
      axios
        .delete(
          `${SERVER_URL}/api/auth/delprofile?email=${this.getEmail()}&filePath=${url}`
        )
        .then(() => {
          this.fetchPictures();
        })
        .catch(err => console.log(err));
    },
    selectImage(url) {
      document.querySelector(
        ".preview-image"
      ).style.backgroundImage = `url('${url}')`;
      this.createFile(url).then(() => console.log(this.file));
    },
    async createFile(url) {
      await fetch(url)
        .then(response => {
          let data = response.blob();
          let metadata = {
            type: "image/jpeg"
          };
          this.file = new File([data], "profile.jpg", metadata);
        })
        .catch(err => console.log(err));
      // let response = await fetch(url);
      // let data = await response.blob();
      // let metadata = {
      //   type: "image/jpeg"
      // };
      // this.file = new File([data], "test.jpg", metadata);
      // ... do something with the file or return it
    },
    showDeleteButton(i) {
      //       color: rgb(0, 0, 0);
      // background-color: rgba(255, 255, 255);
      document.querySelectorAll(".banner-image-delete")[
        i
      ].style.backgroundColor = "rgb(255, 255, 255)";
      document.querySelectorAll(".banner-image-delete")[i].style.color =
        "rgb(0, 0, 0)";
    },
    hideDeleteButton(i) {
      document.querySelectorAll(".banner-image-delete")[
        i
      ].style.backgroundColor = "rgba(255, 255, 255, 0)";
      document.querySelectorAll(".banner-image-delete")[i].style.color =
        "rgba(0, 0, 0, 0)";
    }
  },
  mounted() {
    this.fetchPictures();
    this.$el.querySelector(
      ".preview-image"
    ).style.backgroundImage = `url('${this.getProfile()}')`;
  }
};
</script>

<style scoped lang="scss">
.container-profilepic {
  position: fixed;
  z-index: 5;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0);
}

.background-profilepic {
  position: fixed;
  width: 100vw;
  height: 100vh;
}

// modal

.modal-profilepic {
  position: fixed;
  width: 700px;
  height: 520px;
  background-color: rgb(240, 240, 240);
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  border-radius: 3%;
  display: flex;
  flex-direction: column;
}

.modal-head {
  padding: 50px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: flex-end;
  .btn {
    height: 30px;
    background-color: transparent;
    font-size: 1em;
    font-weight: 600;
    color: #1a7cff;
  }
}

.modal-body {
  padding: 0px 50px;
  position: relative;
  display: block;
  .button {
    position: absolute;
    bottom: 0;
  }
}

.preview-image {
  background-image: url(https://images.unsplash.com/photo-1517832207067-4db24a2ae47c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60);
  background-position: center;
  background-size: 150%;
  width: 120px;
  height: 120px;
  border-radius: 50%;
  margin: 0;
}

.container-images {
  height: 200px;
}

.previous-image {
  position: relative;
  background-position: center;
  background-size: 150%;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin: 10px;
  display: inline-block;
  .banner-image-delete {
    position: absolute;
    top: 0;
    right: 0;
    width: 15px;
    height: 15px;
    border-radius: 50%;
    z-index: 1;
    text-align: center;
    font-size: small;
    color: rgb(0, 0, 0, 0);
    background-color: rgba(255, 255, 255, 0);
  }
  img {
    position: absolute;
    left: 0;
    top: 0;
    min-width: 50px;
    height: 100%;
    border-radius: 50%;
  }
  button {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    border-radius: 50%;
    color: rgba(0, 0, 0, 0);
    &:hover {
      font-size: 2rem;
      font-weight: 0;
      color: rgba(255, 255, 255, 0.6);
      background-color: rgba(0, 0, 0, 0.3);
    }
  }
}

.container-btns {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 24px;
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  .btn {
    width: 100%;
  }
}

h1 {
  margin: 30px 0px;
}

.hide {
  display: none;
}
</style>
