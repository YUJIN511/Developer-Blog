<template>
  <div class="summary-modal">
    <div class="overlay"></div>
    <div class="summary-content">
      <h4>글 미리보기</h4>
      <main>
        <button class="thumbnail">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
            <title>paginate-filter-picture-alternate</title>
            <circle cx="9.75" cy="6.247" r="2.25" />
            <path
              d="M16.916,8.71A1.027,1.027,0,0,0,16,8.158a1.007,1.007,0,0,0-.892.586L13.55,12.178a.249.249,0,0,1-.422.053l-.82-1.024a1,1,0,0,0-.813-.376,1.007,1.007,0,0,0-.787.426L7.59,15.71A.5.5,0,0,0,8,16.5H20a.5.5,0,0,0,.425-.237.5.5,0,0,0,.022-.486Z"
            />
            <path
              d="M22,0H5.5a2,2,0,0,0-2,2V18.5a2,2,0,0,0,2,2H22a2,2,0,0,0,2-2V2A2,2,0,0,0,22,0Zm-.145,18.354a.5.5,0,0,1-.354.146H6a.5.5,0,0,1-.5-.5V2.5A.5.5,0,0,1,6,2H21.5a.5.5,0,0,1,.5.5V18A.5.5,0,0,1,21.855,18.351Z"
            />
            <path
              d="M19.5,22H2.5a.5.5,0,0,1-.5-.5V4.5a1,1,0,0,0-2,0V22a2,2,0,0,0,2,2H19.5a1,1,0,0,0,0-2Z"
            />
          </svg>
          <img :src="thumbSrc" alt class="thumb-img" />
          <button class="btn-thumbnail">
            <input type="file" ref="myFiles" @change="getThumbImage" />
          </button>
        </button>
        <h5>제목</h5>
        <div class="content-article">내용</div>
      </main>
    </div>
  </div>
</template>

<script>
export default {
  data: function() {
    return {
      thumbSrc: ""
    };
  },
  watch: {
    thumbImage: function(newData) {
      this.thumbImage = newData;
      console.log(this.thumbImage);
    }
  },
  methods: {
    getThumbImage() {
      const file = this.$refs.myFiles.files;
      console.dir(file);
      if (FileReader && file && file.length) {
        const fr = new FileReader();
        fr.onload = function() {
          document.querySelector(".thumb-img").src = fr.result;
        };
        fr.readAsDataURL(file[0]);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/_variables.scss";

.summary-modal {
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  min-width: 100vw;
  min-height: 100vh;
  z-index: 10000;
}

.overlay {
  position: absolute;
  background-color: rgba(0, 0, 0, 0.2);
  min-width: 100vw;
  min-height: 100vh;
}

.summary-content {
  position: absolute;
  background-color: $bgColor;
  width: 350px;
  height: 500px;
  padding: 15px 25px;
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  border-radius: 5px;
}

main {
  display: flex;
  flex-direction: column;
  align-items: center;
}

h4 {
  font-weight: 900;
  margin-bottom: 15px;
}

.thumbnail {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 300px;
  height: 180px;
  margin-bottom: 30px;
  background-color: rgba(216, 216, 216, 0.7);

  svg {
    display: none;
    width: 120px;
    height: 120px;
    fill: rgb(247, 247, 247);
  }

  img {
    width: 300px;
    height: 180px;
  }
}

.btn-thumbnail {
  position: absolute;
  width: 300px;
  height: 180px;
  background-color: black;
  opacity: 0;
  color: white;
  font-size: 2em;
  font-weight: 900;
  &:hover {
    opacity: 0.5;
  }
  input {
    width: 100%;
    height: 100%;
    cursor: pointer;
    opacity: 0;
  }
}
h5 {
  width: 100%;
  font-weight: 700;
  text-align: left;
  margin-bottom: 10px;
}
.content-article {
  width: 100%;
  height: 160px;
  padding: 10px;
  text-align: left;
  background-color: white;
}
</style>
