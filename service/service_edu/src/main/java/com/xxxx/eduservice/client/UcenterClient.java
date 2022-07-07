package com.xxxx.eduservice.client;

import com.xxxx.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@FeignClient(value = "service-ucenter", fallback = UcenterClientFallBackImpl.class)
public interface UcenterClient {
    @GetMapping("/educenter/member/getMemberInfoByMobile/{mobile}")
    R getMemberInfoByMobile(@PathVariable("mobile") String mobile);

    @GetMapping("/educenter/member/getMemberInfo")
    R getMemberInfo(HttpServletRequest request);

    @GetMapping("/educenter/member/getMemberInfoByHeader")
    Map<String, String> getMemberInfoByHeader(@RequestHeader("token") String token);
}
