/// 插值器（线性插值）
/// 插值[x]相邻的间隔为1
/// 插值[y]取整
class Interpolation{
  static Map<String, List<num>> interpolated(List<num> x, List<num> y){
    List<num> targetX = [];
    List<num> targetY = [];

    for(int i = 0; i < x.length - 1; i++){
      targetX.add(x[i]);
      targetY.add(y[i]);

      num cur = x[i];
      num next = x[i+1];
      while(next-cur != 1){
        num tempY = y[i] + ((y[i+1] - y[i]) / (x[i+1] - x[i]))*(++cur - x[i]);
        targetX.add(cur);
        targetY.add(tempY);
      }
    }
    // 添加最后一个
    targetX.add(x.last);
    targetY.add(y.last);
    return {
      "x": targetX,
      "y": targetY,
    };
  }
}