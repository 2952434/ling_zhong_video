<template>
  <div class="tabbar">
    <div class="logo">
      <img src="../../assets/images/logo.png" alt="" style="height: 45px;margin-right: 10px;">
      {{ MAIN_TITLE_CHINESE }}
    </div>
    <div class="user">
      <el-button @click="loginClick" style="background-color: black;color: white;border: none;">登录</el-button>
    </div>
  </div>
  <!-- 登录弹出框 -->
  <Transition name="slide-fade">
    <LoginItem v-if="loginShow"></LoginItem>
  </Transition>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { MAIN_TITLE_CHINESE } from '@/config/config.js'
// 引入登录弹出框
import LoginItem from '@/components/loginItem/index.vue'
// 控制登录框是否出现
const loginShow = ref(false)
// 点击登录按钮出现的内容
const loginClick = () => {
  console.log(123);
  if (loginShow.value) {
    loginShow.value = false
  } else {
    loginShow.value = true
  }
}

</script>

<style scoped lang="scss">
.tabbar {
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 10px;
  height: $tabbar-height;

  .logo {
    font-size: $font-size-large;
    font-family: $font-family-default;
    padding-left: 20px;
    display: flex;
    align-items: center;
  }

  .user {
    padding-right: 20px;
  }
}


// Vue动画
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  // transform: translateX(-50%);
  opacity: 0;
}
</style>