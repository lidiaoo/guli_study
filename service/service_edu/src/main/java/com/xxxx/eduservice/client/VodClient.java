package com.xxxx.eduservice.client;

import com.xxxx.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@FeignClient(value = "service-vod", fallback = VodClientFallBackImpl.class)
public interface VodClient {
    @DeleteMapping("/eduvod/video/{id}")
    R deleteVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/delete-batch")
    R deleteVideoList(@RequestParam("ids") List<String> ids);

    @PostMapping("/eduvod/video/uploadVideo")
    R upLoadVideo(MultipartFile file);
}
