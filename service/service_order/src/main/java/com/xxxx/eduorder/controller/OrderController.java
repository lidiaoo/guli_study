package com.xxxx.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.commonutils.JwtUtils;
import com.xxxx.commonutils.R;
import com.xxxx.eduorder.entity.Order;
import com.xxxx.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-19
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId, @RequestHeader(value = "token") String token) {
        String memberId = JwtUtils.getMemberIdByToken(token);
        String orderNo = orderService.createOrders(courseId, memberId);
        return R.ok()
                .data("orderNo", orderNo);
    }

    @GetMapping("/getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable("orderNo") String orderNo) {
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        return R.ok()
                .data("order", order);
    }

    @GetMapping("/getOrderListByMemberId/{memberId}")
    public R getOrderListByMemberId(@PathVariable("memberId") String memberId) {
        System.out.println("memberId: " + memberId);
        List<Order> orders = orderService.list(new QueryWrapper<Order>().eq("member_id", memberId));
        return R.ok()
                .data("items", orders);
    }

    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public R isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId) {
        boolean isBuyCourse = orderService.isBuyCourse(courseId, memberId);
        return R.ok()
                .data("isBuyCourse", isBuyCourse);

    }
}

