package com.xxxx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.eduservice.client.VodClient;
import com.xxxx.eduservice.entity.EduChapter;
import com.xxxx.eduservice.entity.EduCourse;
import com.xxxx.eduservice.entity.EduCourseDescription;
import com.xxxx.eduservice.entity.frontvo.CourseFrontVo;
import com.xxxx.eduservice.entity.frontvo.CourseWebVo;
import com.xxxx.eduservice.entity.vo.CourseInfo;
import com.xxxx.eduservice.entity.vo.PublishCourseVo;
import com.xxxx.eduservice.mapper.EduCourseMapper;
import com.xxxx.eduservice.service.EduChapterService;
import com.xxxx.eduservice.service.EduCourseDescriptionService;
import com.xxxx.eduservice.service.EduCourseService;
import com.xxxx.eduservice.service.EduVideoService;
import com.xxxx.srvicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    @Override
    public String saveCourseInfo(CourseInfo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new GuliException(20001, "添加失败了");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescription.setDescription(courseInfo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);

//        返回课程id
        return eduCourse.getId();
    }

    @Override
    public CourseInfo getCourseInfo(String courseId) {
        EduCourse course = baseMapper.selectOne(new QueryWrapper<EduCourse>().eq("id", courseId));
        CourseInfo courseInfo = new CourseInfo();
        if (course != null) {
            BeanUtils.copyProperties(course, courseInfo);
        }
        EduCourseDescription coursedes = eduCourseDescriptionService.getOne(new QueryWrapper<EduCourseDescription>().eq("id", courseId));
        if (coursedes != null) {
            BeanUtils.copyProperties(coursedes, courseInfo);
        }
        return courseInfo;
    }

    @Override
    public void updateCourseInfo(CourseInfo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo, eduCourseDescription);
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public PublishCourseVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public void deleteCourse(String id) {
// 1       删除视频跟小节
        eduVideoService.deleteByCourseId(id);
// 2       删除章节
        eduChapterService.remove(new QueryWrapper<EduChapter>().eq("course_id", id));
// 3       删除描述
        eduCourseDescriptionService.removeById(id);
// 4       删除课程
        baseMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
