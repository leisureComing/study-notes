import 'dart:async';

import 'package:center_control_unit/utils/htlc.dart';
import 'package:center_control_unit/views/test/utils/format_conversion.dart';
import 'package:center_control_unit/views/widgets/fl_tocast.dart';
import 'package:flutter/material.dart';

class BlueTestProvider with ChangeNotifier {
  // 数据包开头和结尾标志
  final int _7E = 0x7E;

  /// 定时器
  Timer? _timer;

  /// 启动定时器
  void startTimer() {
    _timer ??= Timer(const Duration(milliseconds: 1000), () {
        print("定时器回调函数");
        FlToast.primaryToastErr("定时器时间到！");

        /// 复位定时器
        _timer = null;
      });
  }

  /// 关闭定时器
  void cancel() {
    _timer?.cancel();
    _timer = null;
  }

  // 收发的命令日志
  final List<String> _allLogs = [];

  // 底层数据流收发
  final List<String> _dataLogs = [];

  // 保存日志
  set setAllLogs(String log) {
    _allLogs.insert(0, log);
    notifyListeners();
  }

  // 获取日志
  List<String> get allLogs => _allLogs;

  // 保存底层数据流
  set setDataLogs(String dataLog) {
    dataLogs.insert(0, dataLog);
    notifyListeners();
  }

  // 获取底层数据流
  List<String> get dataLogs => _dataLogs;

  /// 监听蓝牙数据
  void listen(List<int> values) {
    print("接收到的蓝牙数据: $values");
    // 取消定时器
    cancel();

    if (values.first != _7E && values.last != _7E) {
      FlToast.primaryToastErr("接受到的数据包错误！将丢弃！");
      return;
    }

    // 数据包处理
    List<int> inputData = HDLC.input(values);
    if (inputData.isEmpty) {
      FlToast.primaryToastErr("接收数据包验证CRC错误！将丢弃！");
      return;
    }

    // 保存日志
    setAllLogs = "跟据接收到的命令进行日志的表述";

    var now = new DateTime.now();
    // 保存收到的底层数据流
    setDataLogs = "[RX][$now]" +
        FormatConversion.iArray2HexStringArray(inputData).toString();
  }
}
