package com.xxxx.vodservice.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InitVodClient implements InitializingBean {
    public static String ACCESSKEYID;
    public static String ACCESSKEYSECRET;
    @Value("${aliyun.vod.file.keyid}")
    private String keyid;
    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    public static DefaultAcsClient getClient() {
        //        获取Client
        String regionId = "cn-shanghai";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ACCESSKEYID, ACCESSKEYSECRET);
        return new DefaultAcsClient(profile);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESSKEYID = keyid;
        ACCESSKEYSECRET = keysecret;
    }
}
