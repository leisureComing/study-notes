/// Time: 2021-12-03
/// By Leisure

/// Abstract Factory Pattern
/// 创建型模式
/// 分为两大模块：抽象工厂、工厂
/// 抽象工厂创建工厂对象
/// 工厂创建产品
/// 所以又称工厂的工厂
/// 缺点，很难增加产品簇。每增加一个产品簇，就要从抽象工厂更改代码，且其实现类也要更改代码；
/// 优点，很容易增加产品。每增加一个产品，只需要更改对应的产品工厂就行了。
abstract class AbstractFactory{
  Shape getShape(ShapeType shapeType);
  Color getColor(ColorType colorType);
}

/// 形状枚举
enum ShapeType{
  rectangle,
  square
}
class ShapeFactory implements AbstractFactory{
  @override
  Color getColor(ColorType colorType) {
    return null;
  }

  @override
  Shape getShape(ShapeType shapeType) {
    switch(shapeType){
      case ShapeType.rectangle:
        return Rectangle();
      case ShapeType.square:
        return Square();
    }
    return Rectangle();
  }  
} 

/// 颜色枚举
enum ColorType{
  blue,
  red
}
class ColorFactory implements AbstractFactory{
  @override
  Color getColor(ColorType colorType) {
    switch(colorType){
      case ColorType.blue:
        return Blue();
      case ColorType.red:
        return Red();
    }
    return Blue();
  }

  @override
  Shape getShape(ShapeType shapeType) {
    return null;
  }
}

/// 形状产品簇
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


/// 颜色产品簇
abstract class Color {
  void fill();
}

class Blue implements Color{
  @override
  void fill() {
    print("fill Blue");
  }
}

class Red implements Color{
  @override
  void fill() {
    print("fill Red");
  }
}


/// 工厂枚举
enum FactoryType{
  colorFactory,
  shapeFactory
}
/// 工厂创造器/生成器
class FactoryProducer{
  AbstractFactory getFactory(FactoryType factoryType){
    switch(factoryType){
      case FactoryType.colorFactory:
        return ColorFactory();
      case FactoryType.shapeFactory:
        return ShapeFactory();
    }
    return ColorFactory();
  }
}

void main(List<String> args) {
  AbstractFactory abstractFactory1 = FactoryProducer().getFactory(FactoryType.colorFactory);
  Color color = abstractFactory1.getColor(ColorType.blue);
  color.fill();

  AbstractFactory abstractFactory2 = FactoryProducer().getFactory(FactoryType.shapeFactory);
  Shape shape = abstractFactory2.getShape(ShapeType.rectangle);
  shape.draw();
}