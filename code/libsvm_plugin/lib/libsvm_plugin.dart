
import 'dart:async';

import 'package:flutter/services.dart';

class LibsvmPlugin {
  static const MethodChannel _channel = const MethodChannel('libsvm_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// 训练
  ///
  /// [problemData]训练所需的数据。
  /// 例子:
  /// {
  /// "西洋参":[
  ///     [1.11, 2.22, 3.33, ...],
  ///     [1.11, 2.22, 3.33, ...]
  ///  ]
  ///  "高丽洋参":[
  ///     [1.11, 2.22, 3.33, ...]
  ///     [1.11, 2.22, 3.33, ...]
  ///  ]
  /// }
  /// [modelFilePath]模型文件。
  /// 例子: /xx/xx/modelFile.txt
  ///
  /// 成功：标签与类型对应关系:{"西洋参": 1.0, "高丽洋参": 2.0}
  /// 失败： 返回空的Map{}
  static Future<Map> svmTrain(Map<String, List<List<double>>> problemData, String modelFilePath) async {
    return await _channel.invokeMethod('svmTrain', {
      "problemData": problemData,
      "modelFilePath": modelFilePath
    });
  }

  /// 类别预测
  ///
  /// [nodeData]需要预测的数据。
  /// 例子: [1.11, 2.22, 3.33, ...]
  /// [modelFilePath]模型文件。
  /// 例子: /xx/xx/modelFile.txt
  ///
  /// 成功：返回目标值
  /// 失败： 返回[-1]
  static Future<double> svmPredict(List<double> nodeData, String modelFilePath) async {
    return await _channel.invokeMethod('svmPredict', {
      "nodeData": nodeData,
      "modelFilePath": modelFilePath
    });
  }
}
