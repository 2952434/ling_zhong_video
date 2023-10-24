<template>
  <div class='menu'>
    <ul id="ul">
      <li v-for="(item, index) in MENU_LIST" class="listItem" :key="index" @click="menuClick($event, item)">
        <component :is="item.icon" style="height: 20px;vertical-align:text-bottom;padding-right: 5px;"></component>
        {{ item.title }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { MENU_LIST } from '@/config/config.js'
import { useRouter } from 'vue-router';
const router = useRouter()
/**
 * 列表点击事件
 */
const menuClick = (e, item) => {
  console.log(e, item);
  clearClass()
  e.target.children[0].children[0].setAttribute('fill', 'white')
  e.target.classList.add('active')
  router.push(item.path)
}
/**
 * 清除其余所有的active
 */
const clearClass = () => {
  const lis = document.querySelectorAll('.listItem')
  lis.forEach(item => {
    item.classList.remove('active')
    item.children[0].children[0].setAttribute('fill', 'currentColor')
  })
}
</script>

<style scoped lang='scss'>
.menu {
  height: calc(100% - $tabbar-height);
  width: $menu-width;
  padding-left: 10px;

  ul {
    li {
      width: 100%;
      text-align: center;
      height: 30px;
      padding: 10px 0;
      line-height: 30px;
      margin: 5px 0;
      border-radius: 20px;
      cursor: pointer;
      color: gray;
      user-select: none;

      &:hover {
        background: rgba(59, 59, 60, 0.9);
        backdrop-filter: blur(50px);
        transition: all .5;
        color: white;
      }


    }

    li.active {
      background: rgba(59, 59, 60, 0.9);
      backdrop-filter: blur(50px);
      color: white;
    }
  }
}
</style>