package com.xxxx.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.eduservice.entity.EduSubject;
import com.xxxx.eduservice.entity.vo.EduSubjectTreeNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-26
 */
public interface EduSubjectService extends IService<EduSubject> {
    //读取Excel
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<EduSubjectTreeNode> getSubjectTree();
}
