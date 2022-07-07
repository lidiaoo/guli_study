package com.xxxx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.xxxx.eduservice.entity.Menu;
import com.xxxx.eduservice.entity.vo.MenuTreeNode;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Jdbc {

    public static void main(String[] args) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
//        数据库我瞎掰的
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8");
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("root");
        jdbcTemplate.setDataSource(hikariDataSource);

//        查数据
        List<Menu> menuList = jdbcTemplate.query("select * from t_menu", new BeanPropertyRowMapper<>(Menu.class));
        System.out.println("查得数据库内容: ");
        System.out.println(menuList);

//        转json
//        String s = new JsonMapper().writeValueAsString(query);
//        System.out.println("json:" + s);

        System.out.println("--------------------------");
//        根id
        int pid = 0;

//        进行bean转换  在原有基础上增加个children变量
        List<MenuTreeNode> menuTreeNodes = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuTreeNode menuTreeNode = new MenuTreeNode();
            BeanUtils.copyProperties(menu, menuTreeNode);
            menuTreeNodes.add(menuTreeNode);
        }
        System.out.println("新nodes");
        System.out.println(menuTreeNodes);
        System.out.println("-----");

//        执行方法
        List<MenuTreeNode> tree = createTree(0, menuTreeNodes);

//        查看结果
        System.out.println("===================");
        System.out.println("查看结果: ");
        String s = new JsonMapper().writeValueAsString(tree);
        System.out.println(s);
    }

    private static List<MenuTreeNode> createTree(int pid, List<MenuTreeNode> menuTreeNodes) {
        List<MenuTreeNode> treeNodeList = new ArrayList<>();
        for (MenuTreeNode treeNode : menuTreeNodes) {
            if (pid == treeNode.getPid()) {
                treeNodeList.add(treeNode);
                treeNode.setChild(createTree(treeNode.getId(), menuTreeNodes));
            }
        }
        return treeNodeList;
    }
}
