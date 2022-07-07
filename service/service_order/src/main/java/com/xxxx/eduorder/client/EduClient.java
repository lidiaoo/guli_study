package com.xxxx.eduorder.client;

import com.xxxx.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "service-edu")
public interface EduClient {
    @PostMapping("/eduservice/coursefront/getFrontCourseInfoOrder/{courseId}")
    CourseWebVoOrder getFrontCourseInfoOrder(@PathVariable("courseId") String courseId);
}
