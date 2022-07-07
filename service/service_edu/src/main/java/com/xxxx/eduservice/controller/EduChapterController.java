package com.xxxx.eduservice.controller;


import com.xxxx.commonutils.R;
import com.xxxx.eduservice.entity.EduChapter;
import com.xxxx.eduservice.entity.vo.ChapterInfo;
import com.xxxx.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getAllChapter/{courseId}")
    public R getAllChapter(@PathVariable("courseId") String courseId) {
        List<ChapterInfo> chapterTreeNodes = eduChapterService.getAllChapter(courseId);
        return R.ok()
                .data("items", chapterTreeNodes);
    }

    @PostMapping("/saveChapter")
    public R saveChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @GetMapping("/getChapterInfo/{chapterId}")
    public R saveChapter(@PathVariable("chapterId") String chapterId) {
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok()
                .data("data", chapter);
    }

    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

}

