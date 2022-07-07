package com.xxxx.staservice.controller;


import com.xxxx.commonutils.R;
import com.xxxx.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-26
 */
@RestController
@RequestMapping("/staservice/statistics")
//@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @PostMapping("/createStatisticsByDate/{day}")
    public R createStatisticsByDate(@PathVariable("day") String day) {
        statisticsDailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("/getChartData/{type}/{begin}/{end}")
    public R getChartData(@PathVariable("type") String type, @PathVariable("begin") String begin, @PathVariable("end") String end) {
        Map<String, Object> map = statisticsDailyService.getChartData(type, begin, end);
        return R.ok()
                .data(map);
    }
}

