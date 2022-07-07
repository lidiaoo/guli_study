package com.xxxx.eduservice.client;

import com.xxxx.commonutils.R;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class VodClientFallBackImpl implements VodClient {
    @Override
    public R deleteVideo(String id) {
        return R.error()
                .data("msg", "调用了fallback");
    }

    @Override
    public R deleteVideoList(List<String> ids) {
        return R.error()
                .data("msg", "调用了fallback");
    }

    @Override
    public R upLoadVideo(MultipartFile file) {
        return R.error()
                .data("msg", "调用了fallback");
    }
}
