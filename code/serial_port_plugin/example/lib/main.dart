
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:serial_port_plugin/serial_port_plugin.dart';
import 'package:serial_port_plugin_example/spectrum_port.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _tips = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
    SerialPortPlugin.listen((data, size) {
      print("=======原始光谱数=========>$data");
      switch(size){
        case 518:
          List<int> spectrumData = SpectrumPort.getSpectrumData(data);
          print("=======转化后的光谱数据=========>$spectrumData");
          break;
        case 8:
          print("=======设置曝光时间为=========>$data");
          break;
        default:
          print("=======光谱数据有误！=========>数据长度: $size");
      }
    },onError: (e){
      throw e;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await SerialPortPlugin.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Center(
              child: Text('Running on: $_platformVersion\n'),
            ),
            Center(
              child: Text('Running on: $_tips\n'),
            ),
            ElevatedButton(
              child: Text("搜索串口"),
              onPressed: ()async{
                List<String> devList = await SerialPortPlugin.getAllDevicesPath;
                setState(() {
                  _tips = devList.toString();
                });
              },
            ),
            ElevatedButton(
              child: Text("打开串口"),
              onPressed: ()async{
                bool flag = await SerialPortPlugin.open(
                    "/dev/ttyMT2",
                    baudRate: 115200
                );
                setState(() {
                  _tips = flag.toString();
                });
              },
            ),
            ElevatedButton(
              child: Text("关闭串口"),
              onPressed: () {
                SerialPortPlugin.close;
                setState(() {
                  _tips = "close";
                });
              },
            ),
            ElevatedButton(
              child: Text("发送荧光命令"),
              onPressed: () {
                SpectrumPort().sendFluorescenceCmd();
                setState(() {
                  _tips = "send";
                });
              },
            ),
            ElevatedButton(
              child: Text("设置曝光时间"),
              onPressed: () {
                SpectrumPort().sendExposureCmd(5000);
                setState(() {
                  _tips = "设置曝光";
                });
              },
            ),
          ],
        )
      ),
    );
  }
}

