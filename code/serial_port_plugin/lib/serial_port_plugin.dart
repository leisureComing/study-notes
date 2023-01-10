import 'dart:async';

import 'package:flutter/services.dart';

class SerialPortPlugin {
  static const MethodChannel _method = const MethodChannel('serial_port_plugin/method');
  static const EventChannel _event = const EventChannel('serial_port_plugin/event');

  static Future<String> get platformVersion async {
    final String version = await _method.invokeMethod('getPlatformVersion');
    return version;
  }

  /// 获取串口路径列表
  static Future<List<String>> get getAllDevicesPath async {
    final List<Object?> tempList = await _method.invokeMethod('getAllDevicesPath');
    final List<String> devList = [];
    tempList.forEach((element) {
      devList.add(element.toString());
    });
    return devList;
  }

  /// 打开串口
  /// [path]串口路径; [baudRate]波特率
  static Future<bool> open(String path, {int baudRate = 115200}) async {
    return await _method.invokeMethod('open', {
      "path": path,
      "baudRate": baudRate
    });
  }

  /// 关闭串口
  static void get close {
    _method.invokeMethod('close');
  }

  /// 发送数据
  static void send(String message) {
    _method.invokeMethod('send', {
      "message": message
    });
  }

  /// 事件监听
  /// event["size"]: 收到的数据长度
  /// event["data"]: 数据
  static void listen(void onData(data, size),{Function? onError}){
    _event.receiveBroadcastStream().listen(
      (event)=>onData(event["data"], event["size"]),
      onError: onError,
    );
  }
}


