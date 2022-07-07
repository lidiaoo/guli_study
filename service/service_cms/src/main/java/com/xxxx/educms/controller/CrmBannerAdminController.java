package com.xxxx.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.commonutils.R;
import com.xxxx.educms.entity.CrmBanner;
import com.xxxx.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/educms/banneradmin")
//@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        crmBannerService.pageMaps(bannerPage, null);
        return R.ok()
                .data("items", bannerPage.getRecords())
                .data("total", bannerPage.getTotal());
    }

    @GetMapping("/getBanner/{id}")
    public R getBannerInfo(String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok()
                .data("banner", banner);
    }

    @GetMapping("/getAllBanner")
    public R getAll() {
        List<CrmBanner> bannerList = crmBannerService.list(null);
        return R.ok()
                .data("items", bannerList);
    }

    @PostMapping("/addBanner")
    public R saveBanner(@RequestBody CrmBanner crmBanner) {
        boolean flag = crmBannerService.save(crmBanner);
        return flag ? R.ok() : R.error();
    }

    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner) {
        boolean flag = crmBannerService.updateById(crmBanner);
        return flag ? R.ok() : R.error();
    }

    @DeleteMapping("/removeBanner/{id}")
    public R removeBanner(String id) {
        boolean flag = crmBannerService.removeById(id);
        return flag ? R.ok() : R.error();
    }


}


