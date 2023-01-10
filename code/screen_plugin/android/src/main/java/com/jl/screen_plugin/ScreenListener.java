package com.jl.screen_plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @Author: leisure
 * @Date: 2020-12-15
 * @Description: 黑屏、亮屏、锁屏的监听
 * 动态注册；
 */
public class ScreenListener {
    private static final String TAG = "ScreenListener";
    private Context context;

    public ScreenListener(Context context){
        this.context = context;
        this.screenBroadcastReceiver = new ScreenBroadcastReceiver();
    }

    /**
     *  屏幕状态的广播接收器
     */
    private ScreenBroadcastReceiver screenBroadcastReceiver;
    private class ScreenBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Intent.ACTION_SCREEN_ON:
                    Log.d(TAG, "亮屏了！");
                    screenState.screenOn();
                    break;
                case Intent.ACTION_SCREEN_OFF:
                    Log.d(TAG, "黑屏了！");
                    screenState.screenOff();
                    break;
                case Intent.ACTION_USER_PRESENT:
                    Log.d(TAG, "解锁了！");
                    screenState.userPresent();
                    break;
                default:
                    Log.d(TAG, "没有要处理的系统广播发生！");
                    Toast.makeText(context,"The other broadcast happer!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 注册广播接收器
    private void registerReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        this.context.registerReceiver(this.screenBroadcastReceiver, intentFilter);
    }

    // 注销屏幕状态的接收器
    // 在没有使用之后，记得注销接收器
    public void unregisterReceiver(){
        this.context.unregisterReceiver(this.screenBroadcastReceiver);
    }

    // 开启屏幕状态的监听
    private ScreenState screenState;
    public void listener(ScreenState screenState){
        this.screenState = screenState;
        this.registerReceiver();
        this.getScreenCurrentState();
    }

    // 获取屏幕当前状态
    private void getScreenCurrentState(){
        PowerManager powerManager = (PowerManager) this.context.getSystemService(Context.POWER_SERVICE);
        if(powerManager.isScreenOn()){
            if(this.screenState != null){
                this.screenState.screenOn();
            }else {
                this.screenState.screenOff();
            }
        }
    }

    /**
     * 接口
     * 广播接收器的回调函数
     * */
    public interface ScreenState{
        // 亮屏回调
        public void screenOn();

        // 黑屏回调
        public void screenOff();

        // 解锁回调
        public void userPresent();
    }
}
