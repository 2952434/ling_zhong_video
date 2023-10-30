<template>
  <div class='videoItem'>
    <VideoComponent />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
/**
 * 引入视频组件
 */
import VideoComponent from '@/components/videoComponent/index.vue'

onMounted(async () => {

})
</script>

<style scoped lang='scss'>
.videoItem {
  height: 100%;
}
</style>