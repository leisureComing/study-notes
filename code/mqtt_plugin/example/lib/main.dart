import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:mqttPlugin/mqttPlugin.dart';

const String productKey = "a17eOCWUdxZ";// 产品key
const String deviceName = "JLAPP00000001";//已经注册的设备id
const String deviceSecret = "051af20e69a6adc64077d69e637e1bea";//设备秘钥
const String subscribeTopic = "/" + productKey + "/" + deviceName + "/user/read";//订阅
const String publishTopic = "/" + productKey + "/" + deviceName + "/user/write";//订阅
const String brokerServer = "tcp://" + productKey + ".iot-as-mqtt.cn-shanghai.aliyuncs.com:1883";
const String serverName = "testServer4";


//模拟登陆
Map mapJson  = {
  "action": 8009,
  "message": "APP get the server port：8000",
};

//模拟退出登录
//Map mapJson  = {
//  "head": {
//    "devTo": "$serverName",
//    "devFrom": "$deviceName",
//    "action": "7001",
//    "token": "364e468c38cff020302eb5923e75f56c9e9d2c1a",
//    "dateTime": "1561103862919",
//  },
//  "body": {
//    "username": "Test1",
//  }
//};


void main(){

  runApp(MyApp());
  MqttPlugin.initMqtt(
      productKey: productKey,
      deviceName: deviceName,
      deviceSecret: deviceSecret,
      subscribeTopic: subscribeTopic,
      publishTopic: publishTopic,
      brokerServer: brokerServer
  );//MQTT初始化
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _receiveddata = 'null';

  @override
  void initState() {
    super.initState();
    //事件监听
    MqttPlugin.brokerMsg.listen((data){
      setState(() {
        this._receiveddata = data;
      });
//      String strJson = json.encode(mapJson);
//      MqttPlugin.publishRequest(strJson).then((value) => print(value));

    },onError: (error){
      print("brokerMsg error: $error");
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('MQTT Plugin example app'),
        ),
        body: Container(
            width: double.infinity,
            height: double.infinity,
            child: Stack(
              children: <Widget>[
                Text('${this._receiveddata}'),
                Positioned(
                  bottom: 20,
                  left: 20,
                  child: Container(
                    width: 100,
                    height: 30,
                    child: RaisedButton(
                      padding: EdgeInsets.all(0.0),
                      color: Colors.blue,
                      child: Text("publish"),
                      onPressed: (){
                        print("发送");
                        String strJson = json.encode(mapJson);
                        MqttPlugin.publishRequest(strJson);
                      },
                    ),
                  ),
                )
              ],
            )
        ),
      ),
    );
  }
}
