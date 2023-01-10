package com.example.mqttPlugin;

import android.content.BroadcastReceiver;

import java.util.Map;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** MqttPlugin */
public class MqttPlugin implements MethodCallHandler, EventChannel.StreamHandler {

  private static IOTClient MqttInstance = null; //MQTT客户端对象

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    // 保存应用上下文
    StaticApplicationContext.APPLICATION_CONTEXT = registrar.context();
    //name标识与上面dart接口保持一致。
    final MethodChannel methodChannel = new MethodChannel(registrar.messenger(), "com.example.mqttPlugin/method_mqttPlugin");
    final EventChannel eventChannel = new EventChannel(registrar.messenger(), "com.example.mqttPlugin/event_mqttPlugin");
    MqttPlugin instance = new MqttPlugin();

    //绑定插件对象
    eventChannel.setStreamHandler(instance);
    methodChannel.setMethodCallHandler(instance);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if(call.method.equals("getPlatformVersion")) {
      System.out.println(StaticApplicationContext.APPLICATION_CONTEXT);
      result.success("getPlatformVersion null");
    }
    else if(call.method.equals("initMqtt")) {
      Map<String, String> arguments = call.arguments();
      result.success(initMqtt(arguments));
    }
    else if(call.method.equals("publishRequest")) {
      result.success(publishRequest(call.arguments.toString()));
    }
    else {
      result.notImplemented();
    }
  }

  /**
   * 初始化MQTT，
   * 失败： -1
   * 成功：0
   * */
  private int initMqtt(Map<String, String> arguments){
    try{
      MqttInstance  = IOTClient.getInstance(
              arguments.get("productKey"),
              arguments.get("deviceName"),
              arguments.get("deviceSecret"),
              arguments.get("subscribeTopic"),
              arguments.get("publishTopic"),
              arguments.get("brokerServer")
      );
    }catch (Exception e){
      e.printStackTrace();
      return -1;
    }
    return 0;
  }

  /**
   * 发送MQTT请求
   * 失败返回：-1
   * 成功：0
   * */
  private int publishRequest(String arguments){
    if(MqttInstance == null){
      return -1;
    }
    try{
      MqttInstance.postData(arguments);
    }catch (Exception e){
      e.printStackTrace();
      return -1;
    }
    return 0;
  }

  /**
   * 重写StreamHander的onListen方法
   * 此方法标识EventSink准备完成，可以向上层发送数据
   * */
  @Override
  public void onListen(Object arguments, EventChannel.EventSink eventSink) {
    SubscribeMessageHander.eventSink = eventSink;//保存对象
  }

  /**
   * 取消事件流
   * */
  @Override
  public void onCancel(Object arguments) {
    SubscribeMessageHander.eventSink = null;
  }
}
