package com.xxxx.educenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.commonutils.JwtUtils;
import com.xxxx.commonutils.R;
import com.xxxx.commonutils.ordervo.UcenterMemberOrder;
import com.xxxx.educenter.entity.UcenterMember;
import com.xxxx.educenter.entity.vo.LoginVo;
import com.xxxx.educenter.entity.vo.RegisterVo;
import com.xxxx.educenter.service.UcenterMemberService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public R loginUser(@RequestBody LoginVo loginVo) {
        String token = ucenterMemberService.login(loginVo);
        if (!ObjectUtils.isEmpty(token)) {
            return R.ok()
                    .data("token", token);
        }
        return R.error();
    }

    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        boolean flag = ucenterMemberService.register(registerVo);
        return flag ? R.ok() : R.error();
    }

    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        return R.ok()
                .data("item", ucenterMember);
    }

    @GetMapping("/getMemberInfoByHeader")
    public Map<String, String> getMemberInfoByHeader(@RequestHeader("token") String token) {
        String id = JwtUtils.getMemberIdByToken(token);
        UcenterMember ucenterMember = ucenterMemberService.getById(id);

        Map<String, String> map = null;
        try {
            map = BeanUtils.describe(ucenterMember);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //    查询记录数
    @GetMapping("/getMemberByMobile/{mobile}")
    public R getMemberByMobile(@PathVariable("mobile") String mobile) {
        int count = ucenterMemberService.count(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        return count > 0 ? R.ok() : R.error();
    }

    //    查询对象
    @GetMapping("/getMemberInfoByMobile/{mobile}")
    public R getMemberInfoByMobile(@PathVariable("mobile") String mobile) {
        UcenterMember ucenterMember = ucenterMemberService.getOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        return R.ok()
                .data("member", ucenterMember);
    }

    //    根据ID查询对象
    @GetMapping("/getMemberInfoById/{id}")
    public R getMemberInfoById(@PathVariable("id") String id) {
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        return R.ok()
                .data("member", ucenterMember);
    }

    //    根据ID查询对象
    @PostMapping("/getMemberInfoByIdOrder/{id}")
    public UcenterMemberOrder getMemberInfoByIdOrder(@PathVariable("id") String id) {
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        org.springframework.beans.BeanUtils.copyProperties(ucenterMember, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //    根据日期查询注册人数
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day) {
        Integer count = ucenterMemberService.countRegisterByDay(day);
        return R.ok()
                .data("countRegister", count);
    }

    @GetMapping("testRedis")
    public R testRedis() {
        ValueOperations ops = redisTemplate.opsForValue();
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        RedisConnection connection = connectionFactory.getConnection();
        System.out.println(connection.ping());
        return null;
    }
}

