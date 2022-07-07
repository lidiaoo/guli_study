package com.xxxx.eduservice.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer pid;
    private String name;
    private List<MenuTreeNode> child = new ArrayList<>();

    public void add(MenuTreeNode menuTreeNode) {
        child.add(menuTreeNode);
    }
}
