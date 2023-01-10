package com.mediatek.engineermode.io;

import static com.mediatek.engineermode.io.EmGpio.gpioInit;
import static com.mediatek.engineermode.io.EmGpio.gpioUnInit;
import static com.mediatek.engineermode.io.EmGpio.setGpioDataHigh;
import static com.mediatek.engineermode.io.EmGpio.setGpioDataLow;
import static com.mediatek.engineermode.io.EmGpio.setGpioMode;


public class Beep {
    static
    {
        System.loadLibrary("em_gpio_jni");
    }
    public static void beep_init() {
        gpioInit();
    }
    public static void beep_uninit() {
        gpioUnInit();
    }
    public static int flag = 1;
    public static void beep_high() {
        setGpioDataHigh(95);    //蜂鸣器响

    }
    public static void beep_low() {
        setGpioDataLow(95);     //蜂鸣器关

    }

}
