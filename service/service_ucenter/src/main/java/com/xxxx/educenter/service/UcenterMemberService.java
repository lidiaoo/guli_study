package com.xxxx.educenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.educenter.entity.UcenterMember;
import com.xxxx.educenter.entity.vo.LoginVo;
import com.xxxx.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    String login(LoginVo loginVo);

    boolean register(RegisterVo registerVo);

    UcenterMember getByOpenid(String openid);

    Integer countRegisterByDay(String day);
}