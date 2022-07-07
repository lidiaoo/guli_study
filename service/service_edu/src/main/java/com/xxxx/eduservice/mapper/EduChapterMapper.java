package com.xxxx.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.eduservice.entity.EduChapter;
import com.xxxx.eduservice.entity.vo.ChapterInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
@Mapper
public interface EduChapterMapper extends BaseMapper<EduChapter> {
    List<ChapterInfo> getAllChapter(String courseId);
}
