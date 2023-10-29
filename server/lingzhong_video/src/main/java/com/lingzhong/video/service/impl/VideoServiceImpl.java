package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.lingzhong.video.bean.dto.TempUser;
import com.lingzhong.video.bean.dto.VideoPublishDTO;
import com.lingzhong.video.bean.po.Video;
import com.lingzhong.video.bean.po.VideoData;
import com.lingzhong.video.bean.po.VideoLabel;
import com.lingzhong.video.bean.vo.VideoVo;
import com.lingzhong.video.mapper.VideoLabelMapper;
import com.lingzhong.video.mapper.VideoMapper;
import com.lingzhong.video.service.VideoDataService;
import com.lingzhong.video.service.VideoService;
import com.lingzhong.video.utils.TimeUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author ljx
 * @description 针对表【video】的数据库操作Service实现
 * @createDate 2023-10-27 20:30:22
 */
@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.url}")
    private String url;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private VideoLabelMapper videoLabelMapper;

    @Resource
    private VideoDataService videoDataService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean uploadVideo(MultipartFile file, VideoPublishDTO videoPublishDTO) throws Exception {
        try {
            TempUser tempUser = new TempUser();
            String videoUrl = uploadVideo(file);
            Video video = new Video();
            BeanUtils.copyProperties(videoPublishDTO, video);
            video.setVideoUserId(tempUser.getUserId());
            video.setVideoUrl(videoUrl);
            video.setVideoDate(new Date());
            int insert = videoMapper.insert(video);
            System.out.println(insert);
            List<Integer> labelIds = videoPublishDTO.getLabelIds();
            for (Integer labelId : labelIds) {
                VideoLabel videoLabel = new VideoLabel();
                videoLabel.setVideoId(video.getVideoId());
                videoLabel.setLabelId(labelId);
                videoLabelMapper.insert(videoLabel);
            }
            VideoData videoData = new VideoData();
            videoData.setVideoId(video.getVideoId());
            videoDataService.insertVideoData(videoData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("添加视频失败");
        }
        return true;
    }

    @Override
    public List<VideoVo> getVideo(Integer page) {
//        TODO 算法需要优化
        return videoMapper.getVideo(page * 10);
    }

    @Override
    public List<Video> getListById(List<Integer> videoIds) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("video_id" , videoIds);
        return videoMapper.selectList(queryWrapper);
    }

    @Override
    public List<VideoVo> getUserLikeVideoList(Integer userId) {
        return videoMapper.selectLikeVideoByUserId(userId);
    }

    @Override
    public List<VideoVo> getUserCollectVideoList(Integer userId) {
        return videoMapper.selectCollectVideoByUserId(userId);
    }


    private String uploadVideo(MultipartFile file) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        // 设置分片上传并发，1：采用同步上传；大于1：采用并发上传
        cfg.resumableUploadMaxConcurrentTaskCount = 2;

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "video/" + TimeUtils.getNowDateString("yyyy/MM/dd") + "/" + (new Random().nextInt(1000) + 1) +"/" + file.getOriginalFilename();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
        try {
            //设置断点续传文件进度保存目录
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            try {
                Response response = uploadManager.put(file.getBytes(), key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);
                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return url + key;
    }
}




