import 'package:center_control_unit/views/test/providers/blue_test_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

/// 日志界面
class LogPage extends StatefulWidget {
  const LogPage({Key? key}) : super(key: key);

  @override
  State<LogPage> createState() => _LogPageState();
}

class _LogPageState extends State<LogPage> {
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: context.watch<BlueTestProvider>().allLogs.isNotEmpty
          ? context.read<BlueTestProvider>().allLogs.length
          : 0,
      itemBuilder: (BuildContext context, int index) {
        return Padding(
          padding: const EdgeInsets.only(bottom: 8),
          child:
          Text(context.read<BlueTestProvider>().allLogs[index]),
        );
      },
    );
  }
}