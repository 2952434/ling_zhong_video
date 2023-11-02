<template>
  <div class='videoUserInfo'>
    <div class="user videoInfo">
      <div style="position: relative;top: 5px;left: 3px;">
        <img :src="props.videoNumInfo.userPhoto" />
      </div>
    </div>
    <div class="like videoInfo" @click="liveVideo">
      <i class="iconfont icon-Heart" ref="iconHeart"></i>
      {{ props.videoNumInfo.videoLikeNum }}
    </div>
    <div class="comment videoInfo" @click="openChat">
      <i class="iconfont icon-Chat" ref="iconChat"></i>
      {{ props.videoNumInfo.videoCommentNum }}
    </div>
    <div class="collect videoInfo" @click="collectVideo">
      <i class="iconfont icon-Star" ref="iconStar"></i>
      {{ props.videoNumInfo.videoCollectNum }}
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
const props = defineProps(['videoNumInfo'])
// 获取到三个图标
const iconHeart = ref()
const iconChat = ref()
const iconStar = ref()
// 喜欢此视频
const liveVideo = () => {
  if (iconHeart.value.style.color === 'red') {
    iconHeart.value.style.color = 'white'
  } else {
    iconHeart.value.style.color = 'red'
  }

}
// 打开评论
const openChat = () => {
}
// 收藏此视频
const collectVideo = () => {
}
</script>

<style scoped lang='scss'>
.videoUserInfo {
  position: absolute;
  user-select: none;
  right: 0;
  top: 50%;
  transform: translateY(-50%);

  .videoInfo {
    display: flex;
    cursor: pointer;

    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-bottom: 30px;

    .iconfont {
      font-size: 30px;
    }

  }

  .user {
    width: 60px;
    height: 60px;
    background-image: url(../../../assets/images/avatar1.png);
    // background-position: -3px -3px;
    background-size: cover;

    img {
      width: 44px;
      height: 44px;
      transform: translate(3px 3px);
      border-radius: 50%;
    }
  }
}
</style>