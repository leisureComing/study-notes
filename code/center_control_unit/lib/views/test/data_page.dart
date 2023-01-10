import 'package:center_control_unit/views/test/providers/blue_test_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

/// 底层数据流界面
class DataPage extends StatefulWidget {
  const DataPage({Key? key}) : super(key: key);

  @override
  State<DataPage> createState() => _DataPageState();
}

class _DataPageState extends State<DataPage> {
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: context.watch<BlueTestProvider>().dataLogs.isNotEmpty
          ? context.read<BlueTestProvider>().dataLogs.length
          : 0,
      itemBuilder: (BuildContext context, int index) {

        return Padding(
          padding: const EdgeInsets.only(bottom: 8),
          child:
          Text(context.read<BlueTestProvider>().dataLogs[index]),
        );
      },
    );
  }

}
