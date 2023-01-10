import 'dart:math';

import 'package:center_control_unit/utils/log_util.dart';
import 'package:center_control_unit/utils/shared_preferences_controller.dart';
import 'package:center_control_unit/views/widgets/fl_tocast.dart';
import 'package:flutter_blue/flutter_blue.dart';
import 'package:center_control_unit/utils/bluetooth_service.dart';
import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';

class Bluetooth extends StatefulWidget {
  const Bluetooth({Key? key}) : super(key: key);

  @override
  State<Bluetooth> createState() => _BluetoothState();
}

class _BluetoothState extends State<Bluetooth> {
  @override
  void initState() {
    super.initState();

    // 申请定位权限
    getPermission();

    // 蓝牙扫描
    Future.delayed(const Duration(seconds: 0), () {
      // 检测是否已经有蓝牙连接
      BlueService.connectedDevices.then((values) {
        if (values.isEmpty) {
          BlueService.stopScan().then((value) => BlueService.startScan());
        }
      });
    });
  }

  /// 申请权限
  Future<void> getPermission() async {
    Map<Permission, PermissionStatus> statuses = await [
      Permission.location,
    ].request();
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<BluetoothState>(
      stream: BlueService.state,
      initialData: BluetoothState.unknown,
      builder: (_, snapshot) {
        final state = snapshot.data;
        if (state == BluetoothState.off) {
          return BluetoothOffScreen(state: state);
        }
        return const FindDevicesScreen();
      },
    );
  }
}

/// 蓝牙未开启
class BluetoothOffScreen extends StatelessWidget {
  const BluetoothOffScreen({
    Key? key,
    required this.state,
  }) : super(key: key);

  /// 蓝牙设备状态
  final BluetoothState? state;

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          const Icon(
            Icons.bluetooth_disabled,
            size: 200.0,
            color: Colors.amber,
          ),
          Text(
            '蓝牙设备${state != null ? '没开启' : '不可用'}',
            style: Theme.of(context)
                .primaryTextTheme
                .subtitle1
                ?.copyWith(color: Colors.amber),
          ),
        ],
      ),
    );
  }
}

/// 扫描周围蓝牙并列出蓝牙
class FindDevicesScreen extends StatelessWidget {
  const FindDevicesScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('蓝牙搜索'),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: <Widget>[
            StreamBuilder<List<BluetoothDevice>>(
              stream: Stream.periodic(const Duration(seconds: 2))
                  .asyncMap((_) => BlueService.connectedDevices),
              initialData: [],
              builder: (c, snapshot) {
                return Column(
                  children: snapshot.data!.map((d) {
                    return ListTile(
                      title: Text(d.name),
                      subtitle: Text(d.id.toString()),
                      trailing: StreamBuilder<BluetoothDeviceState>(
                        stream: d.state,
                        initialData: BluetoothDeviceState.disconnected,
                        builder: (c, snapshot) {
                          // LogUtil.d(snapshot.data);
                          if (snapshot.data == BluetoothDeviceState.connected) {
                            return const Text('已连接');
                          }
                          return const Text("正在断开连接...");
                        },
                      ),
                      onTap: () {
                        d.disconnect();
                      },
                    );
                  }).toList(),
                );
              },
            ),
            StreamBuilder<List<ScanResult>>(
              stream: BlueService.scanResults,
              initialData: [],
              builder: (c, snapshot) {
                return Column(
                  children: snapshot.data!.map((rs) {
                    // 检测是否已经有蓝牙连接
                    BlueService.connectedDevices.then((values) {
                      if (values.isEmpty) {
                        SharedPreferencesController()
                            .getBlueName()
                            .then((value) {
                          if (value != null) {
                            if (rs.device.name == value) {
                              SharedPreferencesController()
                                  .getBlueId()
                                  .then((id) {
                                if (id != null &&
                                    id == rs.device.id.toString()) {
                                  BlueService.connect(rs.device);
                                }
                              });
                            }
                          }
                        });
                      }
                    });
                    return ListTile(
                      leading: Icon(
                        Icons.bluetooth,
                        size: 30,
                        color: Theme.of(context).primaryColor,
                      ),
                      title: Text(rs.device.name),
                      subtitle: Text(rs.device.id.toString()),
                      onTap: () async {
                        // 保存蓝牙名称
                        SharedPreferencesController()
                            .storageBlueName(rs.device.name);
                        SharedPreferencesController()
                            .storageBlueId(rs.device.id.toString());

                        // 检测是否已经有蓝牙连接
                        List<BluetoothDevice> bds =
                            await BlueService.connectedDevices;
                        if (bds.isNotEmpty) {
                          FlToast.primaryToastPrimary("已有连接设备!");
                        } else {
                          BlueService.connect(rs.device);
                        }
                      },
                    );
                  }).toList(),
                );
              },
            ),
          ],
        ),
      ),
      floatingActionButton: StreamBuilder<bool>(
        stream: BlueService.isScanning,
        initialData: false,
        builder: (c, snapshot) {
          if (snapshot.data!) {
            return FloatingActionButton(
              onPressed: () => BlueService.stopScan(),
              backgroundColor: Colors.red,
              child: const Icon(Icons.stop),
            );
          } else {
            return FloatingActionButton(
                child: const Icon(Icons.search),
                onPressed: () => BlueService.startScan());
          }
        },
      ),
    );
  }
}
