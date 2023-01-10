package com.jl.screen_plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.EventChannel;

/**
 * @Author: leisure
 * @Date: 2020-12-15 17:36
 * @Description: 手机开机、关机的广播接收器
 * 静态注册：<action android:name="android.intent.action.BOOT_COMPLETED"/>
 *          <action android:name="android.intent.action.ACTION_SHUTDOWN"/>
 * 添加权限：<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"></uses-permission>
 */
public class BootBroadcastReceiver extends BroadcastReceiver{
    private String TAG = "BootBroadcastReceiver";
    static public EventChannel.EventSink eventSink;
   @Override
   public void onReceive(Context context, Intent intent) {
       switch (intent.getAction()){
           case Intent.ACTION_BOOT_COMPLETED:
//               Toast.makeText(context, "手机开机完成了！！！", Toast.LENGTH_SHORT).show();
               if(eventSink != null){
                   eventSink.success(Intent.ACTION_BOOT_COMPLETED);
               }
               break;
           case Intent.ACTION_SHUTDOWN:
               if(eventSink != null){
                   eventSink.success(Intent.ACTION_SHUTDOWN);
               }
//               Log.d(TAG, "onReceive: 手机即将关机了");
//               Toast.makeText(context, "手机即将关机了！！！", Toast.LENGTH_SHORT).show();
               break;
           default:
               Toast.makeText(context,"The other broadcast happer!!!", Toast.LENGTH_SHORT).show();
       }

   }
}
