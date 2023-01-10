# libsvm_plugin

A new libsvm Flutter plugin.

by time: 2021-10-14



# 版本

2.0.0



# 介绍

针对Android项目libsvm

Java代码实现并封装了个提供外部调用的接口

```dart
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
  static Future<bool> svmTrain(
      Map<String, List<List<double>>> problemData, 
      String modelFilePath
  );


  /// 类别预测
  ///
  /// [nodeData]需要预测的数据。
  /// 例子: [1.11, 2.22, 3.33, ...]
  /// [modelFilePath]模型文件。
  /// 例子: /xx/xx/modelFile.txt
  ///
  /// 成功：返回目标值
  /// 失败： 返回[-1]
  static Future<double> svmPredict(
      List<double> nodeData, 
      String modelFilePath
  );
```

