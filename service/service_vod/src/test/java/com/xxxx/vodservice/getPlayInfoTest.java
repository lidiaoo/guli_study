package com.xxxx.vodservice;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class getPlayInfoTest {

    //账号AK信息请填写(必选)
    private static final String accessKeyId = "LTAI5tLLzBcAA8a6PHNoAzwo";
    //账号AK信息请填写(必选)
    private static final String accessKeySecret = "oAhowEyNCg5o8o4SNDQD0QYXUN45Jn";

    public static void main(String[] args) {
        // 一、视频文件上传
        // 视频标题(必选)
        String title = "111";
        // 1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        // 2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。
        // 任何上传方式文件名必须包含扩展名

        String fileName = "D:/1.mp4";
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }


    public static void getPlayAuth() throws Exception {
        DefaultAcsClient client = Client.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthRequest authRequest = new GetVideoPlayAuthRequest();
        authRequest.setVideoId("166e30443c3f43febcf12626d65e159c");

        GetVideoPlayAuthResponse authResponse = client.getAcsResponse(authRequest);

        System.out.println(authResponse.getPlayAuth());

    }

    public static void getPlayUrl() throws Exception {
        DefaultAcsClient client = Client.initVodClient(accessKeyId, accessKeySecret);

//        请求
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("166e30443c3f43febcf12626d65e159c");
//响应
        GetPlayInfoResponse response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            String playURL = playInfo.getPlayURL();
            System.out.println("Url: " + playURL);
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase()
                                                        .getTitle() + "\n");
    }
}
