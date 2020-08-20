<template>
  <div class="level-icon">
    <template v-if="score < 100">🥚</template>
    <template v-else-if="score < 200">🌱</template>
    <template v-else-if="score < 300">🍃</template>
    <template v-else-if="score < 400">🍀</template>
    <template v-else-if="score < 500">🌼</template>
    <template v-else>🍍</template>
  </div>
</template>

<script>
export default {
  props: ["score"],
};
</script>

<style scoped lang="scss">
.level-icon {
  font-size: 20px;
  margin: 0;
}
</style>
