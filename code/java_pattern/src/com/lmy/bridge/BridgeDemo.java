package com.lmy.bridge;

/**
 * 桥接模式
 * 将抽象部分与实现部分分离，使它们都可以独立的变化；
 * 这要求开发人员区分出抽象部分，实现部分；
 *
 * */
public class BridgeDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        IShape rectangle = new Rectangle(new RectangleAPI());
        rectangle.draw();

        IShape square = new Square(new SquareAPI());
        square.draw();
    }
}

/// ====================实现部分=======================
/**
 * 绘画接口
 * */
interface IDrawAPI{
    public void draw();
}



/**
 * 绘画Rectangle
 * */
class RectangleAPI implements IDrawAPI{

    @Override
    public void draw() {
        System.out.println("Drawing rectangle");
    }
}



/**
 * 绘画Square
 * */
class SquareAPI implements IDrawAPI{

    @Override
    public void draw() {
        System.out.println("Drawing square");
    }
}



/// ====================抽象部分=======================
/**
 * 形状抽象类
 * */
abstract class IShape{
    protected IDrawAPI iDrawAPI;
    protected IShape(IDrawAPI iDrawAPI){
        this.iDrawAPI = iDrawAPI;
    }
    public abstract void draw();
}



/**
 * Rectangle实现类
 * */
class Rectangle extends IShape{
    protected Rectangle(IDrawAPI iDrawAPI) {
        super(iDrawAPI);
    }

    @Override
    public void draw() {
        iDrawAPI.draw();
    }
}



/**
 * Square实现类
 * */
class Square extends IShape{

    protected Square(IDrawAPI iDrawAPI) {
        super(iDrawAPI);
    }

    @Override
    public void draw() {
        iDrawAPI.draw();
    }
}