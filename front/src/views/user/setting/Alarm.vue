<template>
  <div class="container-alarm">
    <div class="item">
      <span class="title">내 글 좋아요</span>
      <form action="">
        <div class="container-radio">
          <input id="none" type="radio" name="article-like" /><label for="none"
            >해제</label
          >
        </div>
        <div class="container-radio">
          <input id="follow-only" type="radio" name="article-like" /><label
            for="follow-only"
            >내가 팔로우 하는 사람</label
          >
        </div>
        <div class="container-radio">
          <input id="all" type="radio" name="article-like" /><label for="all"
            >모든 사람</label
          >
        </div>
      </form>
      <span class="example">" "님이 회원님의 게시글을 좋아합니다.</span>
    </div>
    <div class="item">
      <span class="title">내 글 좋아요</span>
      <form action="">
        <div class="container-radio">
          <input id="none" type="radio" name="article-like" /><label for="none"
            >해제</label
          >
        </div>
        <div class="container-radio">
          <input id="follow-only" type="radio" name="article-like" /><label
            for="follow-only"
            >내가 팔로우 하는 사람</label
          >
        </div>
        <div class="container-radio">
          <input id="all" type="radio" name="article-like" /><label for="all"
            >모든 사람</label
          >
        </div>
      </form>
      <span class="example">" "님이 회원님의 게시글을 좋아합니다.</span>
    </div>
    <div class="item">
      <span class="title">내 글 좋아요</span>
      <form action="">
        <div class="container-radio">
          <input id="none" type="radio" name="article-like" /><label for="none"
            >해제</label
          >
        </div>
        <div class="container-radio">
          <input id="follow-only" type="radio" name="article-like" /><label
            for="follow-only"
            >내가 팔로우 하는 사람</label
          >
        </div>
        <div class="container-radio">
          <input id="all" type="radio" name="article-like" /><label for="all"
            >모든 사람</label
          >
        </div>
      </form>
      <span class="example">" "님이 회원님의 게시글을 좋아합니다.</span>
    </div>
    <div class="item">
      <span class="title">내 글 좋아요</span>
      <form action="">
        <div class="container-radio">
          <input id="none" type="radio" name="article-like" /><label for="none"
            >해제</label
          >
        </div>
        <div class="container-radio">
          <input id="follow-only" type="radio" name="article-like" /><label
            for="follow-only"
            >내가 팔로우 하는 사람</label
          >
        </div>
        <div class="container-radio">
          <input id="all" type="radio" name="article-like" /><label for="all"
            >모든 사람</label
          >
        </div>
      </form>
      <span class="example">" "님이 회원님의 게시글을 좋아합니다.</span>
    </div>
    <div class="item">
      <span class="title">내 글 좋아요</span>
      <form action="">
        <div class="container-radio">
          <input id="none" type="radio" name="article-like" /><label for="none"
            >해제</label
          >
        </div>
        <div class="container-radio">
          <input id="follow-only" type="radio" name="article-like" /><label
            for="follow-only"
            >내가 팔로우 하는 사람</label
          >
        </div>
        <div class="container-radio">
          <input id="all" type="radio" name="article-like" /><label for="all"
            >모든 사람</label
          >
        </div>
      </form>
      <span class="example">" "님이 회원님의 게시글을 좋아합니다.</span>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.container-alarm {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  padding: 30px 60px;
}
.item {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  padding-bottom: 15px;
  margin-bottom: 25px;
  border-bottom: 1px solid rgb(201, 201, 201);
}
.title {
  font-size: 1.5em;
  font-weight: 900;
  margin-bottom: 20px;
}
.example {
  color: rgb(163, 163, 163);
  font-size: 0.8em;
}
input[type="radio"] {
  margin-right: 10px;
}
form {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
</style>
