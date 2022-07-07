package com.xxxx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.eduservice.client.VodClient;
import com.xxxx.eduservice.entity.EduVideo;
import com.xxxx.eduservice.mapper.EduVideoMapper;
import com.xxxx.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public boolean deleteByCourseId(String courseId) {
        //        查询并批量删除video
        List<EduVideo> videoSourceIdList = baseMapper.selectList(new QueryWrapper<EduVideo>().eq("course_id", courseId)
                                                                                             .select("video_source_id"));
        List ids = new ArrayList<>();
        for (EduVideo eduVideo : videoSourceIdList) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
                ids.add(videoSourceId);
            }
        }
        if (ids.size() > 0) {
            vodClient.deleteVideoList(ids);
        }

        //删除video表示的记录
        Integer count = baseMapper.delete(new QueryWrapper<EduVideo>().eq("course_id", courseId));
        return null != count && count > 0;
    }
}
