import 'dart:math' as math;

/// 波长转化器
class LightWave {
  /// 探头校准系数
  final ProbeFactor _probeFactor = ProbeFactor();

  /// 光谱数据最大个数[256]
  /// 光谱仪约定俗成的常量
  final _maxLen = 256;

  /// 波长列表
  final List<num> _wavelength = [];

  /// 获取波长
  static List<num> getWavelength() {
    return LightWave._()._orderCalculate();
  }

  LightWave._() {}

  /// 按顺序进行计算
  List<num> _orderCalculate() {
    
    _calculateWavelength();

    _removePoint();
    return _wavelength;
  }

  /// 计算波长
  /// 计算公式：A0 + B1 * i + B2 * i^2 + B3 * i^3 + B4 * i^4 + B5 * i^5
  void _calculateWavelength() {
    for (int i = 0; i < _maxLen; i++) {
      final result = _probeFactor.A0 +
          _probeFactor.B1 * i +
          _probeFactor.B2 * math.pow(i, 2) +
          _probeFactor.B3 * math.pow(i, 3) +
          _probeFactor.B4 * math.pow(i, 4) +
          _probeFactor.B5 * math.pow(i, 5);
      _wavelength.add(result);
    }
  }

  /// 四舍五入
  void _removePoint() {
    for (int i = 0; i < _wavelength.length; i++) {
      _wavelength[i] = _wavelength[i].round();
    }
  }
}

/// 探头校准系数类
/// 每个探头的校准系数都不相同
class ProbeFactor {
  /// 波长校准系数
  /// [A0][B1][B2][B3][B4][B5]
  /// 18L00897
  // final A0 = 3.143404084e+02;
  // final B1 = 2.421931127e+00;
  // final B2 = -1.382557946e-03;
  // final B3 = -8.390650837e-07;
  // final B4 = -1.491991402e-08;
  // final B5 = 3.771174922e-11;

  /// 18L00898
  // final A0 = 3.106892836E+02;
  // final B1 = 2.404067664E+00;
  // final B2 = -7.740398927E-04;
  // final B3 = -7.015516920E-06;
  // final B4 = 1.315194109E-09;
  // final B5 = -9.034892779E-12;

  /// 18L00899
  // final A0 = 3.114107545E+02;
  // final B1 =  2.420980344E+00;
  // final B2 =  -1.188258818E-0.3;
  // final B3 = -2.607310929E-06;
  // final B4 = -8.046220568E-09;
  // final B5 = 2.769810521E-11;

  /// 18L00900
  //  final A0 = 3.156326952E+02;
  //  final B1 = 2.407855433E+00;
  //  final B2 = -1.120996856E-03;
  //  final B3 = -2.886983717E-06;
  //  final B4 = -6.691565076E-09;
  //  final B5 = 2.441317697E-11;

  /// 18L00901
  // final A0 = 3.058697045E+02;
  // final B1 =  2.420520447E+00;
  // final B2 =  -1.143090590E-03;
  // final B3 = -3.333264784E-06 ;
  // final B4 = -3.270251989E-09;
  // final B5 = 1.785697616E-11 ;

  /// 18L00902
  // final A0 = 3.135169632E+02;
  // final B1 = 2.406731101E+00;
  // final B2 = -1.087231491E-03;
  // final B3 = -3.476940122E-06;
  // final B4 = -3.706087326E-09;
  // final B5 = 1.985578021E-11;

  // 19H01102
  // final A0 = 3.105220939E+02;
  // final B1 = 2.391016883E+00;
  // final B2 = -6.958356160E-04;
  // final B3 = -6.873323479E-06;
  // final B4 = 9.914476279E-09;
  // final B5 = -9.188676230E-13;

  // 19H01103
  // final A0 = 3.084945578E+02;
  // final B1 = 2.405548357E+00;
  // final B2 = -9.619929967E-04;
  // final B3 = -4.354091060E-06;
  // final B4 = -1.254923726E-09;
  // final B5 = 1.724428572E-11;

  // 19H01104
  // final A0 = 3.121042010E+02;
  // final B1 = 2.398357268E+00;
  // final B2 = -9.407058719E-04;
  // final B3 = -4.354091060E-06;
  // final B4 = -1.254923726E-09;
  // final B5 = 1.724428572E-11;

  // 19H01106
  final A0 = 3.081510720E+02;
  final B1 = 2.408858993E+00;
  final B2 = -1.125069994E-03;
  final B3 = -2.355804874E-06;
  final B4 = -1.057304071E-08;
  final B5 = 3.245101010E-11;

  // // 19H01107
  // final A0 = 3.100381851E+02;
  // final B1 = 2.393754291E+00;
  // final B2 = -9.453397624E-04;
  // final B3 = -3.846051245E-06;
  // final B4 = -4.491256896E-09;
  // final B5 = 2.326580423E-11;

  // 19H01109
//  final A0 = 3.059872583E+02;
//  final B1 = 2.407863370E+00;
//  final B2 = -9.670409066E-04;
//  final B3 = -4.049735041E-06;
//  final B4 = -2.875173227E-09;
//  final B5 = 1.956810334E-11;
}
