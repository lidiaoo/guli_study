package com.xxxx.eduorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.eduorder.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-21
 */
public interface OrderService extends IService<Order> {
    String createOrders(String courseId, String memberId);

    boolean isBuyCourse(String courseId, String memberId);
}
