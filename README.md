# 凌众短视频

### 项目体验

- [**凌众视频**](http://www.ivikey.top/lingzhongvideo)

### 成员介绍

* 前端成员：*陈士博*
* 后端成员：*孙铭杰、李君祥*

### 项目介绍

1. 凌众短视频是一款三人团队打造的短视频娱乐项目，适合休闲放松的简易娱乐选择。在娱乐主流的互联网时代下凌众短视频让你居家环游世界!
2. 本项目提供了 *简单的操作方式*、*专注用户的喜好推荐*、*美观的页面设计*、*视频专题专类的展示*、*交互交流的平台*、*随心分享记录生活的空间*、*喜好收藏的个人空间*......

### 项目演示：

> **提示：若视频不能播放请查看docs下的视频**

[**项目演示视频链接** >>>](http://s32t6kk2m.hb-bkt.clouddn.com/video/lingzhong/lingzhong.mp4)

### 程序运行说明

#### 前端运行：

1. 使用git克隆代码：
```bash
 git clone git@gitee.com:sminjer/350-start.git
```
2. 进入到web目录，可以使用命令行：
```bash
cd web
```
3. 查看电脑是否安装node：
```
node -v
```
  * 输出版本号则已安装node，去往第四步
  * 未识别命令则需要安装node，访问网址[https://nodejs.org/en/download](https://nodejs.org/en/download)
下载对应版本，按顺序安装即可
  * 安装完成后使用`node -v`检查是否安装成功

4. 在web目录下，输入命令：
```bash
npm install
```
5. 使用命令运行：
```bash
npm run dev
```
6. 将会自动打开浏览器，并展示效果，项目运行在3000端口

#### 后端运行：

1. 使用git克隆代码：
```bash
git clone git@gitee.com:sminjer/350-start.git
```
2. 使用Idea打开`server\lingzhong_video`目录。
3. 打开Idea设置，搜索Maven，配置自己的Maven信息，导入相关依赖。
4. 运行项目。
5. 进入`server/lingzhong_video/api-test/lz-auth-api.http`下按照流程申请Token。
6. 访问接口文档[http://localhost:8848/doc.html](http://localhost:8848/doc.html)，将"Bearer "+Token填写到文档Authorize目录下，刷新页面后即可尽情访问服务。

### 技术栈

#### 前端技术栈
1. 框架：`Vue3`
2. 脚手架：`Vite`
3. UI组件库：`ElementUI Plus`
4. ajax请求：`axios`
5. 项目状态管理：`pinia`
6. css预处理器：`scss`
7. 库管理器：`npm`

#### 后端技术栈
1. 主体架构：`SpringBoot`
2. 安全访问框架：`SpringSecurity`
3. 数据访问：`MybatisPlus`、`MySQL`
4. 缓存：`Redis`
5. 中间件：`ElasticSearch`
6. 打包工具：`Docker`
7. 服务器：`腾讯云服务器`、`七牛云对象存储`
8. 前后端交互API文档：`Swagger3`

