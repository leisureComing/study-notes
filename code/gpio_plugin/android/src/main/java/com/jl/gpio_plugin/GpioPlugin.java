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

    //?????????????????????????????????
    boolean init_beep_button() {
        Beep.beep_init();//??????????????????
        Button.button_init();//??????????????????
        Volume.volume_button_init();//??????????????????
        //???????????????98??????-2021-01-11 17:18
        Probe.probe_init();//???????????????
        return true;
    }

    // ???????????????????????????????????????
    private boolean uninit_beep_button() {
        Beep.beep_uninit();
        Button.button_uninit();
        Volume.volume_button_uninit();
        return true;
    }

    //????????????
    private boolean beep_ring() {
        try {
            //??????????????????
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

    // ?????????????????? - 2021-01-11 17:23
    private boolean probe_high() {
        try {
            Probe.probe_high();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ?????????????????? - 2021-01-11 17:23
    private boolean probe_low() {
        try {
            Probe.probe_low();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //?????????????????????handler?????????????????????
    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    static MyHandler myhandler = new MyHandler();

    /**
     * ??????+??????????????????
     */
    private static Timer timer = null;
    private static TimerTask task_open = null;
    private boolean flag = true;

    //?????????????????????
    private boolean open_timer() {
        if (timer != null && task_open != null)
            return true;
        timer = new Timer();
        task_open = new TimerTask() {
            public void run() {
//                Log.d(TAG, "timer running!");
                // ?????????-??????????????????????????????????????????
                if (Volume.volume_down_state() != -1)
                    myhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (eventSink != null)
                                eventSink.success(KeyEvent.KEYCODE_VOLUME_DOWN);
                        }
                    });
                // ?????????+??????????????????????????????????????????
                if (Volume.volume_up_state() != -1)
                    myhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (eventSink != null)
                                eventSink.success(KeyEvent.KEYCODE_VOLUME_UP);
                        }
                    });
                //????????????????????????????????????????????????
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
                    // ??????+??????????????????;??????????????????
                    flag = true;
                }
            }
        };
        timer.schedule(task_open, 300, 200);
        return true;
    }

    //?????????????????????
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
        // ??????????????????
        if (null != myhandler)
            myhandler.removeCallbacksAndMessages(null);
    }
}
