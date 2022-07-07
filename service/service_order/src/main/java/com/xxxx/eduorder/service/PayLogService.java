package com.xxxx.eduorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.eduorder.entity.PayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-21
 */
public interface PayLogService extends IService<PayLog> {
    Map createNative(String orderId);

    Map queryPayStatus(String orderNo);

    void updateOrderStatus(Map map);
}
