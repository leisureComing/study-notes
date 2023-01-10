package com.jl.gpio_plugin;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

import com.mediatek.engineermode.io.Beep;
import com.mediatek.engineermode.io.Button;
import com.mediatek.engineermode.io.Probe;
import com.mediatek.engineermode.io.Volume;

import java.util.Timer;
import java.util.TimerTask;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * GpioPlugin
 */
public class GpioPlugin implements FlutterPlugin, MethodCallHandler, EventChannel.StreamHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private final String TAG = "GpioPlugin";
    private MethodChannel method;
    private EventChannel.EventSink eventSink;
    private EventChannel event;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        method = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "gpio_plugin/method");
        method.setMethodCallHandler(this);

        event = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "gpio_plugin/event");
        event.setStreamHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("initBeepButton")) {
            result.success(init_beep_button());
        } else if (call.method.equals("uninitBeepButton")) {
            result.success(uninit_beep_button());
        } else if (call.method.equals("beepRing")) {
            result.success(beep_ring());
        } else if (call.method.equals("openTimer")) {
            result.success(open_timer());
        } else if (call.method.equals("closeTimer")) {
            result.success(close_timer());
        } else if (call.method.equals("probeHigh")) {
            result.success(probe_high());
        } else if (call.method.equals("probeLow")) {
            result.success(probe_low());
        } else {
            result.notImplemented();
        }
    }

    //初始化蜂鸣器和按钮引脚
    boolean init_beep_button() {
        Beep.beep_init();//蜂鸣器初始化
        Button.button_init();//功能键初始化
        Volume.volume_button_init();//音量键初始化
        //初始化探头98引脚-2021-01-11 17:18
        Probe.probe_init();//探头初始化
        return true;
    }

    // 取消初始化蜂鸣器和按钮引脚
    private boolean uninit_beep_button() {
        Beep.beep_uninit();
        Button.button_uninit();
        Volume.volume_button_uninit();
        return true;
    }

    //蜂鸣器响
    private boolean beep_ring() {
        try {
            //初始化蜂鸣器
            Beep.beep_high();
            Thread.sleep(100);
            Beep.beep_low();
            return true;
        } catch (Exception e) {
            Beep.beep_low();
            return false;
        } finally {
            Beep.beep_low();
        }
    }

    // 拉高探头电位 - 2021-01-11 17:23
    private boolean probe_high() {
        try {
            Probe.probe_high();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 拉低探头电位 - 2021-01-11 17:23
    private boolean probe_low() {
        try {
            Probe.probe_low();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //使用静态内部类handler，防止内存泄漏
    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    static MyHandler myhandler = new MyHandler();

    /**
     * 音乐+与功能键冲突
     */
    private static Timer timer = null;
    private static TimerTask task_open = null;
    private boolean flag = true;

    //开启按钮定时器
    private boolean open_timer() {
        if (timer != null && task_open != null)
            return true;
        timer = new Timer();
        task_open = new TimerTask() {
            public void run() {
//                Log.d(TAG, "timer running!");
                // 音量（-）按下时，一直向上层发送消息
                if (Volume.volume_down_state() != -1)
                    myhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (eventSink != null)
                                eventSink.success(KeyEvent.KEYCODE_VOLUME_DOWN);
                        }
                    });
                // 音量（+）按下时，一直向上层发送消息
                if (Volume.volume_up_state() != -1)
                    myhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (eventSink != null)
                                eventSink.success(KeyEvent.KEYCODE_VOLUME_UP);
                        }
                    });
                //功能键按下时，向上层发送一次消息
                if (Button.button_state() != -1 && flag) {
                    flag = false;
                    myhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (eventSink != null)
                                eventSink.success(KeyEvent.KEYCODE_POWER);
                        }
                    });
                } else if (Button.button_state() == -1 && Volume.volume_down_state() == -1) {
                    // 音乐+与功能键冲突;只能如此判断
                    flag = true;
                }
            }
        };
        timer.schedule(task_open, 300, 200);
        return true;
    }

    //关闭按钮定时器
    private boolean close_timer() {
//        Log.d(TAG, "close_timer: " + task_open.toString());
        if (task_open != null) {
            task_open.cancel();
            task_open = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        return true;
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        method.setMethodCallHandler(null);
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        eventSink = events;
    }

    @Override
    public void onCancel(Object arguments) {
        eventSink = null;
        // 清空消息队列
        if (null != myhandler)
            myhandler.removeCallbacksAndMessages(null);
    }
}
