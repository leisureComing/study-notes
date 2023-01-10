import 'dart:ffi';

import 'package:center_control_unit/views/test/bash_dialog.dart';
import 'package:center_control_unit/views/test/bash_page.dart';
import 'package:center_control_unit/views/test/data_page.dart';
import 'package:center_control_unit/views/test/log_page.dart';
import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';

class TestIndex extends StatefulWidget {
  const TestIndex({Key? key}) : super(key: key);

  @override
  State<TestIndex> createState() => _TestIndexState();
}

class _TestIndexState extends State<TestIndex> {
  /// 子界面
  final List<Widget> wItems = const [BashPage(), LogPage(), DataPage()];

  /// 当前页面下标
  int _pageIndex = 0;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();

    getPermission();
  }

  /// 申请权限
  Future<void> getPermission() async {
    Map<Permission, PermissionStatus> statuses = await [
      Permission.storage,
    ].request();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: const Text("back"),
        ),
        body: wItems[_pageIndex],
        bottomNavigationBar: BottomNavigationBar(
          onTap: (index) {
            setState(() {
              _pageIndex = index;
            });
          },
          currentIndex: _pageIndex,
          items: const <BottomNavigationBarItem>[
            BottomNavigationBarItem(
              label: 'bash',
              icon: Icon(Icons.settings),
            ),
            BottomNavigationBarItem(
              label: 'log',
              icon: Icon(Icons.file_copy),
            ),
            BottomNavigationBarItem(
              label: '底层数据',
              icon: Icon(Icons.dataset_rounded),
            ),
          ],
        ),
      ),
    );
  }
}