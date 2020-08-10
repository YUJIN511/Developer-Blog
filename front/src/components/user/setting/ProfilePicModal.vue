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
        <button @click="clickInput" class="btn btn-upload">...사진 업로드</button>
      </div>
      <div class="modal-body">
        <hr />
        <div>
          <div
            class="previous-image"
            v-for="image in images"
            :key="image"
            @click="deleteImage(image)"
          >
            <img :src="image" />
            <button class="banner-image-delete">✖</button>
          </div>
        </div>
        <button @click="saveChanges">저장</button>
      </div>
      <!-- <button class="btn-close" @click="closeModal">✖</button> -->
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";

const SERVER_URL = "http://i3a604.p.ssafy.io:8081";

export default {
  name: "ProfilePicModal",
  data() {
    return {
      url: null,
      images: []
    };
  },
  methods: {
    ...mapGetters({
      getEmail: "user/getEmail",
      getProfile: "user/getProfile"
    }),
    closeModal() {
      document.querySelector(".container-profilepic").classList.add("hide");
    },
    previewFile(event) {
      this.file = event.target.files[0];
      this.url = URL.createObjectURL(this.file);
      document.querySelector(
        ".preview-image"
      ).style.backgroundImage = `url('${this.url}')`;
    },
    saveChanges() {
      let formData = new FormData();
      formData.append("filename", this.file);
      formData.append("email", this.email);

      axios
        .post(`${SERVER_URL}/api/auth/profile`, formData, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(() => {
          // console.log(res);
        })
        .catch(err => console.log(err));
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
    deleteImage(url) {
      console.log(url);
      axios
        .delete(`${SERVER_URL}/api/auth/delprofile`, {
          email: this.getEmail(),
          filePath: url
        })
        .then(res => {
          console.log(res);
        })
        .catch(err => console.log(err));
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
}

.modal-body {
  padding: 50px;
}

.preview-image {
  position: absolute;
  background-image: url(https://images.unsplash.com/photo-1517832207067-4db24a2ae47c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60);
  background-position: center;
  background-size: 150%;
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.previous-image {
  position: relative;
  background-position: center;
  background-size: 150%;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  img {
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    border-radius: 50%;
  }
  button {
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    border-radius: 50%;
    color: rgba(0, 0, 0, 0);
    &:hover {
      font-size: 2rem;
      font-weight: 0;
      color: rgba(255, 255, 255, 0.6);
      background-color: rgba(0, 0, 0, 0.6);
    }
  }
}

h1 {
  margin: 30px 0px;
}

.hide {
  display: none;
}

.btn {
  background-color: rgb(69, 181, 187);
}
.btn:hover {
  background-color: cadetblue;
}
</style>
