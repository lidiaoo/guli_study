package com.xxxx.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.eduservice.entity.EduCourse;
import com.xxxx.eduservice.entity.frontvo.CourseFrontVo;
import com.xxxx.eduservice.entity.frontvo.CourseWebVo;
import com.xxxx.eduservice.entity.vo.CourseInfo;
import com.xxxx.eduservice.entity.vo.PublishCourseVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
public interface EduCourseService extends IService<EduCourse> {
    String saveCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfo courseInfo);

    PublishCourseVo getPublishCourseInfo(String courseId);

    void deleteCourse(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
