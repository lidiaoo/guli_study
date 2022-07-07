package com.xxxx.eduorder.controller;


import com.xxxx.commonutils.R;
import com.xxxx.eduorder.entity.PayLog;
import com.xxxx.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-19
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable("orderNo") String orderNo) {
        Map map = payLogService.createNative(orderNo);
        return R.ok()
                .data(map);
    }

    @GetMapping("/queryPayStatus/{orderNo}")
    public R getPayStatus(@PathVariable("orderNo") String orderNo) {
        Map map = payLogService.queryPayStatus(orderNo);
        System.out.println(map);
        if (map == null) {
            return R.error()
                    .message("支付出错了");
        }

        String trade_state = (String) map.get("trade_state");
        if (!StringUtils.isEmpty(trade_state) && trade_state.equals("SUCCESS")) {//如果成功
            //更改订单状态
            payLogService.updateOrderStatus(map);
            return R.ok()
                    .message("支付成功");
        }

        String err_code_des = (String) map.get("err_code_des");
        if (!StringUtils.isEmpty(err_code_des)) {//如果出错
            return R.error()
                    .message(err_code_des);
        }

        return R.ok()
                .code(25000)
                .message("支付中");
    }

    @PostMapping("/createPayLog")
    public R createPayLog(@RequestBody PayLog tPayLog) {
        boolean save = payLogService.save(tPayLog);
        return save ? R.ok() : R.error();
    }

    @GetMapping("/getPayLogList")
    public R getPayLogList() {
        List<PayLog> payLogs = payLogService.list(null);
        return R.ok()
                .data("items", payLogs);
    }

    @GetMapping("/getPayLog/{id}")
    public R getPayLog(@PathVariable("id") String id) {
        PayLog payLog = payLogService.getById(id);
        return R.ok()
                .data("payLog", payLog);
    }
}

