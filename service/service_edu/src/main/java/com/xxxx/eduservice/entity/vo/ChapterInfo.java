package com.xxxx.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    public List<ChapterInfo> children = new ArrayList<>();
    private String id;
    private String courseId;
    private String title;
    private String vchapterId;
    private String vid;
    private String vtitle;
    //    阿里云视频ID
    private String videoSourceId;
}
