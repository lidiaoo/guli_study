package com.xxxx.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.eduservice.entity.EduSubject;
import com.xxxx.eduservice.entity.SubjectData;
import com.xxxx.eduservice.entity.vo.EduSubjectTreeNode;
import com.xxxx.eduservice.listener.SubjectExcelListener;
import com.xxxx.eduservice.mapper.EduSubjectMapper;
import com.xxxx.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-26
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService))
                     .sheet()
                     .doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EduSubjectTreeNode> getSubjectTree() {
        List<EduSubject> menuList = baseMapper.selectList(null);
//        转json
//        String s = new JsonMapper().writeValueAsString(query);
//        System.out.println("json:" + s);
        System.out.println("--------------------------");


//        进行bean转换  在原有基础上增加个children变量
        List<EduSubjectTreeNode> menuTreeNodes = new ArrayList<>();
        for (EduSubject menu : menuList) {
            EduSubjectTreeNode menuTreeNode = new EduSubjectTreeNode();
            BeanUtils.copyProperties(menu, menuTreeNode);
            menuTreeNodes.add(menuTreeNode);
        }
        System.out.println("新nodes");
        System.out.println(menuTreeNodes);
        System.out.println("-----");

//        执行方法
//        设置参数 根id
        Object pid = "0";


        pid = pid.toString();
        List<EduSubjectTreeNode> tree = createTree(pid, menuTreeNodes);

//输出json
//        String s = null;
//        try {
//            s = new JsonMapper().writeValueAsString(tree);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        System.out.println(s);
//        }

//        查看结果
        System.out.println("===================");
        System.out.println("查看结果: ");
        System.out.println(tree);
        return tree;
    }

    private List<EduSubjectTreeNode> createTree(Object pid, List<EduSubjectTreeNode> menuTreeNodes) {
////        参数情况
//        System.out.println("参数情况");
//        System.out.println("pid: " + pid);
//        System.out.println("list: " + menuTreeNodes);
        List<EduSubjectTreeNode> treeNodeList = new ArrayList<>();
        for (EduSubjectTreeNode treeNode : menuTreeNodes) {
            if (pid.equals(treeNode.getParentId())) {
//                System.out.println("判断" + pid);
                treeNodeList.add(treeNode);
                treeNode.setChildren(createTree(treeNode.getId(), menuTreeNodes));
            }
        }
        return treeNodeList;
    }
}
