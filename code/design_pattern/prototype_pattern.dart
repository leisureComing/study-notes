/// Time: 2021-12-08
/// By Leisure

/// Prototype Pattern
/// 创建型模式
/// 定义：用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象；
/// 
/// 个人理解
/// 从定义上理解分为两个部分；
/// （1）原型实例给出创建接口；
/// （2）创建接口对原型实例进行克隆；
/// 
/// 克隆分为：浅克隆、深克隆
/// 浅克隆：对原型实例基本类型进行复制出另一份，而引用类型（数组、自定义类）只是复制引用（地址）；
/// 深克隆：对原型实例基本类型、引用类型（数组、自定义类）都进行复制出另一份；
/// 
/// java中有[Cloneable]克隆接口可以实现、dart中没有克隆接口可实现；
/// 所以dart的克隆接口要自己定义，并实现克隆方法；

/// 克隆接口
abstract class  Cloneable {
  Object clone();
}

/// 需要克隆的要实现克隆接口
class Dog implements  Cloneable{
  final String _name;
  final String _type;

  Dog(this._name, this._type);

  @override
  String toString() {
    return "$_name-$_type";
  }

  @override
  Object clone() {
    Dog dog = Dog(this._name, this._type);
    return dog;
  }
  
}

void main(List<String> args) {
  Dog dog = Dog("大黄", "田园犬");
  Dog clone1 = dog.clone();

  print("hashCode: ${dog.hashCode} - ${dog.toString()}");
  print("hashCode: ${clone1.hashCode} - ${clone1.toString()}");
}