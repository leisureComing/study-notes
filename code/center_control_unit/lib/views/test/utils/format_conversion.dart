/// 数据格式转换工具
class FormatConversion {

  /// 16进制字符串数组（没有0x前缀）转成int数组
  static List<int> strArray2IArray(List<String> strDate) {
    List<int> target = [];
    for (int i = 0; i < strDate.length; i++) {
      target.add(hexToInt(strDate[i]));
    }
    return target;
  }


  /// 16进制字符串（没有0x前缀）转成int
  static int hexToInt(String hex) {
    int val = 0;
    int len = hex.length;
    for (int i = 0; i < len; i++) {
      int hexDigit = hex.codeUnitAt(i);
      if (hexDigit >= 48 && hexDigit <= 57) {
        val += (hexDigit - 48) * (1 << (4 * (len - 1 - i)));
      } else if (hexDigit >= 65 && hexDigit <= 70) {
        // A..F
        val += (hexDigit - 55) * (1 << (4 * (len - 1 - i)));
      } else if (hexDigit >= 97 && hexDigit <= 102) {
        // a..f
        val += (hexDigit - 87) * (1 << (4 * (len - 1 - i)));
      } else {
        throw new FormatException("Invalid hexadecimal value");
      }
    }
    return val;
  }

  /// int数组转16进制String数组
  static List<String> iArray2HexStringArray(List<int> intArray){
    List<String> target = [];
    for(int i = 0; i < intArray.length; i++){
      target.add(intToHex(intArray[i]));
    }
    return target;
  }


  /// 数字转16进制字符串
  /// 指定长度的[hex]为[2]不够补零
  static String intToHex(int num) {
    return num.toRadixString(16).padLeft(2, "0").toUpperCase();
  }
}