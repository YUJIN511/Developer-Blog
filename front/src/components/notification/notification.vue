<template>
  <div class="test-wrapper">
    <h2>Server Sent Events Test-View</h2>
  </div>
</template>

<script>
export default {
  name: "notification",
  data() {
    return {
      eventSource: null
    };
  },
  methods: {
    setupStream() {
      console.log("==> 이벤트 소스 수행");
      this.eventSource = new EventSource(
        "http://i3a604.p.ssafy.io:8081/api/notification/user/push?email=test@test.com",
        { withCredentials: true }
      );
      this.eventSource.onopen = function(e) {
        console.log("이벤트 소스 오픈");
        console.log(e);
      };
      this.eventSource.onmessage = function(e) {
        console.log("이벤트 소스 메시지 도착");
        console.log(e.data);
      };
      this.eventSource.onerror = function(e) {
        console.log("이벤트 소스 에러");
        console.log(e);
      };
    },
    unSetupStream() {
      if (this.eventSource === null) {
        return;
      }
      console.log("==> 이벤트 소스 종료");
      this.eventSource.close();
    }
  },
  created() {
    this.setupStream();
  },
  beforeDestroy() {
    this.unSetupStream();
  }
};
</script>

<style></style>
