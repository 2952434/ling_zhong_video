package com.lingzhong.video.service.impl;

import com.google.gson.Gson;
import com.lingzhong.video.utils.TimeUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

/**
 * @Author: 李君祥
 * @Date: 2023/11/1 21:46
 * @Description: 七牛云文件操作服务类
 */
@Service
public class QiNiuService {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.url}")
    private String url;

    /**
     * 根据文件路径删除视频
     *
     * @param filePath 文件路径
     * @return boolean
     */
    public boolean deleteFile(String filePath) {
        String path = filePath.substring(url.length());
        Configuration configuration = new Configuration(Region.region1());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, configuration);
        try {
            bucketManager.delete(bucket, path);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 分片上传视频
     *
     * @param file 视频文件
     * @return 视频路径
     */
    public String uploadVideo(MultipartFile file) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        // 设置分片上传并发，1：采用同步上传；大于1：采用并发上传
        cfg.resumableUploadMaxConcurrentTaskCount = 2;

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "video/" + TimeUtils.getNowDateString("yyyy/MM/dd") + "/" + (new Random().nextInt(1000) + 1) + "/" + file.getOriginalFilename();
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
                new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
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

    /**
     * 上传图片
     *
     * @param photo 图片
     * @return url路径
     */
    public String uploadImage(MultipartFile photo) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "photo/" + TimeUtils.getNowDateString("yyyy/MM/dd") + "/" + (new Random().nextInt(1000) + 1) + "/" + photo.getOriginalFilename();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            try {
                Response response = uploadManager.put(photo.getBytes(), key, upToken);
                //解析上传成功的结果
                new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);
                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                        throw new RuntimeException("上传头像失败");
                    }
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("上传头像失败");
        }
        return url + key;
    }

}
