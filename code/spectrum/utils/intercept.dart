/// 截取器
class Intercept{
  /// 截取波长范围[335, 785]的数据
  /// 包含335和785
  /// 数据长度451个
  static List<num> intercept_335WL_785WL(Map<String, List<num>> source){
    List<num> x = source["x"]!;
    List<num> y = source["y"]!;
    int startIndex = x.indexOf(335);
    int endIndex = x.indexOf(785);

    List<num> result = List.of(y.getRange(startIndex, endIndex + 1));
    return result;
  }
}