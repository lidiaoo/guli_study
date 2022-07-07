package com.xxxx.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.commonutils.R;
import com.xxxx.eduservice.entity.EduCourse;
import com.xxxx.eduservice.entity.EduTeacher;
import com.xxxx.eduservice.service.EduCourseService;
import com.xxxx.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询前8个热门课程 前四个讲师
     *
     * @return
     */
    @GetMapping("/index")
    public R getCourseTeacher() {
        List<EduCourse> courseList = eduCourseService.list(new QueryWrapper<EduCourse>().orderByDesc("id")
                                                                                        .last("limit 8"));
        List<EduTeacher> teacherList = eduTeacherService.list(new QueryWrapper<EduTeacher>().orderByDesc("id")
                                                                                            .last("limit 4"));
        return courseList != null && teacherList != null ? R.ok()
                                                            .data("courseList", courseList)
                                                            .data("teacherList", teacherList) : R.error();
    }
}
