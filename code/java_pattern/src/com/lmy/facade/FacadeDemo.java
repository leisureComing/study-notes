package com.lmy.facade;

/**
 * 外观模式
 * 提供了一个统一的接口，用来访问子系统中的一群接口；外观定义了一个高层接口，让子系统更统一使用。
 * <p>
 * 外观创建了一个接口简化而统一的类，用来包装子系统中一个或多个复杂的类；
 * 外观没有[封装]子系统的类，只提供简化的接口；客户如有必要也可以直接使用子系统的类；
 * 客户如果只针对外观类编程，可以将客户从组件的子系统中解耦；
 */
public class FacadeDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        // 客户端直接使用外观类来驱动各个子系统
        Facade facade = new Facade();
        facade.drawShape();

    }
}


/**
 * 形状接口
 */
interface IShape {
    void draw();
}


/**
 * 矩形
 */
class Rectangle implements IShape {

    @Override
    public void draw() {
        System.out.println("Rectangle::draw()");
    }
}


/**
 * 正方形
 */
class Square implements IShape {

    @Override
    public void draw() {
        System.out.println("Square::draw()");
    }
}


/**
 * 圆形
 */
class Circle implements IShape {

    @Override
    public void draw() {
        System.out.println("Circle::draw()");
    }
}


/**
 * 外观类
 * 来包装子系统：Rectangle、Square、Circle
 * 子系统之间的可能存在着复杂的关系；
 * */
class Facade{
    private IShape rectangle;
    private IShape square;
    private IShape circle;

    public Facade(){
        this.rectangle = new Rectangle();
        this.square = new Square();
        this.circle = new Circle();
    }


    public void drawShape(){
        rectangle.draw();
        square.draw();
        circle.draw();
    }
}