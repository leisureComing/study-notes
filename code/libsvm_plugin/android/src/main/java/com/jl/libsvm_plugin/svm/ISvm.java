package com.jl.libsvm_plugin.svm;

import java.util.List;
import java.util.Map;

/**
 * @Date: 2021-10-11
 * @Description: libsvm 的封装接口
 */
public interface ISvm {
    /**
     * 训练数据。
     *
     * [problemData]训练所需的数据。
     * 例子:
     * {
     *     "西洋参":[
     *          [1.11, 2.22, 3.33, ...],
     *          [1.11, 2.22, 3.33, ...]
     *     ]
     *     "高丽洋参":[
     *          [1.11, 2.22, 3.33, ...]
     *          [1.11, 2.22, 3.33, ...]
     *     ]
     * }
     * [modelFilePath]模型文件。
     * 例子: /xx/xx/modelFile.txt
     *
     * 成功：标签与类型对应关系:{"西洋参": 1.0, "高丽洋参": 2.0}
     * 失败： 返回空的Map[Collections.EMPTY_MAP]
     * */
    Map<String, Double> svmTrain(Map<String, List<List<Double>>> problemData, String modelFilePath);

    /**
     * 类别预测。
     *
     * [nodeData]需要预测的数据。
     * 例子: [1.11, 2.22, 3.33, ...]
     * [modelFilePath]模型文件。
     * 例子: /xx/xx/modelFile.txt
     *
     * 成功：返回目标值
     * 失败： 返回[-1]
     * */
    double svmPredict(List<Double> nodeData, String modelFilePath);
}
