import 'package:center_control_unit/utils/bluetooth_service.dart';
import 'package:center_control_unit/utils/htlc.dart';
import 'package:center_control_unit/utils/log_util.dart';
import 'package:center_control_unit/views/bluetooth.dart';
import 'package:center_control_unit/views/device.dart';
import 'package:center_control_unit/views/information.dart';
import 'package:center_control_unit/views/widgets/fl_tocast.dart';
import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter_blue/flutter_blue.dart';

class BottomNavigation extends StatefulWidget {
  const BottomNavigation({Key? key}) : super(key: key);

  @override
  State<BottomNavigation> createState() => _BottomNavigationState();
}

class _BottomNavigationState extends State<BottomNavigation>
    with SingleTickerProviderStateMixin {
  final GlobalKey<CurvedNavigationBarState> _bottomNavigationKey = GlobalKey();
  // 动画控制器
  late AnimationController _animationController;

  /// 当前页面下标
  int _pageIndex = 0;

  /// 子界面
  final List<Widget> wItems = const [Bluetooth(), Information()];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();

    _animationController = AnimationController(
      duration: const Duration(seconds: 2),
      vsync: this,
    );
  }

  @override
  void dispose() {
    super.dispose();
    _animationController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Stack(
          children: [
            // 各个子界面
            wItems[_pageIndex],
          ],
        ),
        floatingActionButton: RotationTransition(
          turns: _animationController,
          child: ConstrainedBox(
            constraints: const BoxConstraints.tightFor(
              width: 120,
              height: 120,
            ),
            child: ElevatedButton(
              onPressed: () {
                if(_animationController.isAnimating){
                  FlToast.primaryToastPrimary("正在扫描中耐心等待...");
                  return;
                }
                List<int> highPull = [
                  0x46,
                  0x46,
                ];
                // 向蓝牙发送命令
                // BlueService.write(highPull);
                // _animationController.repeat();
                // Future.delayed(const Duration(seconds: 5), () {
                //   _animationController.reset();
                //   Navigator.of(context).pushNamed("/Device");
                // });

                List<int> cmdTest = [0xFF, 0xBF, 0x81, 0xF0, 0x07, 0x01, 0x02, 0x42, 0x31, 0x02, 0x01, 0x01];
                // 测试
                HDLC.output(cmdTest);

                List<int> cmdData = [0x7E, 0x01, 0xBF, 0x81, 0xF0, 0x18, 0x01, 0x13, 0x50, 0x5A, 0x34,
                  0x30, 0x41, 0x32, 0x32, 0x38, 0x30, 0x30, 0x31, 0x34, 0x31, 0x48, 0x48, 0x4C, 0x2D,
                  0x42, 0x31, 0x04, 0x01, 0x01, 0x83, 0xAD, 0x7E];
                HDLC.input(cmdData);
              },
              style: ElevatedButton.styleFrom(
                shape: const CircleBorder(),
                elevation: 8,
              ),
              child: const Icon(
                Icons.security,
                size: 50,
              ),
            ),
          ),
        ),
        floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
        bottomNavigationBar: CurvedNavigationBar(
          key: _bottomNavigationKey,
          index: 0,
          height: 75,
          items: const <Widget>[
            Icon(
              Icons.bluetooth,
              size: 50,
              color: Colors.white,
            ),
            Icon(
              Icons.people,
              size: 50,
              color: Colors.white,
            ),
          ],
          color: Theme.of(context).primaryColor,
          buttonBackgroundColor: Theme.of(context).primaryColor,
          backgroundColor: Colors.transparent,
          animationCurve: Curves.easeInOutQuint,
          animationDuration: const Duration(milliseconds: 600),
          onTap: (index) {
            setState(() {
              _pageIndex = index;
            });
          },
          letIndexChange: (index) => _animationController.isAnimating?false:true,
        ),
      ),
    );
  }
}
