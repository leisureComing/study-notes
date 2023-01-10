package com.lmy.factory;

/**
 * 简单工厂
 * 我们明确地计划不同条件下创建不同实例时
 * 工厂类没有继承抽象类或实现接口；有新增加的实例都会对现有工厂类进行修改
 * */
public class SimpleFactoryDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        println("==涂色==");
        IColor blue = SimpleFactory.fillBlue();
        blue.fill();

        IColor dark = SimpleFactory.fillDark();
        dark.fill();

        println("==绘形状==");
        IShape circle = SimpleFactory.drawCircle();
        circle.draw();

        IShape square = SimpleFactory.drawSquare();
        square.draw();
    }
}

/**
 * 简单工厂类
 * */
class SimpleFactory{
    /**
     * 涂blue
     * */
    static public IColor fillBlue(){
        return new Blue();
    }

    /**
     * 涂dark
     * */
    static public IColor fillDark(){
        return new Dark();
    }

    /**
     * 绘Circle
     * */
    static public IShape drawCircle(){
        return new Circle();
    }

    /**
     * 绘Square
     * */
    static public IShape drawSquare(){
        return new Square();
    }
}

