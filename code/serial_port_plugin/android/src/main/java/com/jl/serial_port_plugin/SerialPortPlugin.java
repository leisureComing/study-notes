package com.jl.serial_port_plugin;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** SerialPortPlugin */
public class SerialPortPlugin implements FlutterPlugin, MethodCallHandler, EventChannel.StreamHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel method;
  private EventChannel event;
  private EventChannel.EventSink eventSink;
  /**
   * 子线程与主线程通讯
   * eventSink不能再子线程直接使用，所以使用handler消息机制
   */
  private Handler handler;

  // 串口工具
  SerialPortUtils serialPortUtils = new SerialPortUtils();

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    method = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "serial_port_plugin/method");
    event = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "serial_port_plugin/event");

    method.setMethodCallHandler(this);
    event.setStreamHandler(this);

    handler = new Handler(){
      @Override
      public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        // 接收到的数据
        final byte[] buffer = (byte[]) msg.obj;
        // 接收到的数据长度
        final int size = msg.what;

        final Map temp = new HashMap<String, Object>(){{
          put("data", buffer);
          put("size", size);
        }};

        if(null != eventSink){
          eventSink.success(temp);
        }
      }
    };
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {

      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("getAllDevicesPath")) {

      List<String> devList = FormattingData.strArrToStrList(
              serialPortUtils.getAllDevicesPath());
      result.success(devList);
    } else if (call.method.equals("open")) {

      String path = call.argument("path");
      int baudRate = call.argument("baudRate");
      result.success(serialPortUtils.open(path, baudRate));
    }else if (call.method.equals("close")) {

      serialPortUtils.close();
    }else if (call.method.equals("send")) {
      // 注册数据监听回调
      serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener(){
        @Override
        public void onDataReceive(byte[] buffer, int size) {
          // 向主线程发送消息
          Message msg = new Message();
          msg.obj = buffer;
          msg.what = size;
          handler.sendMessage(msg);
        }
      });

      // 发送数据
      String message = call.argument("message");
      serialPortUtils.send(message);
    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    method.setMethodCallHandler(null);
  }

  @Override
  public void onListen(Object arguments, EventChannel.EventSink sink) {
    eventSink = sink;
  }

  @Override
  public void onCancel(Object arguments) {
    eventSink = null;
    // 清除消息队列
    if(null != handler)
      handler.removeCallbacksAndMessages(null);
  }
}
