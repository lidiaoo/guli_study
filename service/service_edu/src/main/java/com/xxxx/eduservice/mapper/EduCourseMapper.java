package com.xxxx.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.eduservice.entity.EduCourse;
import com.xxxx.eduservice.entity.frontvo.CourseWebVo;
import com.xxxx.eduservice.entity.vo.PublishCourseVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    PublishCourseVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
