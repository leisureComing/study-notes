
enum SpectrumType{
  fluorescence,
  absorption,
  reflection
}

/// 校准器
class Calibration{
  /// 光谱背景校准
  static List<num> backgroundCal(List<num> spectrumData, List<num> background, SpectrumType type){
    List<num> target = [];
    switch(type){
      case SpectrumType.fluorescence:
        //荧光：扣减
        for(int i = 0; i < background.length; i++){
          target.add((spectrumData[i] - background[i]));
        }
        break;
      case SpectrumType.absorption:
        //吸收光：相除
        for(int i = 0; i < background.length; i++){
          background[i] == 0 ?
          target.add(background[i]) :
          target.add(spectrumData[i] / (background[i]));
        }
        break;
      case SpectrumType.reflection:
        //反射谱，不用校准
        target = spectrumData;
        break;
    }
    return target;
  }

  static SpectrumType getType(String type){
    switch (type) {
      case "荧光谱":
        return SpectrumType.fluorescence;
      case "吸收谱":
        return SpectrumType.absorption;
      case "反射谱":
        return SpectrumType.reflection;
    }
    return SpectrumType.reflection;
  }
}