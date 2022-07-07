package com.xxxx;

import com.xxxx.commonutils.JwtUtils;

public class TestTokenInfo {
    public static void main(String[] args) {
        String token = JwtUtils.getJwtToken("1481961958168072193", "虫虫");
        System.out.println("token: " + token);

    }
}

