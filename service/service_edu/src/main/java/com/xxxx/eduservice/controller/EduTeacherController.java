package com.xxxx.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xxxx.commonutils.R;
import com.xxxx.eduservice.entity.EduTeacher;
import com.xxxx.eduservice.entity.vo.TeacherQuery;
import com.xxxx.eduservice.service.EduTeacherService;
import com.xxxx.srvicebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/getAll")
    public R getAll() throws JsonProcessingException {
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        return R.ok()
                .data("items", eduTeachers);
    }

    @GetMapping("/getAll2")
    public List<EduTeacher> getAll2() throws JsonProcessingException {
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        return eduTeachers;
    }

    @DeleteMapping("/{id}")
    public R del(@PathVariable("id") String id) throws JsonProcessingException {
        boolean flag = eduTeacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    @GetMapping("/pageTeacher/{current}/{limit}")
    public R getpage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        Page<EduTeacher> p = new Page(current, limit);
        IPage page = eduTeacherService.page(p, null);
//        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
//        stringObjectHashMap.put("total", page.getTotal());//数据个数
//        stringObjectHashMap.put("rows", page.getRecords());//每页数据的list集合
//        return R.ok().data(stringObjectHashMap);

        return R.ok()
                .data("total", page.getTotal())
                .data("rows", page.getRecords());
    }

    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit, @RequestBody(required = false) TeacherQuery teacherQuery) {
//        分页对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
//        查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        if (teacherQuery != null) {
            String name = teacherQuery.getName();
            Integer level = teacherQuery.getLevel();
            String begin = teacherQuery.getBegin();
            String end = teacherQuery.getEnd();

            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq("level", level);
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

        IPage<Map<String, Object>> mapIPage = eduTeacherService.pageMaps(eduTeacherPage, wrapper);
        return R.ok()
                .data("total", mapIPage.getTotal())
                .data("rows", mapIPage.getRecords());
    }

    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok()
                .data("teacher", eduTeacher);
    }

    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        return flag ? R.ok() : R.error();
    }

    //    异常测试
    @GetMapping("/e")
    public Integer e() {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            throw new GuliException(500, "被除数不能为0");
        }
        return 1;
    }
}

