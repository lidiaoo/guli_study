package com.xxxx.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublishCourseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String price;//只用于显示
    private Integer lessonNum;
    private String cover;
    private String teacherName;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String description;
}
