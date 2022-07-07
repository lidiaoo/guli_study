package com.xxxx.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:application.properties")
//@ConfigurationProperties("wx.open")
public class ConstantWxUtil implements InitializingBean {
    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;
    @Value("${wx.open.app_id}")
    private String appId;
    @Value("${wx.open.app_secret}")
    private String appSecret;
    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}