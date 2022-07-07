package com.xxxx.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.commonutils.JwtUtils;
import com.xxxx.commonutils.R;
import com.xxxx.eduservice.client.UcenterClient;
import com.xxxx.eduservice.entity.EduComment;
import com.xxxx.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/eduservice/comment")
//@CrossOrigin
public class EduCommentController {
    @Autowired
    private EduCommentService eduCommentService;
    @Autowired
    private UcenterClient ucenterClient;

    //    分页查询评论
    @GetMapping("/getCommentList/{page}/{limit}")
    public R getCommentList(@PathVariable("page") Long page, @PathVariable("limit") Long limit, @RequestParam("courseId") String courseId) {
        Page<EduComment> pageParam = new Page<>(page, limit);
        eduCommentService.page(pageParam, new QueryWrapper<EduComment>().eq("course_id", courseId)
                                                                        .orderByDesc("gmt_modified"));
        List<EduComment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok()
                .data(map);
    }

    @PostMapping("/addComment")
    public R addComment(@RequestBody EduComment eduComment, @RequestHeader("token") String token) {
        System.out.println(eduComment);
        String memberId = JwtUtils.getMemberIdByToken(token);
        if (StringUtils.isEmpty(memberId)) {
            return R.error()
                    .code(20001)
                    .message("请登录");
        }
        Map<String, String> memberInfoByHeader = ucenterClient.getMemberInfoByHeader(token);
        System.out.println(memberInfoByHeader);
        String nickname = memberInfoByHeader.get("nickname");
        String avatar = memberInfoByHeader.get("avatar");
        String id = memberInfoByHeader.get("id");
        eduComment.setNickname(nickname);
        eduComment.setAvatar(avatar);
        eduComment.setMemberId(id);
        boolean save = eduCommentService.save(eduComment);
        return save ? R.ok() : R.error();
    }

    @GetMapping("/testUcenterClient/{mobile}")
    public R testUcenterClient(@PathVariable("mobile") String mobile) {
        R memberByMobile = ucenterClient.getMemberInfoByMobile(mobile);
        System.out.println(memberByMobile);
        return memberByMobile;
    }
}

