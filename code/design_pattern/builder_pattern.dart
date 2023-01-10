/// Time: 2021-12-07
/// By Leisure

/// Builder Pattern
/// 创建型模式
/// 定义：将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示；
/// 
/// 个人理解
/// 建造者模式有很多的实现方式，只要符合定义的都可以称之为建造者模式；
/// 实现方式在符合定义的同时，是否遵循设计原则就看个人的意愿；
/// 在传统的建造者模式遵循了设计原则；
/// 所以传统的实现方式上分了四个角色：
/// Product：最终要生成的对象；
/// Builder：构建者的抽象基类；
/// ConcreteBuilder：Builder的实现类；
/// Director：决定如何构建最终产品的算法；

/// 盖房子例子
/// 两种类型：普通房子、别墅
/// 盖房流程：打地基、砌墙、封顶；
/// 盖房流程一样的都要先打地基、砌墙、封顶；但普通房子和别墅地基、砌墙、封顶都不一样。

/// 房子
/// Product：最终要生成的对象
class House {
  /// 打地基
  String _foundation = "打地基";
  /// 砌墙
  String _wall = "砌墙";
  /// 封顶
  String _capping = "封顶";

  void setFoundation(String foundation)=>this._foundation = foundation;

  void setWall(String wall)=>this._wall = wall;

  void setCapping(String capping)=>this._capping = capping;

  void housePrint(){
    print("$_foundation - $_wall - $_capping");
  }
}

/// 盖房子抽象类
/// Builder：构建者的抽象基类
abstract class AbstractHouseBuilder {
  void layingFoundation();
  void buildWall();
  void capping();
  House buildHouse();
}

/// 盖普通房子
/// ConcreteBuilder：Builder的实现类
class  OrdinaryHouseBuilder implements AbstractHouseBuilder {
  /// 组合房子对象
  House _house;

  OrdinaryHouseBuilder(){
    _house = House();
  }

  @override
  void buildWall() {
    _house.setWall("普通房子砌墙");
  }

  @override
  void capping() {
    _house.setCapping("普通房子封顶");
  }

  @override
  void layingFoundation() {
    _house.setFoundation("普通房子打地基");
  }

  @override
  House buildHouse()=>_house;
}

/// 盖别墅
/// ConcreteBuilder：Builder的实现类
class  VillaHouseBuilder implements AbstractHouseBuilder {
  /// 组合房子对象
  House _house;

  VillaHouseBuilder(){
    _house = House();
  }

  @override
  void buildWall() {
    _house.setWall("别墅砌墙");
  }

  @override
  void capping() {
    _house.setCapping("别墅封顶");
  }

  @override
  void layingFoundation() {
    _house.setFoundation("别墅打地基");
  }

  @override
  House buildHouse()=>_house;
}

/// 盖房指挥者
/// Director：决定如何构建最终产品的算法；
class  HouseDirector {
  /// 组合构建者抽象类
  AbstractHouseBuilder _abstractHouseBuilder;

  HouseDirector(AbstractHouseBuilder abstractHouseBuilder){
    this._abstractHouseBuilder = abstractHouseBuilder;
  }

  /// 盖房流程
  House buildHouse(){
    // 打地基
    _abstractHouseBuilder.layingFoundation();
    // 砌墙
    _abstractHouseBuilder.buildWall();
    // 封顶
    _abstractHouseBuilder.capping();
    return _abstractHouseBuilder.buildHouse();
  }
}


void main(List<String> args) {
  // 普通房子
  OrdinaryHouseBuilder ordinaryHouseBuilder = OrdinaryHouseBuilder();
  HouseDirector houseDirector1 = HouseDirector(ordinaryHouseBuilder);
  House house1 = houseDirector1.buildHouse();
  house1.housePrint();

  // 别墅房子
  VillaHouseBuilder illaHouseBuilder = VillaHouseBuilder();
  HouseDirector houseDirector2 = HouseDirector(illaHouseBuilder);
  House house2 = houseDirector2.buildHouse();
  house2.housePrint();
}