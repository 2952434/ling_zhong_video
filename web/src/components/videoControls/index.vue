<template>
  <div class='videoControls'></div>
</template>

<script setup>
import { ref, reactive } from 'vue'
</script>

<style scoped lang='scss'>
.videoControls {
  width: 100%;
  height: $video-contorls-height;
  background-color: red;
  position: absolute;
  z-index: 999;
  bottom: 0;
}
</style>