/**
 * Video相关的仓库
 */
import { defineStore } from "pinia";
import { getVideoByPages } from "@/api/video";
import { ElMessage } from "element-plus";
// 创建Video小仓库
let useVideoStore = defineStore({
  id: 'video',
  state: () => {
    return {
      // 视频列表
      videoList: [],
      // 当前第几页
      videoListPage: 0,
      // 每页请求几个数据
      videoListPageSize: 10,
      // 当前播放的是第几个视频
      videoCurrentCount: 0,
      // 总共播放了多少个视频
      totalVideoCount: 0
    }
  },
  actions: {
    getVideo() {
      return new Promise((resolve, reject) => {
        getVideoByPages(this.videoListPage).then((res) => {
          if (res.status === 200) {
            this.videoList.push(...res.obj);
            resolve(res);
          } else {
            reject(res);
          }
        }, (err) => {
          ElMessage.error('认证失败，请先登录！')
        });
      });
    },
    // 当前视频增加
    addCurrentCount() {
      this.videoCurrent += 1
    },
    // 当前总共播放视频增加
    addTotalVideoCount() {
      this.totalVideoCount += 1
    },
  },
  getters: {
    allVideoList() {
      return this.videoList.map((item, index) => {
        item.id = index
        return item
      })

    }
  }
})
export default useVideoStore;