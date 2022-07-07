package com.xxxx.eduservice.controller;


import com.xxxx.commonutils.R;
import com.xxxx.eduservice.client.VodClient;
import com.xxxx.eduservice.entity.EduVideo;
import com.xxxx.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    @PostMapping("/saveVideo")
    public R saveVideo(@RequestBody EduVideo eduVideo) {
        System.out.println(eduVideo);
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    @GetMapping("/getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable("videoId") String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok()
                .data("data", eduVideo);
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        System.out.println(eduVideo);
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    @DeleteMapping("/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId) {
//        获取videoSourceId
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
//        调用删除视频
            vodClient.deleteVideo(videoSourceId);
        }

//        删除数据库记录
        boolean flag = eduVideoService.removeById(videoId);
        if (flag) {
            return R.ok();
        } else {
            return R.ok();
        }
    }

    //    测试降级
    @GetMapping("/deGrade")
    public R deGrade() {
        return vodClient.deleteVideo("1");
    }

}

