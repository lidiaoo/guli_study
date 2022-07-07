package com.xxxx.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.xxxx.vodservice.service.VodService;
import com.xxxx.vodservice.utils.InitVodClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public void deleteVideo(String id) {
        DefaultAcsClient client = InitVodClient.getClient();
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(id);
        try {
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVideoList(List<String> ids) {
        DefaultAcsClient client = InitVodClient.getClient();
        DeleteVideoRequest request = new DeleteVideoRequest();
//将list集合按照,符号分割
        String videos = String.join(",", ids);
//        String videos = StringUtils.join(ids.toArray(), ",");
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videos);
        try {
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map upLoadVideo(MultipartFile file) {
        InputStream inputStream = null;
        UploadStreamResponse response = null;
        try {
            inputStream = file.getInputStream();

            // 一、视频文件上传
            // 任何上传方式文件名必须包含扩展名
            String fileName = file.getOriginalFilename();

            // 视频标题(必选)
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            // 1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
            // 2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。
            // 3.流式上传时，文件名称为源文件名，如文件名称.mp4(必选)。


            UploadStreamRequest request = new UploadStreamRequest(InitVodClient.ACCESSKEYID, InitVodClient.ACCESSKEYSECRET, title, fileName, inputStream);
            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
            //request.setShowWaterMark(true);
            /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
            //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
            /* 视频分类ID(可选) */
            //request.setCateId(0);
            /* 视频标签,多个用逗号分隔(可选) */
            //request.setTags("标签1,标签2");
            /* 视频描述(可选) */
            //request.setDescription("视频描述");
            /* 封面图片(可选) */
            //request.setCoverURL("http://cover.sample.com/sample.jpg");
            /* 模板组ID(可选) */
            //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
            /* 工作流ID(可选) */
            //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
            /* 存储区域(可选) */
            //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
            /* 开启默认上传进度回调 */
            // request.setPrintProgress(true);
            /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
            // request.setProgressListener(new PutObjectProgressListener());
            /* 设置应用ID*/
            //request.setAppId("app-1000000");
            /* 点播服务接入点 */
            //request.setApiRegionId("cn-shanghai");
            /* ECS部署区域*/
            // request.setEcsRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
//            关闭流
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("videoOriginalName", file.getOriginalFilename());
        map.put("response", response);
        return map;
    }
}
