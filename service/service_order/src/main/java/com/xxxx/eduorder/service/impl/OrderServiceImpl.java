package com.xxxx.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.commonutils.ordervo.CourseWebVoOrder;
import com.xxxx.commonutils.ordervo.UcenterMemberOrder;
import com.xxxx.eduorder.client.EduClient;
import com.xxxx.eduorder.client.UcenterClient;
import com.xxxx.eduorder.entity.Order;
import com.xxxx.eduorder.mapper.OrderMapper;
import com.xxxx.eduorder.service.OrderService;
import com.xxxx.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrders(String courseId, String memberId) {
        CourseWebVoOrder courseInfo = eduClient.getFrontCourseInfoOrder(courseId);
        UcenterMemberOrder memberInfo = ucenterClient.getMemberInfoByIdOrder(memberId);
        Order Order = new Order();
        String orderNo = OrderNoUtil.getOrderNo();
//设置订单ID
        Order.setOrderNo(orderNo);

        Order.setCourseId(courseId);
        Order.setCourseTitle(courseInfo.getTitle());
        Order.setCourseCover(courseInfo.getCover());
        Order.setTeacherName(courseInfo.getTeacherName());
        Order.setTotalFee(courseInfo.getPrice());

        Order.setMemberId(memberId);
        Order.setNickname(memberInfo.getNickname());
        Order.setMobile(memberInfo.getMobile());


//       设置支付类型
        Order.setPayType(1);
        Order.setStatus(0);

        baseMapper.insert(Order);
        return orderNo;
    }

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        Integer integer = baseMapper.selectCount(wrapper);
        return integer > 0;
    }
}
