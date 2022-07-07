package com.xxxx.vodservice;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyTest {

    @Test
    public void t1() {
        System.out.println("hello");
        List list = new ArrayList();
        Collections.addAll(list, "1", "2", "3", "4", "5");
        System.out.println("list1: " + list);
        String join = String.join("-", list);
        System.out.println(join);
        list.forEach(System.out::println);
    }

}
