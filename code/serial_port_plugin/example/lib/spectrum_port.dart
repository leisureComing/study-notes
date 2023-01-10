import 'dart:typed_data';
import 'package:serial_port_plugin/serial_port_plugin.dart';

/// 光谱串口类
/// 发送曝光时间命令、荧光谱命令、吸收光命令、反射谱命令
/// 原始光谱数据转化为用户数据
class SpectrumPort{
  /// 曝光时间命令
  final String _exposureTimeCmd  = "6@ITM";
  /// 荧光谱命令
  final String _fluorescenceCmd = "6@SPLD*";
  /// 吸收光命令
  final String _absorptionCmd = "6@SPFT*";
  /// 反射谱命令
  final String _reflectionCmd = "6@SPZF*";

  /// 发送荧光谱命令
  void sendExposureCmd(int exposureTime){
    SerialPortPlugin.send(_exposureTimeCmd+((exposureTime-200)~/20).toString()+"*");
  }

  /// 发送荧光谱命令
  void sendFluorescenceCmd()=>SerialPortPlugin.send(_fluorescenceCmd);
  /// 发送吸收光命令
  void sendAbsorptionCmd()=>SerialPortPlugin.send(_absorptionCmd);
  /// 发送反射谱命令
  void sendReflectionCmd()=>SerialPortPlugin.send(_reflectionCmd);

  /// 处理光谱数据
  /// 518个8位数据，去除前5个和后1个数据；
  /// 剩下512个8位数据（518-5-1 = 512）
  /// 512个数据前一个数据 <<8 与后一个数据合并；成为16位的256个数据。
  static List<int> getSpectrumData(dynamic data){
    final Uint16List target = Uint16List(256);
    int index = 0;
    for(int i = 5; i < 518-1; i += 2){
      target[index++] = (data[i] << 8) + data[i+1];
    }
    return target;
  }
}