package com.xxxx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.eduservice.entity.EduChapter;
import com.xxxx.eduservice.entity.EduVideo;
import com.xxxx.eduservice.entity.vo.ChapterInfo;
import com.xxxx.eduservice.mapper.EduChapterMapper;
import com.xxxx.eduservice.service.EduChapterService;
import com.xxxx.eduservice.service.EduVideoService;
import com.xxxx.srvicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterInfo> getAllChapter(String courseId) {
        List<ChapterInfo> allChapter = baseMapper.getAllChapter(courseId);
//        获取全部
//        System.out.println("全部: " + allChapter);

//用set去重 获得章节对象
        List<ChapterInfo> chapterInfos = new ArrayList<>();
        Set<ChapterInfo> chapterInfoSet = new HashSet<>();
        for (ChapterInfo chapterInfo : allChapter) {
            ChapterInfo info = new ChapterInfo();
            BeanUtils.copyProperties(chapterInfo, info, "vchapter_id", "vid", "vtitle", "videoSourceId");
            chapterInfos.add(info);
            chapterInfoSet.add(info);
        }
//        转成list
        chapterInfos = new ArrayList<>(chapterInfoSet);
//        System.out.println("去重后一级: " + chapterInfos);
//        try {
//            System.out.println("去重后一级json: " + new JsonMapper().writeValueAsString(chapterInfos));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        List<ChapterInfo> finalList = new ArrayList<>();
        for (ChapterInfo chapterInfo : chapterInfos) {
//            章节下没有节点的直接封装到最终集合
            if (chapterInfo.getVchapterId() == null) {
                finalList.add(chapterInfo);
            } else {
                //            定义children集合
                List<ChapterInfo> childrens = new ArrayList<>();
                for (ChapterInfo info : allChapter) {
                    if (info.getId() != null && info.getVchapterId() != null) {
                        if (info.getVchapterId()
                                .equals(chapterInfo.getId())) {
//            将符合条件的小节封装到集合(children)
                            info.setCourseId(null);
                            info.setId(null);
                            info.setTitle(null);
                            childrens.add(info);
                        }
                    }
                }
//            将children放入对象中
                chapterInfo.setChildren(childrens);
//            将对象放入集合
                finalList.add(chapterInfo);
            }
        }
//        System.out.println("--------");
//        System.out.println(finalList);
//        json格式打印
//        try {
//            System.out.println(new JsonMapper().writeValueAsString(finalList));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
//        先根据id查询 有没有小节 有的话不可以删除
        int count = eduVideoService.count(new QueryWrapper<EduVideo>().eq("chapter_id", chapterId));
        if (count > 0) {
            throw new GuliException(20001, "不能删除");
        } else {
            int i = baseMapper.deleteById(chapterId);
            return i > 0;
        }
    }

}