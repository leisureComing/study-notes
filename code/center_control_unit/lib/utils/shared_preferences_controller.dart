import 'package:shared_preferences/shared_preferences.dart';

class SharedPreferencesController {
  /// 已存放数据的key
  final List<String> _storageKey = [
    "blueName",
    "blueId",
  ];

  /// 存贮蓝牙名称
  void storageBlueName(String account) {
    SharedPreferences.getInstance().then((prefs) {
      prefs.setString(_storageKey[0], account);
    });
  }

  /// 取蓝牙名称
  Future<String?> getBlueName() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString(_storageKey[0]);
  }

  /// 存贮蓝牙id
  void storageBlueId(String account) {
    SharedPreferences.getInstance().then((prefs) {
      prefs.setString(_storageKey[1], account);
    });
  }

  /// 取蓝牙id
  Future<String?> getBlueId() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString(_storageKey[1]);
  }
}
