package com.lmy.factory;


/**
 * 工厂
 * 同一类下，我们明确地计划不同条件下创建不同实例时
 * 定义一个创建对象的接口，让其子类自己决定实例化哪一个工厂类，工厂模式使其创建过程延迟到子类进行
 */
public class FactoryDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        println("==涂色==");
        IFactoryColor factoryBlue = new FactoryBlue();
        IColor blue = factoryBlue.fill();
        blue.fill();

        IFactoryColor factoryDark = new FactoryDark();
        IColor dark = factoryDark.fill();
        dark.fill();

        println("==绘形状==");
        IFactoryShape factoryCircle = new FactoryCircle();
        IShape circle = factoryCircle.draw();
        circle.draw();

        IFactoryShape factorySquare = new FactorySquare();
        IShape square = factorySquare.draw();
        square.draw();


    }
}


/**
 * 涂色工厂接口
 */
interface IFactoryColor {
    /**
     * 涂色
     */
    public IColor fill();
}


/**
 * 涂blue工厂类
 */
class FactoryBlue implements IFactoryColor {

    @Override
    public IColor fill() {
        return new Blue();
    }
}

/**
 * 涂dark工厂类
 */
class FactoryDark implements IFactoryColor {

    @Override
    public IColor fill() {
        return new Dark();
    }
}


/// =======================另一类========================
/**
 * 绘形状工厂接口
 */
interface IFactoryShape {
    /**
     * 涂色
     */
    public IShape draw();
}

/**
 * 绘circle工厂类
 */
class FactoryCircle implements IFactoryShape{

    @Override
    public IShape draw() {
        return new Circle();
    }
}

/**
 * 绘square工厂类
 */
class FactorySquare implements IFactoryShape{

    @Override
    public IShape draw() {
        return new Square();
    }
}