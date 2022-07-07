package com.xxxx.staservice.schedule;

import com.xxxx.staservice.service.StatisticsDailyService;
import com.xxxx.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;
//
//    @Scheduled(cron = "1/2 * * * * ?")
//    public void task01() {
//        System.out.println("执行了: " + new Date());
//    }

    /**
     * 17
     * 每天凌晨1点执行定时
     * 18
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.createStatisticsByDay(day);
    }
}
