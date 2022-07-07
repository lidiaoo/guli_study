package com.xxxx.vodservice.controller;

import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.xxxx.commonutils.R;
import com.xxxx.srvicebase.exception.GuliException;
import com.xxxx.vodservice.service.VodService;
import com.xxxx.vodservice.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class UploadVideoController {
    @Autowired
    private VodService vodService;

    @GetMapping("/getPlayAuth/{videoSourceId}")
    public R getPlayAuth(@PathVariable("videoSourceId") String videoSourceId) {
        try {
            DefaultAcsClient client = InitVodClient.getClient();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoSourceId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok()
                    .data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }

    @DeleteMapping("/{id}")
    public R deleteVideo(@PathVariable("id") String id) {
        vodService.deleteVideo(id);
        return R.ok();
    }

    //    批量删除多个id
    @DeleteMapping("/delete-batch")
    public R deleteVideoList(@RequestParam("ids") List<String> ids) {
        vodService.deleteVideoList(ids);
        return R.ok();
    }

    @PostMapping("/uploadVideo")
    public R upLoadVideo(MultipartFile file) {
        Map map = vodService.upLoadVideo(file);
        Object videoOriginalName = (String) map.get("videoOriginalName");
        UploadStreamResponse response = (UploadStreamResponse) map.get("response");
        String videoId = response.getVideoId();
        return R.ok()
                .data("videoId", videoId)
                .data("videoOriginalName", videoOriginalName);
    }
}
