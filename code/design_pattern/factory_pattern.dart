/// Time: 2021-10-29
/// By Leisure

/// Factory Pattern
/// 创建型模式
/// 定义：定义一个创建对象的接口，让其子类自己决定实例化哪一个工厂类，工厂模式使其创建过程延迟到子类进行；
/// 
/// 个人理解
/// 有多个同类型产品（对象）下，由用户自己决定创建那个产品（对象），且用户无需知道创建产品（对象）细节，
/// 创建细节由工厂决定；为保证产品用法一样的，所有产品必须实现同一个接口(抽象类)。
/// 对于用户来说是开闭原则[OCP]的体现。
abstract class Shape {
  void draw();
}

class Rectangle implements Shape{
  @override
  void draw() {
    print("drawing a Rectangle");
  }
}

class Square implements Shape{
  @override
  void draw() {
    print("drawing a Square");
  }
}

class Circle implements Shape{
  @override
  void draw() {
    print("drawing a Circle");
  }
}

/// 形状枚举
/// 像是菜单，让用户知道都有啥菜
enum ShapeType{
  rectangle,
  square,
  circle
}

/// 形状工厂
class ShapeFactory{

  /// 获取形状
  /// 默认返回[Rectangle]
  Shape getShape(ShapeType shapeType){
    switch(shapeType){
      case ShapeType.rectangle:
        return Rectangle();
      case ShapeType.square:
        return Square();
      case ShapeType.circle:
        return Circle();
    }
    return Rectangle();
  }
}