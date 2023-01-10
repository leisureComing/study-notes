package com.mediatek.engineermode.io;

import android.util.Log;
import android.view.KeyEvent;

import static com.mediatek.engineermode.io.EmGpio.getGpioDataIn;
import static com.mediatek.engineermode.io.EmGpio.gpioInit;
import static com.mediatek.engineermode.io.EmGpio.gpioUnInit;
import static com.mediatek.engineermode.io.EmGpio.setGpioInput;
import static com.mediatek.engineermode.io.EmGpio.setGpioMode;


public class Volume {

    private static String TAG = "Volume";
    static
    {
        System.loadLibrary("em_gpio_jni");
    }
    public static void volume_button_init() {
        gpioInit();
        setGpioMode(84);
        setGpioInput(84);

        setGpioMode(85);
        setGpioInput(85);
//        setGpioMode(66);
//        setGpioInput(66);
    }

    public static void volume_button_uninit() {
        gpioUnInit(84);
        gpioUnInit(85);
//        gpioUnInit(66);
    }

    public static int volume_up_state() {
        if(getGpioDataIn(84) == 0)
            return KeyEvent.KEYCODE_VOLUME_UP;
        return -1;
    }

    public static int volume_down_state() {
        if(getGpioDataIn(85) == 0)
            return KeyEvent.KEYCODE_VOLUME_DOWN;
//        if(getGpioDataIn(66) == 0)
//            return KeyEvent.KEYCODE_VOLUME_DOWN;
        return -1;
    }
}
