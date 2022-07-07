package com.xxxx.eduservice.controller;

import com.xxxx.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
public class EduLoginController {
    @PostMapping("/login")
    public R login(String username, String password) {
        return R.ok()
                .data("token", "admin");
    }

    @GetMapping("/info")
    public R info(String token) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("roles", Arrays.asList("admin"));
        stringObjectHashMap.put("name", "admin");
        stringObjectHashMap.put("avatar", "null");
        return R.ok()
                .data(stringObjectHashMap);
    }
}
