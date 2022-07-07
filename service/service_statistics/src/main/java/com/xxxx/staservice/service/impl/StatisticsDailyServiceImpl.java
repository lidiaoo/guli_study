package com.xxxx.staservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.commonutils.R;
import com.xxxx.staservice.client.UcenterClient;
import com.xxxx.staservice.entity.StatisticsDaily;
import com.xxxx.staservice.mapper.StatisticsDailyMapper;
import com.xxxx.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-26
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void createStatisticsByDay(String day) {
//            格式化时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        try {
            day = simpleDateFormat.format(simpleDateFormat.parse(day));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        删除当天数据
        baseMapper.delete(new QueryWrapper<StatisticsDaily>().eq("date_calculated", day));

        //        根据日期查询注册人数
        R r = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) r.getData()
                                           .get("countRegister");

        StatisticsDaily daily = new StatisticsDaily();
        daily.setDateCalculated(day);
        daily.setRegisterNum(countRegister);
//        生成虚拟数据
        daily.setLoginNum(RandomUtils.nextInt(100, 999));
        daily.setVideoViewNum(RandomUtils.nextInt(100, 999));
        daily.setCourseNum(RandomUtils.nextInt(100, 999));
//        存数据库
        baseMapper.insert(daily);
    }

    //    查询图表数据
    @Override
    public Map<String, Object> getChartData(String type, String begin, String end) {
//        查询符合条件的对象
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated", type);
        wrapper.between("date_calculated", begin, end);
        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);

        List<String> dateList = new ArrayList<>();
        List<Integer> registerNumList = new ArrayList<>();
//递归map对象分别封装到list数组中
        for (StatisticsDaily daily : dailyList) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":
                    registerNumList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    registerNumList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    registerNumList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    registerNumList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
//        封装map
        Map<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("registerNumList", registerNumList);
        return map;
    }
}
