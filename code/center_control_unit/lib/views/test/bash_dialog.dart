import 'package:flutter/material.dart';


/// 弹窗界面
class BashList extends StatelessWidget {
  const BashList({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final width = MediaQuery.of(context).size.width;
    final height = MediaQuery.of(context).size.height;
    return Material(
      color: Colors.transparent,
      child: Center(
        child: Container(
          padding: EdgeInsets.all(10),
          width: width *  2 / 3,
          height: height *  2 / 3,
          color: Colors.white,
          child: ListView.builder(
            itemBuilder: (BuildContext context, int index) {
              return ListTile(
                title: Text("$index 脚本名"),
                subtitle: Text("此脚本是链接"),
                onTap: (){
                  Navigator.pop(context, {
                    "bashName": "脚本名$index",
                  });
                },
              );
            },
          ),
        ),
      ),
    );
  }
}

/// 弹出弹框函数
class BashDialog {
  static Future<Map?> showBashDialog(BuildContext context) {
    return showGeneralDialog(
      context: context,
      barrierLabel: "你好",
      barrierDismissible: true,
      barrierColor: const Color(0x88000000),
      transitionDuration: const Duration(milliseconds: 300),
      transitionBuilder: (_, anim, __, child) {
        return ScaleTransition(
          scale: anim,
          alignment: const Alignment(-0.8, -0.89),
          child: child,
        );
      },
      pageBuilder: (BuildContext context, Animation animation,
          Animation secondaryAnimation) {
        return const Center(
          child: BashList(),
        );
      },
    );
  }
}
