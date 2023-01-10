import 'dart:math';

/// 归一化器
class Normalization {
  /// 数据进行归一化
  static List<double> normalized(List<num> source) {
    List<double> target = [];
    double mMax = source.reduce(max).toDouble();
    double mMin = source.reduce(min).toDouble();
    double interval = mMax - mMin;

    for (int i = 0; i < source.length; i++) {
      target.add(((source[i] - mMin) / interval).isNaN
          ? 1.0
          : (source[i] - mMin) / interval);
    }
    return target;
  }
}
