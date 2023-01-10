package com.jl.screen_plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @Author: leisure
 * @Date: 2020-12-16
 * @Description: 黑屏、亮屏、锁屏的监听
 * 静态注册：（没有效果！！！）
 * <action android:name="android.intent.action.SCREEN_ON"></action>
 * <action android:name="android.intent.action.SCREEN_OFF"></action>
 * <action android:name="android.intent.action.USER_PRESENT"></action>
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private String TAG = "ScreenBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case Intent.ACTION_SCREEN_ON:
                Log.d(TAG, "亮屏了！");
                break;
            case Intent.ACTION_SCREEN_OFF:
                Log.d(TAG, "黑屏了！");
                break;
            case Intent.ACTION_USER_PRESENT:
                Log.d(TAG, "解锁了！");
                break;
            default:
                Log.d(TAG, "没有要处理的系统广播发生！");
                Toast.makeText(context,"The other broadcast happer!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
