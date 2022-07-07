package com.xxxx.msmservice.controller;

import com.xxxx.commonutils.R;
import com.xxxx.commonutils.RandomUtil;
import com.xxxx.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone) {
//        先查Redis命中
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String code = (String) ops.get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        String fourBitRandom = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", fourBitRandom);
//        设置Redis失效时间
        ops.set(phone, code, 5, TimeUnit.MINUTES);
        System.out.println("验证码是: " + fourBitRandom);
        boolean isDone = true;
//        boolean isDone = msmService.send(param, phone);
        return isDone ? R.ok() : R.error()
                                  .message("短信发送失败");
    }
}
