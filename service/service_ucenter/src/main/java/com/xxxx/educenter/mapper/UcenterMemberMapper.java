package com.xxxx.educenter.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.educenter.entity.UcenterMember;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterByDay(String day);
}
