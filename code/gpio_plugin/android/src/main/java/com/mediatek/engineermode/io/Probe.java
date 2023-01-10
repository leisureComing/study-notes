package com.mediatek.engineermode.io;

import static com.mediatek.engineermode.io.EmGpio.gpioInit;
import static com.mediatek.engineermode.io.EmGpio.gpioUnInit;
import static com.mediatek.engineermode.io.EmGpio.setGpioDataHigh;
import static com.mediatek.engineermode.io.EmGpio.setGpioDataLow;
import static com.mediatek.engineermode.io.EmGpio.setGpioMode;
import static com.mediatek.engineermode.io.EmGpio.setGpioOutput;

public class Probe {
    static
    {
        System.loadLibrary("em_gpio_jni");
    }
    public static void probe_init() {
        gpioInit();
        setGpioMode(98);
        setGpioOutput(98);
        setGpioMode(97);
        setGpioOutput(97);
    }
    public static void probe_uninit() {
        gpioUnInit();
    }
    public static void probe_high() {
        setGpioDataHigh(98); 
        setGpioDataHigh(97);

    }
    public static void probe_low() {
        setGpioDataLow(98);
        setGpioDataLow(97);

    }
}
