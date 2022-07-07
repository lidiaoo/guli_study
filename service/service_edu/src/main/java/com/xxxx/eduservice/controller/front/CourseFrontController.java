package com.xxxx.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.commonutils.JwtUtils;
import com.xxxx.commonutils.R;
import com.xxxx.commonutils.ordervo.CourseWebVoOrder;
import com.xxxx.eduservice.client.OrderClient;
import com.xxxx.eduservice.entity.EduCourse;
import com.xxxx.eduservice.entity.frontvo.CourseFrontVo;
import com.xxxx.eduservice.entity.frontvo.CourseWebVo;
import com.xxxx.eduservice.entity.vo.ChapterInfo;
import com.xxxx.eduservice.service.EduChapterService;
import com.xxxx.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OrderClient orderClient;

    @PostMapping("/getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable("page") Long page, @PathVariable("limit") Long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.getCourseFrontList(coursePage, courseFrontVo);
        return R.ok()
                .data(map);
    }

    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);
        List<ChapterInfo> allChapter = eduChapterService.getAllChapter(courseId);
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBuyCourse = false;
        if (!StringUtils.isEmpty(memberId)) {
            R isBuyCourseR = orderClient.isBuyCourse(courseId, memberId);
            isBuyCourse = (Boolean) isBuyCourseR.getData()
                                                .get("isBuyCourse");
        }
        return R.ok()
                .data("courseWebVo", courseWebVo)
                .data("allChapter", allChapter)
                .data("isBuyCourse", isBuyCourse);
    }

    @PostMapping("/getFrontCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getFrontCourseInfoOrder(@PathVariable("courseId") String courseId) {
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo, courseWebVoOrder);
        return courseWebVoOrder;
    }
}
