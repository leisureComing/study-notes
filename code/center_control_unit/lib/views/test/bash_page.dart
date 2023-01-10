import 'dart:async';

import 'package:center_control_unit/utils/bluetooth_service.dart';
import 'package:center_control_unit/utils/htlc.dart';
import 'package:center_control_unit/views/test/bash_dialog.dart';
import 'package:center_control_unit/views/test/providers/blue_test_provider.dart';
import 'package:center_control_unit/views/test/utils/format_conversion.dart';
import 'package:center_control_unit/views/widgets/fl_tocast.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';

/// 脚本生成和执行界面
class BashPage extends StatefulWidget {
  const BashPage({Key? key}) : super(key: key);

  @override
  State<BashPage> createState() => _BashPageState();
}

class _BashPageState extends State<BashPage> {
  /// 序列号
  final TextEditingController _controllerNum = TextEditingController();

  /// 地址
  final TextEditingController _controllerAddr = TextEditingController();

  /// 间隔时间ms
  final TextEditingController _controllerTime = TextEditingController();

  /// 测试
  final TextEditingController _controllerTest = TextEditingController();

  /// 下拉框的值
  String? _value = 'SRET';

  @override
  void initState() {
    super.initState();

    _controllerNum.text = "M2L21B00066-HL-B1";
    _controllerAddr.text = "1";
    _controllerTime.text = "50";
    _controllerTest.text =
        "FF BF 81 F0 16 01 11 4D 32 4C 32 31 42 30 30 30 36 36 2D 48 4C 2D 42 31 02 01 01";
  }

  @override
  void dispose() {
    super.dispose();
    _controllerNum.dispose();
    _controllerAddr.dispose();
    _controllerTime.dispose();
    _controllerTest.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      physics: const BouncingScrollPhysics(),
      child: Column(
        children: [
          ListTile(
            title: const Text("请选择脚本"),
            subtitle: const Text("此脚本是用来链接的"),
            onTap: () {
              BashDialog.showBashDialog(context).then((value) {
                if (value != null) {}
              });
            },
          ),
          setParameter(),
          controllerBash(),
          showAllBash(),
        ],
      ),
    );
  }

  /// 可变化的参数设置界面
  Widget setParameter() {
    return Container(
      padding: const EdgeInsets.only(left: 8),
      height: 240,
      width: double.infinity,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 200,
            child: TextField(
              controller: _controllerNum,
              maxLength: 19,
              inputFormatters: [
                //设置只允许输入数字
                FilteringTextInputFormatter.allow(RegExp(r'^[A-Za-z0-9-]+')),
              ],
              decoration: const InputDecoration(
                labelText: '设备序列号：',
              ),
            ),
          ),
          SizedBox(
            width: 200,
            child: TextField(
              controller: _controllerAddr,
              keyboardType: TextInputType.number,
              //设置键盘为数字
              maxLength: 19,
              inputFormatters: [
                //设置只允许输入数字
                FilteringTextInputFormatter.allow(RegExp(r'[0-9]'))
              ],
              decoration: const InputDecoration(
                labelText: '设备地址：',
              ),
            ),
          ),
          SizedBox(
            width: 200,
            child: DropdownButtonFormField(
              value: _value,
              items: const [
                DropdownMenuItem(child: Text('SRET'), value: 'SRET'),
                DropdownMenuItem(child: Text('MRET'), value: 'MRET'),
                DropdownMenuItem(child: Text('TMA'), value: 'TMA'),
                DropdownMenuItem(child: Text('RAE'), value: 'RAE'),
              ],
              onChanged: (String? value) {
                setState(() {
                  _value = value;
                });
              },
            ),
          ),
        ],
      ),
    );
  }

  /// 失败停止
  bool? _falseStop = false;

  /// 断链停止
  bool? _rokenStop = false;

  /// 循环
  bool? _cycle = false;

  /// 脚本控制界面
  Widget controllerBash() {
    return SizedBox(
      height: 350,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              SizedBox(
                width: 160,
                child: CheckboxListTile(
                  title: const Text('失败终止'),
                  value: _falseStop,
                  onChanged: (value) {
                    setState(() {
                      _falseStop = value;
                    });
                  },
                ),
              ),
              SizedBox(
                width: 160,
                child: CheckboxListTile(
                  title: const Text('断链停止'),
                  value: _rokenStop,
                  onChanged: (value) {
                    setState(() {
                      _rokenStop = value;
                    });
                  },
                ),
              ),
              SizedBox(
                width: 120,
                child: CheckboxListTile(
                  title: const Text('循环'),
                  value: _cycle,
                  onChanged: (value) {
                    setState(() {
                      _cycle = value;
                    });
                  },
                ),
              ),
            ],
          ),
          Container(
            width: 200,
            padding: const EdgeInsets.only(left: 8),
            child: TextField(
              controller: _controllerTime,
              keyboardType: TextInputType.number,
              maxLength: 5,
              inputFormatters: [
                //设置只允许输入数字
                FilteringTextInputFormatter.allow(RegExp(r'[0-9]'))
              ],
              decoration: const InputDecoration(
                labelText: '间隔(ms)',
              ),
            ),
          ),
          const SizedBox(height: 10),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              ElevatedButton(
                onPressed: _cleanAllBash,
                child: const Text("清除批处理"),
              ),
              ElevatedButton(
                onPressed: _startAllBash,
                child: const Text("执行批处理"),
              ),
              ElevatedButton(
                onPressed: _addBash,
                child: const Text("添加脚本"),
              ),
              ElevatedButton(
                onPressed: _startBash,
                child: const Text("执行脚本"),
              ),
            ],
          ),
          TextField(
            controller: _controllerTest,
            maxLength: 200,
            maxLines: 3,
            minLines: 1,
            inputFormatters: [
              //设置只允许输入数字
              FilteringTextInputFormatter.allow(RegExp(r'^[A-Fa-f0-9 ]+')),
            ],
            decoration: const InputDecoration(
              labelText: '测试16进制字符串',
            ),
          ),
        ],
      ),
    );
  }

  /// 完整脚本显示
  Widget showAllBash() {
    return Container(
      margin: const EdgeInsets.only(bottom: 50),
      height: 300,
      child: ListView.builder(
        itemBuilder: (BuildContext context, int index) {
          return item(index);
        },
      ),
    );
  }

  /// 每个子项
  Widget item(int index) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 8),
      child:
          Text("$index-[126, 1, 17, 151, 23, 126, 126, 1, 17, 151, 23, 126]"),
    );
  }

  /// 清除批处理
  void _cleanAllBash() {
    print("清除批处理");
  }

  /// 执行脚本
  void _startAllBash() {
    print("执行批处理");
  }

  /// 添加脚本
  void _addBash() {
    print("添加脚本");
  }

  /// 执行脚本
  void _startBash() {
    print("执行脚本");

    // 获取输入框的值并转译
    List<String> testCmd = _controllerTest.text.split(" ");
    List<int> cmdPa = FormatConversion.strArray2IArray(testCmd);
    List<int> cmdTag = HDLC.output(cmdPa);

    // 启动定时器
    context.read<BlueTestProvider>().startTimer();

    // 检测是否已经有蓝牙连接
    BlueService.connectedDevices.then((values) {
      if (values.isEmpty) {
        FlToast.primaryToastErr("蓝牙未连接上！");
        return;
      }
      // 蓝牙发送命令
      BlueService.write(cmdTag);
      // 获取当前时间
      DateTime now = DateTime.now();
      // 保存底层数据流
      context.read<BlueTestProvider>().setDataLogs = "[TX][$now] "+FormatConversion.iArray2HexStringArray(cmdPa).toString();
      // 保存日志
      context.read<BlueTestProvider>().setAllLogs = "跟据发送命令进行日志的表述";

    });
  }
}
