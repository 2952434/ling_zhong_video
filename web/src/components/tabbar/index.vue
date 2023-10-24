<template>
  <div class="tabbar">
    <div class="logo">
      {{ MAIN_TITLE_CHINESE }}
    </div>
    <div class="user">
      <el-button>登录</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { MAIN_TITLE_CHINESE } from '@/config/config.js'
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
  }

  .user {
    padding-right: 20px;
  }
}
</style>