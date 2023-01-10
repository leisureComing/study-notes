package com.mediatek.engineermode.io;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import io.flutter.embedding.android.FlutterActivity;

import static com.mediatek.engineermode.io.EmGpio.getGpioDataIn;
import static com.mediatek.engineermode.io.EmGpio.gpioInit;
import static com.mediatek.engineermode.io.EmGpio.gpioUnInit;
import static com.mediatek.engineermode.io.EmGpio.setGpioInput;
import static com.mediatek.engineermode.io.EmGpio.setGpioMode;

/**
 * 83(功能键)
 * */
public class Button{
    private static String TAG = "Button";
    static
    {
        System.loadLibrary("em_gpio_jni");
    }
    public static void button_init() {
        gpioInit();
        setGpioMode(83);
        setGpioInput(83);
    }

    public static void button_uninit() {
        gpioUnInit();
    }

    // 这里使用26进行标记，没别的意思
    public static int button_state() {
        if(getGpioDataIn(83) == 1)
            return KeyEvent.KEYCODE_POWER;
        return -1;
    }


}
