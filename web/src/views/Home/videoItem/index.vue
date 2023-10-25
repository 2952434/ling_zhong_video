<template>
  <div class='videoItem'>
    <VideoComponent :videoUrl="videoSrc" />
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
/**
 * 引入视频组件
 */
import VideoComponent from '@/components/videoComponent/index.vue'
/**
 * 引入视频地址解析函数
 */
import { getVideoUrl } from '@/utils/utils.js'
// 引入路径
const videoSrc = getVideoUrl('video.mp4')
</script>

<style scoped lang='scss'>
.videoItem {
  height: 100%;
}
</style>