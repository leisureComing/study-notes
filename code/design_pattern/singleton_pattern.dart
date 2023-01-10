/// Time: 2021-12-06
/// By Leisure

/// Singleton Pattern
/// 创建型模式
/// 定义：保证一个类仅有一个实例，并提供一个访问它的全局访问点；
/// 
/// 个人理解
/// 外部无法创建其对象，由其内部创建；
/// 何时何地何人使用都是同一个对象；
/// 目的：一个全局使用的类频繁地创建与销毁；当您想控制实例数目，节省系统资源的时候；
/// 注意：dart是单线程语言，所以没有线程安全要考虑


/// (1) 饿汉式
class Singleton1{
  static final Singleton1 _instance = Singleton1._();

  /// 命名构造函数私有化
  Singleton1._(){}

  static Singleton1 getInstance()=> _instance;
}


/// (2) 懒汉式
class Singleton2{
  static Singleton2 _instance;

  Singleton2._(){}
  
  static Singleton2 getInstance(){
    if(null == _instance){
      _instance = Singleton2._();
    }
    return _instance;
  }
}