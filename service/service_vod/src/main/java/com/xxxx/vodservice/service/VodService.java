package com.xxxx.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VodService {
    void deleteVideo(String id);

    void deleteVideoList(List<String> ids);

    Map upLoadVideo(MultipartFile file);
}
