package com.lmy.factory;

/**
 * 抽象工厂
 * [提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类]
 * 系统的产品有多于一个的产品族，而系统只消费其中某一族的产品；
 * 产品簇：同一品牌下全部产品
 * 产品等级：不同品牌下的同一产品
 *
 * 例子：
 * 假设现有两个品牌的A，B；每个品牌能涂色[blue]和绘形状[circle]
 * 大概结构：
 *      涂blue
 * 品牌A{
 *      绘circle
 *
 *      涂blue
 * 品牌B{
 *      绘circle
 *
 *
 * 如果是另一种结构：
 *
 *      涂色[涂blue;涂dark...]
 * 品牌A{
 *      绘型[绘circle;绘square...]
 *
 *      涂色[涂blue;涂dark...]
 * 品牌B{
 *      绘型[绘circle;绘square...]
 * 则需要修改抽象工厂传入[工厂模式的接口]
 * 如：public IColor fill(IFactoryColor i);
 *
 * */
public class AbstractFactoryDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        println("==涂色==");
        IAbstractFactory factoryA = new FactoryA();
        IColor blueA = factoryA.fill();
        blueA.fill();

        IAbstractFactory factoryB = new FactoryB();
        IColor blueB = factoryB.fill();
        blueB.fill();

        println("==绘形状==");
        IShape circleA = factoryA.draw();
        circleA.draw();

        IShape circleB = factoryB.draw();
        circleB.draw();
    }
}



/**
 * 抽象工厂接口
 * */
interface IAbstractFactory{
    /**
     * 涂色
     * */
    public IColor fill();

    /**
     * 绘形状
     * */
    public IShape draw();
}



/**
 * 品牌A工厂类
 * */
class FactoryA implements IAbstractFactory{

    @Override
    public IColor fill() {
        return new BlueA();
    }

    @Override
    public IShape draw() {
        return new CircleA();
    }
}

/**
 * 品牌B工厂类
 * */
class FactoryB implements IAbstractFactory{

    @Override
    public IColor fill() {
        return new BlueB();
    }

    @Override
    public IShape draw() {
        return new CircleB();
    }
}



//=============================品牌A===================================
/**
 * 品牌A
 * 涂blue
 * */
class BlueA implements IColor{

    @Override
    public void fill() {
        System.out.println("A品牌filling blue");
    }
}

/**
 * 品牌A
 * 绘circle
 * */
class CircleA implements IShape{

    @Override
    public void draw() {
        System.out.println("A品牌drawing circle");
    }
}



//=============================品牌B===================================
/**
 * 品牌B
 * 涂blue
 * */
class BlueB implements IColor{

    @Override
    public void fill() {
        System.out.println("B品牌filling blue");
    }
}

/**
 * 品牌B
 * 绘circle
 * */
class CircleB implements IShape{

    @Override
    public void draw() {
        System.out.println("B品牌drawing circle");
    }
}