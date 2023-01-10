package com.lmy.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * 将对象组合成树形结构以表示"部分-整体"的层次结构。组合模式使得用户对单个对象和组合对象的使用具有一致性。
 * 包含自己对象组的类；
 */
public class CompositeDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        println("==组合模式==");
        Organization university1 = new Organization("国防科技大学");
        Organization university2 = new Organization("湖北文理学院");
        Organization college1 = new Organization("计算机学院");
        Organization college2 = new Organization("音乐学院");
        Organization subject1 = new Organization("英语");
        Organization subject2 = new Organization("计算机网络");
        Organization subject3 = new Organization("音乐");

        university1.add(college1);
        university1.add(college2);
        college1.add(subject1);
        college1.add(subject2);
        college2.add(subject1);
        college2.add(subject3);
        university2.add(college1);
        university1.show();
        university2.show();
    }
}


/**
 * 抽象类
 */
class Organization {
    private List<Organization> Organizations = new ArrayList<>();

    private String name;

    public Organization(String name) {
        this.name = name;
    }

    public void add(Organization organization) {
        Organizations.add(organization);
    }

    public void show() {
        System.out.println("==" + name + "==");
        for (Organization temp : Organizations) {
            System.out.println("==" + temp.name + "==");
            for(Organization temp2 : temp.Organizations){
                System.out.println(temp2.name);
            }

        }
    }
}