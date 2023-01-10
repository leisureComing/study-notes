/// 平滑器
class Smooth{

  /// 每[4]个平均得到第一个值；并向下滑动[1]个等到下一个数据
  /// 直到最后<[4]个数据时，由其自身的长度进行平均
  /// 如：
  /// 只有[3]个时，分母为[3];
  /// 只有[2]个时，分母为[2];
  /// 只有[1]个时，分母为[1];
  static List<num> smoothed(List<num> source){
    List<num> target = [];
    for(int i = 0; i < source.length; i++){
      if((4 + i) <= source.length){
        double temp = 0;
        for(int j = 0; j < 4; j++){
          temp += source[i + j];
        }
        target.add(temp / 4);
        continue;
      }
      double temp = 0;
      for(int k = 0; k < (source.length - i); k++){
        temp += source[i + k]; 
      }
      target.add(temp / (source.length - i));
    }
    return target;
 }
}