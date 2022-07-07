package com.xxxx.eduservice.controller;


import com.xxxx.commonutils.R;
import com.xxxx.eduservice.entity.vo.EduSubjectTreeNode;
import com.xxxx.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-26
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok();
    }

    @GetMapping("/getList")
    public R getList() {
        List<EduSubjectTreeNode> eduSubjectTreeNodeList = eduSubjectService.getSubjectTree();
        return R.ok()
                .data("items", eduSubjectTreeNodeList);
    }
}

