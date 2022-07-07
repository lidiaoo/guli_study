package com.xxxx.staservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.staservice.entity.StatisticsDaily;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-26
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void createStatisticsByDay(String day);

    Map<String, Object> getChartData(String type, String begin, String end);
}
