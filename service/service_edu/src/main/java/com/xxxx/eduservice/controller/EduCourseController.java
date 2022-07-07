package com.xxxx.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.commonutils.R;
import com.xxxx.eduservice.entity.EduCourse;
import com.xxxx.eduservice.entity.vo.CourseInfo;
import com.xxxx.eduservice.entity.vo.CourseQuery;
import com.xxxx.eduservice.entity.vo.PublishCourseVo;
import com.xxxx.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/saveCourseInfo")
    public R saveCourseInfo(@RequestBody CourseInfo courseInfo) {
        String courseId = eduCourseService.saveCourseInfo(courseInfo);
        return R.ok()
                .data("courseId", courseId);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId) {
        CourseInfo courseInfo = eduCourseService.getCourseInfo(courseId);
        return R.ok()
                .data("data", courseInfo);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfo courseInfo) {
        eduCourseService.updateCourseInfo(courseInfo);
        return R.ok();
    }

    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable("courseId") String courseId) {
        PublishCourseVo coursePublishInfo = eduCourseService.getPublishCourseInfo(courseId);
        return R.ok()
                .data("data", coursePublishInfo);
    }

    //    课程最终发布
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable("courseId") String courseId) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(courseId);
        courseInfo.setStatus("Normal");
        eduCourseService.updateCourseInfo(courseInfo);
        return R.ok();
    }

    //    分页查询
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit, @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> eduCoursePage = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (courseQuery != null) {
            String title = courseQuery.getTitle();
            String status = courseQuery.getStatus();
            String begin = courseQuery.getBegin();
            String end = courseQuery.getEnd();
            if (!StringUtils.isEmpty(title)) {
                wrapper.like("title", title);
            }
            if (!StringUtils.isEmpty(status)) {
                wrapper.eq("status", status);
            }
            if (!StringUtils.isEmpty(begin)) {
                wrapper.ge("gmt_create", begin);
            }
            if (!StringUtils.isEmpty(end)) {
                wrapper.le("gmt_create", end);
            }
//        排序
            wrapper.orderByDesc("gmt_create");
        }

        IPage<Map<String, Object>> mapIPage = eduCourseService.pageMaps(eduCoursePage, wrapper);
        return R.ok()
                .data("total", mapIPage.getTotal())
                .data("rows", mapIPage.getRecords());
    }

    @DeleteMapping("/{id}")
    public R deleteCourseId(@PathVariable("id") String id) {
        eduCourseService.deleteCourse(id);
        return R.ok();
    }
}

