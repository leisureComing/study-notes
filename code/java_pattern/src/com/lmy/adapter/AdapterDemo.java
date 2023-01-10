package com.lmy.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 适配器模式
 * 将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作
 * */
public class AdapterDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        println("==类适适配器==");
        IListInfo adapter1 = new Adapter1();
        adapter1.showListInfo();
        println("==对象适配器==");
        IMapInfo devInfo = new DevMapInfo();
        Adapter2 adapter2 = new Adapter2(devInfo);
        adapter2.showListInfo();
        println("==抽象适配器==");
        println("只实现了start方法");
        new Adapter3(){
            @Override
            public void start() {
                super.start();
            }
        };
        // 替代
//        new IAnimation(){
//
//            @Override
//            public void start() {
//
//            }
//
//            @Override
//            public void listen() {
//
//            }
//
//            @Override
//            public void stop() {
//
//            }
//        };

    }
}



/**
 * 类适适配器
 * 利用继承（java没有多继承）和实现；
 * 继承被适配的接口的同时实现目标接口，这就要求被适配的类是具体的实体类而目标接口是抽象接口；
 * 继承是强耦合的；
 * */
class Adapter1 extends DevMapInfo implements IListInfo{
    @Override
    public void showListInfo() {
        Map<String, String> temp = getMapInfo();

        List showList = new ArrayList();
        for(String item: temp.keySet()){
            showList.add(temp.get(item));
        }
        System.out.println(showList);
    }
}



/**
 * 对象适配器
 * 组合和聚合的方式代替继承；
 * */
class Adapter2 implements IListInfo{
    private IMapInfo info;

    public Adapter2(IMapInfo info){
        this.info = info;
    }

    @Override
    public void showListInfo() {

        if(info != null){
            Map<String, String> temp = info.getMapInfo();

            List showList = new ArrayList();
            for(String item: temp.keySet()){
                showList.add(temp.get(item));
            }
            System.out.println(showList);
        }
    }
}



/**
 * 抽象适配器
 * 只实现的接口方法之一时；
 * */
abstract class Adapter3 implements IAnimation{

    @Override
    public void start() {
        // 提供空实现
    }

    @Override
    public void listen() {
        // 提供空实现
    }

    @Override
    public void stop() {
        // 提供空实现
    }
}



/**
 * 接口
 * */
interface IAnimation{
    public void start();
    public void listen();
    public void stop();
}