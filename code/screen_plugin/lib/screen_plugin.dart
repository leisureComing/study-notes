
import 'dart:async';

import 'package:flutter/services.dart';

class ScreenPlugin {
  static const MethodChannel _channel = const MethodChannel('com.jl.screenPlugin/method_screenPlugin');
  static const EventChannel _eventChannel = const EventChannel('com.jl.screenPlugin/event_screenPlugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// 通过事件通道EventChannel的receiveBroadcastStream方法接收广播事件流
  /// 获取关开屏通知
  static Stream<dynamic> get screenStatus{
    return _eventChannel.receiveBroadcastStream();
  }
}
