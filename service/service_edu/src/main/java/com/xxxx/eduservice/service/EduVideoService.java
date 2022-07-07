package com.xxxx.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.eduservice.entity.EduVideo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
public interface EduVideoService extends IService<EduVideo> {
    boolean deleteByCourseId(String courseId);
}
