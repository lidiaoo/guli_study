package com.xxxx.eduservice.client;

import com.xxxx.commonutils.R;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class UcenterClientFallBackImpl implements UcenterClient {
    @Override
    public R getMemberInfoByMobile(String mobile) {
        return null;
    }

    @Override
    public R getMemberInfo(HttpServletRequest request) {
        return null;
    }

    @Override
    public Map<String, String> getMemberInfoByHeader(String token) {
        return null;
    }

}
