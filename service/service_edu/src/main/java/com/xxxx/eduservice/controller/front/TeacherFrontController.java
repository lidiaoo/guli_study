package com.xxxx.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.commonutils.R;
import com.xxxx.eduservice.entity.EduCourse;
import com.xxxx.eduservice.entity.EduTeacher;
import com.xxxx.eduservice.service.EduCourseService;
import com.xxxx.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = eduTeacherService.getTeacherFrontList(teacherPage);
        return R.ok()
                .data(map);
    }

    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable("teacherId") String teacherId) {
//查询讲师
        EduTeacher teacher = eduTeacherService.getById(teacherId);
//        查询课程
        List<EduCourse> courseList = eduCourseService.list(new QueryWrapper<EduCourse>().eq("teacher_id", teacherId));
        return R.ok()
                .data("teacher", teacher)
                .data("courseList", courseList);
    }
}
