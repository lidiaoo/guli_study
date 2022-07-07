package com.xxxx.eduservice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Menu implements Serializable {
    private Integer id;
    private Integer pid;
    private String name;
}
