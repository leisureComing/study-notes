/// HDLC工具
class HDLC {
  /// 发送数据添加：包头[7E]、CRC、包头[7E]
  static List<int> output(List<int> cmdPackage) {
    List<int> target = [];

    final int _7E = 0x7E;
    final int _7D = 0x7D;
    final List<int> _7D5E = [0x7D, 0x5E];
    final List<int> _7D5D = [0x7D, 0x5D];

    // CRC校验
    int crc = _crc16(cmdPackage);
    int high = (crc >> 8) & 0xFF;
    int low = crc & 0xFF;
    cmdPackage.add(low);
    cmdPackage.add(high);

    // 转译-碰到[7E]->[7D 5E]; [7D]->[7D 5D];
    for (int i = 0; i < cmdPackage.length; i++) {
      if (cmdPackage[i] == _7E) {
        target.addAll(_7D5E);
      } else if (cmdPackage[i] == _7D) {
        target.addAll(_7D5D);
      } else {
        target.add(cmdPackage[i]);
      }
    }

    // 恢复原本数据
    cmdPackage.removeLast();
    cmdPackage.removeLast();

    // 添加包头包尾7E
    target.insert(0, _7E);
    target.add(_7E);
    return target;
  }

  /// 接收数据除去：包头[7E]、CRC、包尾[7E]
  static List<int> input(List<int> dataPackage) {
    List<int> target = [];

    final int _7E = 0x7E;
    final int _7D = 0x7D;
    final int _5E = 0x5E;
    final int _5D = 0x5D;

    // 转译，不包含[包头包尾7E]
    for (int i = 1; i < dataPackage.length - 1; i++) {
      if (dataPackage[i] == _7D && dataPackage[i + 1] == _5E) {
        target.add(_7E);
      } else if (dataPackage[i] == _7D && dataPackage[i + 1] == _5D) {
        target.add(_7D);
      } else {
        target.add(dataPackage[i]);
      }
    }
    // 去除RCR位
    target.removeLast();
    target.removeLast();

    // 验证CRC
    int crc = _crc16(target);
    if (crc !=
        ((dataPackage[dataPackage.length - 2] << 8) +
            dataPackage[dataPackage.length - 3])) {
      print("CRC错误!");
      return [];
    }

    return target;
  }

  /// 计算CRC校验码
  static int _crc16(List<int> bytes) {
    int i, j, lsb;
    int h = 0xffff;
    for (i = 0; i < bytes.length; i++) {
      h ^= (bytes[i] & 0xff);
      h &= 0xffff;
      for (j = 0; j < 8; j++) {
        lsb = h & 0x0001;
        h >>= 1;
        if (lsb == 1) {
          h ^= 0x8408;
          h &= 0xffff;
        }
      }
    }
    h ^= 0xffff;
    return h;
  }
}
