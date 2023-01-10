import 'dart:async';

import 'package:flutter/services.dart';

class MqttPlugin {
  static const MethodChannel _methodchannel = const MethodChannel('com.example.mqttPlugin/method_mqttPlugin');
  static const EventChannel _eventChannel = const EventChannel('com.example.mqttPlugin/event_mqttPlugin');

  static Future<String> get platformVersion async {
    final String version = await _methodchannel.invokeMethod('getPlatformVersion');
    return version;
  }

  //初始化MQTT
  static Future<int> initMqtt({String productKey, String deviceName, String deviceSecret, String subscribeTopic, String publishTopic, String brokerServer}) async {
    if(productKey == null || deviceName == null|| deviceSecret == null || subscribeTopic == null || publishTopic == null || brokerServer == null){
      return -1;
    }
    Map initParameter = {
      "productKey": productKey,
      "deviceName": deviceName,
      "deviceSecret": deviceSecret,
      "subscribeTopic": subscribeTopic,
      "publishTopic": publishTopic,
      "brokerServer": brokerServer
    };
    return await _methodchannel.invokeMethod('initMqtt', initParameter);
  }

  static Future<int> publishRequest(String request) async {
    return await _methodchannel.invokeMethod('publishRequest', request);
  }

  /**
   * 获取server broker 发来的信息
   * 通过事件通道EventChannel的receiveBroadcastStream方法接收广播事件流
   * */
  static Stream<dynamic> get brokerMsg{
    Stream<dynamic> result = _eventChannel.receiveBroadcastStream();
    if(result != null){
      return result;
    }
    return null;
  }
}
