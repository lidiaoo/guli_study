package com.xxxx.eduorder.client;

import com.xxxx.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "service-ucenter")
public interface UcenterClient {
    @PostMapping("/educenter/member/getMemberInfoByIdOrder/{id}")
    UcenterMemberOrder getMemberInfoByIdOrder(@PathVariable("id") String id);
}
