package com.xxxx.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.eduservice.entity.EduChapter;
import com.xxxx.eduservice.entity.vo.ChapterInfo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
public interface EduChapterService extends IService<EduChapter> {
    List<ChapterInfo> getAllChapter(String id);

    boolean deleteChapter(String chapterId);
}
