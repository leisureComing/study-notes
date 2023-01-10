package com.jl.serial_port_plugin;


import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

public class SerialPortUtils {
    private final String TAG = "SerialPortUtils";

    private InputStream inputStream;
    private OutputStream outputStream;
    private SerialPort serialPort;
    private SerialPortFinder serialPortFinder;

    //保存读取数据和数据长度
    private byte[] buffer = new byte[1024];
    //读取数据的大小
    private int dataSize;
    //线程状态
    private boolean exitThread = true;
    private ReadThread readThread;

    // 回调接口
    private OnDataReceiveListener onDataReceiveListener;
    /**
     * 监听器监听回调接口
     * */
    public static interface OnDataReceiveListener{
        public void onDataReceive(byte[] buffer, int size);
    }
    /**
     * 设置监听回调
     * */
    public void setOnDataReceiveListener(OnDataReceiveListener dataReceiveListener) {
        onDataReceiveListener = dataReceiveListener;
    }


    /**
     * 功能：打开串口
     * 参数：[path]串口路径、[baudRate]波特率
     * 成功：return true;
     */
    public boolean open(String path, int baudRate) {
        try {
            serialPort = new SerialPort(new File(path), baudRate, 0);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //线程状态
        exitThread = false;

        //获取打开的串口中的输入输出流，以便于串口数据的收发
        inputStream = serialPort.getInputStream();
        outputStream = serialPort.getOutputStream();

        // 启动接收数据线程
        readThread = new ReadThread();
        readThread.start();
        return true;
    }

    /**
     * 功能：关闭串口
     */
    public void close() {
        // 线程状态
        exitThread = true;
        // 关闭线程
        readThread.interrupt();
        // 关闭串口
        serialPort.close();
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：发送到串口
     * 参数：[message]发送的字符串
     */
    public void send(String message) {
        byte[] sendData = message.getBytes();
        if (sendData.length <= 0) return;
        try {
            outputStream.write(sendData);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：接收串口数据
     * 成功：return 接收到的数据长度；失败：-1
     */
    public int receive(byte b[], int off, int len) {
        try{
            // 阻塞读取
            return inputStream.read(b, off, len);
        }catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 功能：获取全部的串口路径
     */
    public String[] getAllDevicesPath(){
        serialPortFinder = new SerialPortFinder();
        return serialPortFinder.getAllDevicesPath();
    }


    /**
     * 功能：监听接收的数据线程
     */
    private class ReadThread extends Thread {
        @Override
        public void run() {
            while (!exitThread){
                // 清空数据
                java.util.Arrays.fill(buffer, (byte) 0);
                dataSize = receive(buffer,0, buffer.length);
                // 接收到的数据
                byte[] tempBuf = Arrays.copyOf(buffer, dataSize);
                // 发送到回调函数中
                if(null != onDataReceiveListener)
                    onDataReceiveListener.onDataReceive(tempBuf, dataSize);
            }
            Log.d(TAG, "监听线程结束...");
        }
    }
}
