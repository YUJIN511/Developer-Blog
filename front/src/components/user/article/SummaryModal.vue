<template>
  <div class="summary-modal">
    <div class="overlay"></div>
    <div class="summary-content"></div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
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
  background-color: white;
  width: 300px;
  height: 500px;
}
</style>
