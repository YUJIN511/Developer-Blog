<template>
  <div class="container__about">
    <h1>팀원 소개</h1>
    <div class="about__block">
      <div class="element__people">
        <img
          src="https://images.unsplash.com/photo-1580048915913-4f8f5cb481c4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
          alt=""
        />
        <div class="people__userinfo">
          <span class="userinfo__name">고유진</span>
          <span class="userinfo__role">역할</span>
          <hr />
          <span class="userinfo__description"
            >Lorem ipsum dolor, sit amet consectetur adipisicing elit. Nihil
            veritatis incidunt laboriosam
          </span>
        </div>
      </div>
      <div class="element__people">
        <img
          src="https://images.unsplash.com/photo-1580048915913-4f8f5cb481c4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
          alt=""
        />
        <div class="people__userinfo">
          <span class="userinfo__name">김나연</span>
          <span class="userinfo__role">역할</span>
          <hr />
          <span class="userinfo__description"
            >Lorem ipsum dolor, sit amet consectetur adipisicing elit. Nihil
            veritatis incidunt laboriosam
          </span>
        </div>
      </div>
      <div class="element__people">
        <img
          src="https://images.unsplash.com/photo-1580048915913-4f8f5cb481c4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
          alt=""
        />
        <div class="people__userinfo">
          <span class="userinfo__name">김현대</span>
          <span class="userinfo__role">역할</span>
          <hr />
          <span class="userinfo__description"
            >Lorem ipsum dolor, sit amet consectetur adipisicing elit. Nihil
            veritatis incidunt laboriosam
          </span>
        </div>
      </div>
      <div class="element__people">
        <img
          src="https://images.unsplash.com/photo-1580048915913-4f8f5cb481c4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
          alt=""
        />
        <div class="people__userinfo">
          <span class="userinfo__name">오재민</span>
          <span class="userinfo__role">역할</span>
          <hr />
          <span class="userinfo__description"
            >Lorem ipsum dolor, sit amet consectetur adipisicing elit. Nihil
            veritatis incidunt laboriosam
          </span>
        </div>
      </div>
      <div class="element__people">
        <img
          src="https://images.unsplash.com/photo-1580048915913-4f8f5cb481c4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
          alt=""
        />
        <div class="people__userinfo">
          <span class="userinfo__name">정무영</span>
          <span class="userinfo__role">역할</span>
          <hr />
          <span class="userinfo__description"
            >Lorem ipsum dolor, sit amet consectetur adipisicing elit. Nihil
            veritatis incidunt laboriosam
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.container__about {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  flex-wrap: wrap;
  background-color: #fafafa;
  min-width: 100vw;
  min-height: 100vh;
  padding-top: 56px;
  h1 {
    margin: 50px 0px;
  }
  .about__block {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    width: 100%;
    height: 500px;
    .element__people {
      display: flex;
      background-color: white;
      align-items: center;
      width: 450px;
      height: 150px;
      border-radius: 10px;
      padding: 15px;
      margin: 20px;
      box-shadow: 0px 3px 5px rgba(0, 0, 0, 0.19), 0 1px 1px rgba(0, 0, 0, 0.23);
      img {
        width: 110px;
        height: 110px;
        object-fit: cover;
        border-radius: 50%;
        margin-right: 20px;
      }
      .people__userinfo {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        .userinfo__name {
          font-size: 1.1em;
          margin-bottom: 5px;
          font-weight: 900;
        }
        hr {
          border: 0;
          clear: both;
          display: block;
          width: 100%;
          background-color: #ebebeb;
          height: 1px;
        }
        .userinfo__role {
          color: rgb(177, 177, 177);
          font-weight: 500;
        }
        .userinfo__description {
          color: rgb(177, 177, 177);
          text-align: left;
        }
      }
    }
  }
}
</style>
