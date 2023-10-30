/**
 * 视频操作类接口
 */
// 引入axios
import request from "../utils/request"
export const getVideoByPages = (page) => {
  return request({
    url: `/video/getVideo/${page}`,
    method: "get",
  })
}
