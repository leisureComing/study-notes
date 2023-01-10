/// Time: 2021-12-09
/// By Leisure

/// Adapter Pattern
/// 结构型模式
/// 定义：将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作;
/// 
/// 个人理解
/// 将一个类的接口（被适配者）转换成客户希望的另外一个接口（目标接口）；
/// 适配器分为两类：对象适配器（推荐）、类适配器；
/// 对象适配器
/// 实现目标接口，组合被适配者接口；
/// 
/// 类适配器
/// 同时继承目标接口、被适配者接口；
/// 注意：java不支持多继承，可以使用继承、实现两者配合使用，这要求被适配者、目标接口至少有一个是接口；

/// 例子
/// 220V电压给5V电压的用电器充电


/// --------------------对象适配器---------------
/// 220V电压接口（被适配者）
class I220V {
  int output(){
    return 220;
  }
}

/// 5V电压接口（目标接口）
class I5V {
  void input(){
    print("充电中...");
  }
}

class AdapterI5V extends I5V{
  I220V _i220V;

  AdapterI5V(I220V i220V){
    this._i220V = i220V;
  }

  @override
  void input() {
    int v = _i220V.output() ~/ 44;
    if(v == 5){
      super.input();
    }
  }
}
/// --------------------对象适配器---------------


/// --------------------类适配器---------------
/// 220V电压接口（被适配者）
class IClass220V {
  int output(){
    return 220;
  }
}

/// 5V电压接口（目标接口）
class IClass5V {
  void input(){
    print("充电中...");
  }
}

class AdapterIClass5V extends IClass5V with IClass220V{
  @override
  void input() {
    int v = this.output() ~/ 44;
    if(v == 5){
      super.input();
    }
  }
}
/// --------------------类适配器---------------

/// 客户端
void main(List<String> args) {

  print("对象适配器");
  AdapterI5V dapterI5V = AdapterI5V(I220V());
  dapterI5V.input();

  print("类适配器");
  AdapterIClass5V adapterIClass5V = AdapterIClass5V();
  adapterIClass5V.input();
}