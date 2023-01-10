import 'dart:async';

import 'package:center_control_unit/config/global_context.dart';
import 'package:center_control_unit/views/test/providers/blue_test_provider.dart';
import 'package:flutter_blue/flutter_blue.dart';
import 'package:provider/provider.dart';

/// 蓝牙通用属性
class BlueGatt {
  // /// 服务UUID
  // static const String serviceUuid = "0000fee0-0000-1000-8000-00805f9b34fb";
  //
  // /// 读特征值UUID
  // static const String notifyCharacteristicUuid =
  //     "0000fee1-0000-1000-8000-00805f9b34fb";
  //
  // /// 写特征值UUID
  // static const String writeCharacteristicUuid =
  //     "0000fee2-0000-1000-8000-00805f9b34fb";

  /// 服务UUID
  static const String serviceUuid = "0000FFE0-0000-1000-8000-00805F9B34FB";

  /// 读特征值UUID
  static const String notifyCharacteristicUuid =
      "0000FFE1-0000-1000-8000-00805F9B34FB";

  /// 写特征值UUID
  static const String writeCharacteristicUuid =
      "0000FFE1-0000-1000-8000-00805F9B34FB";

}

/// 蓝牙服务
/// 蓝牙搜索、连接、读、写
class BlueService {
  /// 蓝牙设备
  static late BluetoothDevice _device;

  /// 读特征值
  static late BluetoothCharacteristic _rCharacteristic;

  /// 写特征值
  static late BluetoothCharacteristic _wCharacteristic;

  /// 开始扫描蓝牙设备
  static Future startScan() async {
    return await FlutterBlue.instance
        .startScan(timeout: const Duration(seconds: 4));
  }

  /// 结束扫描
  static Future stopScan() async {
    return await FlutterBlue.instance.stopScan();
  }

  /// 返回扫描过程中[ScanResult]结果列表的流
  static Stream<List<ScanResult>> get scanResults =>
      FlutterBlue.instance.scanResults;

  /// 连接蓝牙；监听读特征值,保存读特征值；保存写特征值; 保存蓝牙设备对象
  static void connect(BluetoothDevice device) {
    // 连接蓝牙
    device.connect().then((value) {
      // 获取服务
      device.discoverServices().then((services) async {
        // 遍历服务
        for (var s in services) {
          print(s.uuid.toString());
          if (s.uuid.toString().toUpperCase() == BlueGatt.serviceUuid) {
            // 获取特征值
            List<BluetoothCharacteristic> characteristics = s.characteristics;
            // 遍历特征值
            for (var characteristic in characteristics) {
              print(characteristic.uuid.toString().toUpperCase());
              if (characteristic.uuid.toString().toUpperCase() ==
                  BlueGatt.notifyCharacteristicUuid) {
                _rCharacteristic = characteristic;
                _device = device;
                listen();
              }
              if (characteristic.uuid.toString().toUpperCase() ==
                  BlueGatt.writeCharacteristicUuid) {
                _wCharacteristic = characteristic;
              }
            }
          }
        }
      });
    });
  }

  static StreamSubscription? _listenStream;

  /// 监听收到的数据
  static Future<void> listen() async {
    await _listenStream?.cancel();
    await _rCharacteristic?.setNotifyValue(true);

    // 请求MTU长度；取决与蓝牙硬件的支持
    final mtu = await _device.mtu.first;
    await _device.requestMtu(100);

    _listenStream = _rCharacteristic?.value.listen((values) {
      // print("蓝牙数据： $values - ${values.length}");
      if(values.isNotEmpty){
        // 发送到状态管理
        GlobalContext.context.read<BlueTestProvider>().listen(values);
      }

    }, onError: (e) {
      print(e);
    });
  }

  /// 写数据
  static void write(List<int> msg) {
    _wCharacteristic.write(msg);
  }

  /// 关闭蓝牙
  static void disconnect(BluetoothDevice device) {
    // _rCharacteristic.setNotifyValue(false);
    device.disconnect();
  }

  /// 获取蓝牙打开状态
  static Stream<BluetoothState> get state => FlutterBlue.instance.state;

  /// 获取蓝牙的连接状态
  // static Stream<BluetoothDeviceState>? get deviceState => _device?.state;

  /// 监听是否在扫描
  static Stream<bool> get isScanning => FlutterBlue.instance.isScanning;


  /// 检索已连接设备的列表
  static Future<List<BluetoothDevice>> get connectedDevices async =>
      await FlutterBlue.instance.connectedDevices;
}
