package com.jl.screen_plugin;

import android.content.Intent;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** ScreenPlugin */
public class ScreenPlugin implements FlutterPlugin, MethodCallHandler , EventChannel.StreamHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel methodChannel;
  private EventChannel eventChannel;

  // 屏幕监听
  private ScreenListener screenListener;
  private EventChannel.EventSink eventSink;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {

    methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "com.jl.screenPlugin/method_screenPlugin");
    eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "com.jl.screenPlugin/event_screenPlugin");
    methodChannel.setMethodCallHandler(this);
    eventChannel.setStreamHandler(this);

    // 监听屏幕状态
    screenListener = new ScreenListener(flutterPluginBinding.getApplicationContext());
    screenListener.listener(new ScreenListener.ScreenState() {
      @Override
      public void screenOn() {
        // 屏幕亮了
        if(eventSink != null){
          eventSink.success(Intent.ACTION_SCREEN_ON);
        }
      }

      @Override
      public void screenOff() {
        // 屏幕灭了
        if(eventSink != null){
          eventSink.success(Intent.ACTION_SCREEN_OFF);
        }
      }

      @Override
      public void userPresent() {
        // 解开锁屏了
      }
    });
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
  }

  @Override
  public void onListen(Object arguments, EventChannel.EventSink events) {
    BootBroadcastReceiver.eventSink = events;
    eventSink = events;
  }

  @Override
  public void onCancel(Object arguments) {

  }
}
