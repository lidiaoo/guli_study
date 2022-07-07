package com.xxxx.educms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.commonutils.R;
import com.xxxx.educms.entity.CrmBanner;
import com.xxxx.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-06
 */
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/getAllBanner")
    public R getAll() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> bannerList = crmBannerService.selectAllBanner();
        return R.ok()
                .data("items", bannerList);
    }
}


