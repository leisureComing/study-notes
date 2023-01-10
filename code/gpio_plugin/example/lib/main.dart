import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:gpio_plugin/gpio_plugin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  int beepRingFlag = -1;
  int index = 0;

  @override
  void initState() {
    super.initState();
    GpioPlugin.buttonBeepRing.listen((data) {
      setState(() {
        index++;
        beepRingFlag = data;
      });
    }, onError: (data) {
      print("发生错误$data");
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
            children: <Widget>[
              ElevatedButton(
                child: Text("初始化蜂鸣器和按钮"),
                onPressed: () async {
                  bool result = await GpioPlugin.initBeepButton;
                  print(result);
                },
              ),
              ElevatedButton(
                child: Text("关闭蜂鸣器和按钮"),
                onPressed: () async {
                  bool result = await GpioPlugin.uninitBeepButton;
                  print(result);
                },
              ),
              ElevatedButton(
                child: Text("开启按钮定时器"),
                onPressed: () async {
                  bool result = await GpioPlugin.openTimer;
                  print(result);
                },
              ),
              ElevatedButton(
                child: Text("关闭按钮定时器"),
                onPressed: () async {
                  bool result = await GpioPlugin.closeTimer;
                  print(result);
                },
              ),
              ElevatedButton(
                child: Text("Beep"),
                onPressed: () async {
                  bool result = await GpioPlugin.beepRing;
                  print(result);
                },
              ),
              ElevatedButton(
                child: Text("探头电位拉高"),
                onPressed: () async {
                  bool result = await GpioPlugin.probeHigh;
                  print(result);
                },
              ),
              ElevatedButton(
                child: Text("探头电位拉低"),
                onPressed: () async {
                  bool result = await GpioPlugin.probeLow;
                  print(result);
                },
              ),
              Divider(
                color: Colors.grey,
                height: 10,
              ),
              Text("音量键、功能键点击次数：$index \n点击标志：$beepRingFlag"),
              Divider(
                color: Colors.grey,
                height: 10,
              ),
            ],
          )),
    );
  }
}
