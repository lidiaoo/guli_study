package com.xxxx.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.eduservice.entity.EduSubject;
import com.xxxx.eduservice.entity.SubjectData;
import com.xxxx.eduservice.service.EduSubjectService;
import com.xxxx.srvicebase.exception.GuliException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //    注入edusubjectservice
    private EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(200001, "数据表未空");
        }

        EduSubject existsOneSubject = existsOneSubject(subjectData.getOneSubjectName());
        if (existsOneSubject == null) {
            existsOneSubject = new EduSubject();
            existsOneSubject.setParentId("0");
            existsOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existsOneSubject);
        }

//        获取一级分类的id值
        String pid = existsOneSubject.getId();

        EduSubject existsTwoSubject = existsTwoSubject(subjectData.getTwoSubjectName(), pid);
        if (existsTwoSubject == null) {
            existsTwoSubject = new EduSubject();
            existsTwoSubject.setParentId(pid);
            existsTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existsTwoSubject);
        }
    }

    //    判断一级分类不能重复添加
    private EduSubject existsOneSubject(String name) {
        return eduSubjectService.getOne(new QueryWrapper<EduSubject>().eq("title", name)
                                                                      .eq("parent_id", "0"));
    }

    //    判断二级分类不能重复添加
    private EduSubject existsTwoSubject(String name, String pid) {
        return eduSubjectService.getOne(new QueryWrapper<EduSubject>().eq("title", name)
                                                                      .eq("parent_id", pid));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
