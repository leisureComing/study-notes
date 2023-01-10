package com.jl.libsvm_plugin.svm.impl;

import com.jl.libsvm_plugin.libsvm.svm;
import com.jl.libsvm_plugin.libsvm.svm_model;
import com.jl.libsvm_plugin.libsvm.svm_node;
import com.jl.libsvm_plugin.libsvm.svm_parameter;
import com.jl.libsvm_plugin.libsvm.svm_problem;
import com.jl.libsvm_plugin.svm.ISvm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2021-10-11
 * @Description: libsvm 的封装实践接口
 */
public class SpectrumSvm implements ISvm {
    /**
     * 训练使用的数据格式
     */
    private svm_problem prob;

    /**
     * 数据格式节点
     */
    private svm_node node;

    /**
     * 数据节点数组
     */
    private svm_node[] arrayNode;

    /**
     * svm参数设置（针对光谱仪）
     */
    private svm_parameter param = new svm_parameter() {{
        svm_type = 0;
        kernel_type = 0;
        degree = 3;
        gamma = 0.5;
        coef0 = 0;
        C = 10;
        nu = 0.5;
        p = 0.1;
        cache_size = 100;
        eps = 1e-3;
        shrinking = 1;
        probability = 1;
    }};

    /**
     * 训练得到的模型
     */
    private svm_model model;

    @Override
    public Map<String, Double> svmTrain(Map<String, List<List<Double>>> problemData, String modelFilePath) {

        Map<String, Double> labelType = convertData(problemData);

        // 模型训练
        model = svm.svm_train(prob, param);
        try {
            // 模型保存
            svm.svm_save_model(modelFilePath, model);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.EMPTY_MAP;
        }
        return labelType;
    }

    /**
     * 数据转化为训练需要的数据格式。
     */
    private Map<String, Double> convertData(Map<String, List<List<Double>>> problemData) {
        newObj(problemData);

        // 标签与类型对应关系
        Map<String, Double> labelType = new HashMap();
        // 总数据条数
        int len = 0;
        // 目标值
        double label = 0;
        for (Map.Entry<String, List<List<Double>>> entry : problemData.entrySet()) {
            label++;
            List<List<Double>> tempData = entry.getValue();
            for (List<Double> classData : tempData) {
                for (int i = 0; i < classData.size(); i++) {
                    node = new svm_node();
                    node.index = i + 1;
                    node.value = classData.get(i);
                    prob.x[len][i] = node;
                }
                prob.y[len] = label;
                len++;
                prob.l = len;
            }
            labelType.put(entry.getKey(), label);
        }
        return labelType;
    }

    /**
     * 新建svm_problem、svm_parameter对象
     */
    private void newObj(Map<String, List<List<Double>>> problemData) {
        // 计算数据总条数-用于初始化数组
        int index = 0;
        int dataIndex = 0;
        for (Map.Entry<String, List<List<Double>>> entry : problemData.entrySet()) {
            index += entry.getValue().size();
            dataIndex = entry.getValue().get(0).size();

        }
        prob = new svm_problem();
        prob.x = new svm_node[index][dataIndex];
        prob.y = new double[index];
    }

    @Override
    public double svmPredict(List<Double> nodeData, String modelFilePath) {
        try {
            // 加载训练模型
            model = svm.svm_load_model(modelFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        convertToNodeArray(nodeData);
        return svm.svm_predict(model, arrayNode);
    }

    /**
     * 测试数据转化为svm的测试数据格式
     */
    private void convertToNodeArray(List<Double> nodeData) {
        arrayNode = new svm_node[nodeData.size()];
        for (int i = 0; i < nodeData.size(); i++) {
            node = new svm_node();
            node.index = i + 1;
            node.value = nodeData.get(i);
            arrayNode[i] = node;
        }
    }
}
