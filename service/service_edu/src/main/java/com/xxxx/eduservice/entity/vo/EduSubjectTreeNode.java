package com.xxxx.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EduSubjectTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String parentId;
    private List<EduSubjectTreeNode> children = new ArrayList<>();

    public void add(EduSubjectTreeNode node) {
        children.add(node);
    }
}
