package com.example.mqttPlugin;

import android.util.Base64;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IOTClient{
    private static final String TAG = IOTClient.class.getSimpleName();
    private final String productKey;// 产品key
    private final String deviceName;//已经注册的设备id
    private final String deviceSecret;//设备秘钥
    private final String subscribeTopic;//订阅
    private final String publishTopic;//发送
    private final String brokerServer;//服务器地址

    private MqttAndroidClient mqttAndroidClient = null;

    //单例化
    private static IOTClient instance = null;
    private IOTClient(String productKey, String deviceName, String deviceSecret, String subscribeTopic, String publishTopic, String brokerServer){
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.deviceSecret = deviceSecret;
        this.subscribeTopic = subscribeTopic;
        this.publishTopic = publishTopic;
        this.brokerServer = brokerServer;
        initMqttClient();
    };
    public static synchronized IOTClient getInstance(String productKey, String deviceName, String deviceSecret, String subscribeTopic, String publishTopic, String brokerServer){
        if(instance == null){
            instance = new IOTClient(productKey, deviceName, deviceSecret, subscribeTopic, publishTopic, brokerServer);
        }
        return instance;
    }

    private void initMqttClient() {
        try {
            if(mqttAndroidClient != null && mqttAndroidClient.isConnected()){
                Log.d(TAG, "mqttClient is Connected " + mqttAndroidClient.isConnected());
                return;
            }
            String clientId = deviceName + System.currentTimeMillis();
            Map<String, String> params = new HashMap<>(16);
            params.put("productKey", productKey);
            params.put("deviceName", deviceName);
            params.put("clientId", clientId);
            String timestamp = String.valueOf(System.currentTimeMillis());
            params.put("timestamp", timestamp);

            // cn-shanghai
            String targetServer = brokerServer;
            String mqttClientId = clientId + "|securemode=3,signmethod=hmacsha1,timestamp=" + timestamp + "|";
            String mqttUsername = deviceName + "&" + productKey;
            String mqttPassword = AliyunIoTSignUtil.sign(params, deviceSecret,"HMACSHA1");
            
            connectMqtt(targetServer, mqttClientId, mqttUsername, mqttPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectMqtt(String url, String clientId, String mqttUsername, String mqttPassword) throws Exception {
        mqttAndroidClient = new MqttAndroidClient(StaticApplicationContext.APPLICATION_CONTEXT, url, clientId);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        // MQTT 3.1.1
        connOpts.setMqttVersion(4);
        connOpts.setAutomaticReconnect(true);
        connOpts.setCleanSession(true);

        connOpts.setUserName(mqttUsername);
        connOpts.setPassword(mqttPassword.toCharArray());
        connOpts.setKeepAliveInterval(60);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                //第一次连接，连接成功的回调函数，并订阅topic（有信息发送至此topic可及时收到）
                if(!reconnect) {
                    subNotifyTopic();
                }else{
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.d(TAG, "connectionLost" + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(SubscribeMessageHander.eventSink != null){
                    SubscribeMessageHander.eventSink.success(new String(message.getPayload()));
                }
                Log.d(TAG, "messageArrived topic=" + topic+", message=" + new String(message.getPayload()));
                //此处为收到的云端数据
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.d(TAG, "deliveryComplete token=" + token.toString());
            }
        });

        mqttAndroidClient.connect(connOpts,null,
                new IMqttActionListener(){
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.d(TAG, "connect 回调 连接成功");
                    }
                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.d(TAG, "connect 回调 onFailure，原因 exception=" + exception.toString());
                        if(exception instanceof MqttSecurityException){
                            MqttSecurityException mqttException = (MqttSecurityException)exception;
                            // 由于网络，本地client超时...等原因导致失败，需要主动发起重连
                            if(mqttException.getReasonCode() == MqttException.REASON_CODE_CLIENT_EXCEPTION
                                    || mqttException.getReasonCode() == MqttException.REASON_CODE_CLIENT_TIMEOUT ){
                                System.out.println("由于网络，本地client超时...等原因导致失败，需要主动发起重连");
//                                mHandler.postDelayed(() -> {
//                                    initMqttClient();
//                                },5000);
                            }
                        }
                    }
                });
    }
    private void subNotifyTopic(){
        //订阅Topic
        try {
            mqttAndroidClient.subscribe(subscribeTopic , 0);
            System.out.println("订阅Topic"+subscribeTopic);
        } catch (MqttException e) {
            Log.d(TAG, "subscribe error " );
        }
    }
    //上报数据
    public void postData(String requestJson) throws Exception {
        MqttMessage message = new MqttMessage(requestJson.getBytes("utf-8"));
        message.setQos(0);
        mqttAndroidClient.publish(this.publishTopic,message);
    }

}
