package com.xxxx.educenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.commonutils.JwtUtils;
import com.xxxx.commonutils.MD5;
import com.xxxx.educenter.entity.UcenterMember;
import com.xxxx.educenter.entity.vo.LoginVo;
import com.xxxx.educenter.entity.vo.RegisterVo;
import com.xxxx.educenter.mapper.UcenterMemberMapper;
import com.xxxx.educenter.service.UcenterMemberService;
import com.xxxx.srvicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (ObjectUtils.isEmpty(mobile) || ObjectUtils.isEmpty(password)) {
            throw new GuliException(20001, "账号密码不能为空");
        }
        UcenterMember user = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (user == null) {
            throw new GuliException(20001, "该用户不存在");
        }
        if (user.getIsDisabled()) {
            throw new GuliException(20001, "该用户已被禁用");
        }
        if (!MD5.encrypt(password)
                .equals(user.getPassword())) {
            throw new GuliException(20001, "密码不正确");
        }
        String jwtToken = JwtUtils.getJwtToken(user.getId(), user.getNickname());
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("login_token_" + mobile, jwtToken, 2, TimeUnit.MINUTES);
        return jwtToken;
    }

    @Override
    public boolean register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        if (ObjectUtils.isEmpty(mobile) || ObjectUtils.isEmpty(nickname) || ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(code)) {
            throw new GuliException(20001, "不能为空");
        }
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (count > 0) {
            throw new GuliException(20001, "该用户已被注册");
        }
        UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(registerVo, member);
//        设置密码
        member.setPassword(MD5.encrypt(password));
        int insert = baseMapper.insert(member);
        return insert > 0;
    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer countRegisterByDay(String day) {
        Integer count = baseMapper.countRegisterByDay(day);
        System.out.println("数量: " + count);
        return count;
    }
}
