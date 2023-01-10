
import 'dart:async';

import 'package:flutter/services.dart';

class GpioPlugin {
  static const MethodChannel _method = const MethodChannel('gpio_plugin/method');
  static const EventChannel _event = const EventChannel("gpio_plugin/event");

  static Future<String> get platformVersion async {
    final String version = await _method.invokeMethod('getPlatformVersion');
    return version;
  }

  /// 初始化蜂鸣器和按钮
  static Future<bool> get initBeepButton async {
    final bool result = await _method.invokeMethod('initBeepButton');
    return result;
  }

  /// 初始化蜂鸣器和按钮
  static Future<bool> get uninitBeepButton async {
    final bool result = await _method.invokeMethod('uninitBeepButton');
    return result;
  }

  /// 开启蜂鸣器
  static Future<bool> get beepRing async {
    final bool result = await _method.invokeMethod('beepRing');
    return result;
  }

  /// 开启定时器
  static Future<bool> get openTimer async {
    final bool result = await _method.invokeMethod('openTimer');
    return result;
  }

  /// 拉高探头电位
  static Future<bool> get probeHigh async {
    final bool result = await _method.invokeMethod('probeHigh');
    return result;
  }

  /// 拉低探头电位
  static Future<bool> get probeLow async {
    final bool result = await _method.invokeMethod('probeLow');
    return result;
  }

  /// 关闭定时器
  static Future<bool> get closeTimer async {
    final bool result = await _method.invokeMethod('closeTimer');
    return result;
  }

  /// 监听按钮的按下
  static Stream get buttonBeepRing {
    return _event.receiveBroadcastStream();
  }

}
