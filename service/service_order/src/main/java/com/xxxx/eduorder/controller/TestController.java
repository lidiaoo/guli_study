package com.xxxx.eduorder.controller;

import com.xxxx.eduorder.client.EduClient;
import com.xxxx.eduorder.client.UcenterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
//@CrossOrigin
public class TestController {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @GetMapping("/test1")
    public String test1() {

        return null;
    }
}
