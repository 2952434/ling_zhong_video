<template>
  <div :style="{ width: props.width + '%', height: props.height + '%' }" class="videoItem" ref="videoBox">
    <video @contextmenu.prevent.stop="rewriteVideoClick" @click="videoClick" :src="props.videoUrl" ref="videoItem"
      autoplay></video>
    <VideoControls />
    <div class="click-menu" ref="menuList" v-show="menuShow">
      <ul class="menu-ul">
        <li v-for="(item, index) in MOUSE_CLICK_MENU_LIST" @click="menuListClick(item)" v-show="item.show">{{ item.label
        }}</li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
// 引入视频控件
import VideoControls from '@/components/videoControls/index.vue'
import { MOUSE_CLICK_MENU_LIST } from '@/config/config.js'
// 操作剪切板
import { copy } from '@/utils/utils.js'
// 获取菜单实例
const menuList = ref()
// 控制菜单显示和隐藏
const menuShow = ref(false)
// 获取video标签
const videoItem = ref()
onMounted(() => {
  keydownEvent()
})
/**
 * 获取到video盒子实例
 */
const videoBox = ref()
const props = defineProps({
  // 视频组件的宽度（百分比）
  width: {
    type: Number,
    default: 100,
  },
  // 视频组件的高度（百分比）
  height: {
    type: Number,
    default: 100,
  },
  // 视频路径，必传，传绝对地址
  videoUrl: {
    type: String,
    require: true
  }
})
/**
 * 为视频组件重写右键点击事件
 */
const rewriteVideoClick = (e) => {
  const X = e.offsetX
  const Y = e.offsetY
  // 切换位置
  menuShow.value = true
  menuList.value.style.left = X + 'px'
  menuList.value.style.top = Y + 'px'
}
/**
 * video的点击
 */
const videoClick = () => {
  menuShow.value = false
  if (videoItem.value.paused) {
    videoItem.value.play()

  } else {
    videoItem.value.pause()
  }
}
/**
 * 右击列表点击事件
 */
const menuListClick = (item) => {
  menuShow.value = false
  switch (item.value) {
    case 'stop':
      videoItem.value.pause()
      break
    case 'play':
      videoItem.value.play()
      break
    case 'copy':
      copy(props.videoUrl)
      break
  }
}
/**
 * 键盘事件
 */
const keydownEvent = (e) => {
  document.body.addEventListener('keyup', (e) => {
    console.log(e.code);
    if (e.code === 'Space') {
      if (videoItem.value.paused) {
        videoItem.value.play()
      } else {
        videoItem.value.pause()
      }
    }
  })
}



</script>

<style lang="scss">
.videoItem {
  position: relative;

  video {
    width: 100%;
    height: 100%;
  }
}

.click-menu {
  width: 130px;
  background-color: rgba(98, 98, 98, 0.7);
  border-radius: 10px;
  position: absolute;
}

.menu-ul {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  text-align: center;
  cursor: pointer;
}

.menu-ul li {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  min-height: 50px;
}

.menu-ul li:hover {
  background-color: rgba(98, 98, 98, 0.9);
}
</style>