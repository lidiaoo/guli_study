package com.xxxx.eduservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Menu implements Serializable {
    private Integer id;
    private Integer pid;
    private String name;
}
