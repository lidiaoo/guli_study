package com.xxxx.ossservice.controller;

import com.xxxx.commonutils.R;
import com.xxxx.ossservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private FileService ossService;

    @PostMapping("upLoadAvatar")
    public R upLoadAvatar(MultipartFile file) {
        String url = ossService.upload(file);
        return R.ok()
                .data("url", url);
    }
}
