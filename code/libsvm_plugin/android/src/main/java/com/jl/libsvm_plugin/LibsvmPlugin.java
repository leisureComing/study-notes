package com.jl.libsvm_plugin;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import com.jl.libsvm_plugin.libsvm.*;
import com.jl.libsvm_plugin.svm.ISvm;
import com.jl.libsvm_plugin.svm.impl.SpectrumSvm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * LibsvmPlugin
 */
public class LibsvmPlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private ISvm iSvm = new SpectrumSvm();

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "libsvm_plugin");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("svmTrain")) {
            Map<String, List<List<Double>>> problemData = call.argument("problemData");
            String modelFilePath = call.argument("modelFilePath");
            result.success(iSvm.svmTrain(problemData, modelFilePath));
        } else if (call.method.equals("svmPredict")) {
            List<Double> nodeData = call.argument("nodeData");
            String modelFilePath = call.argument("modelFilePath");
            result.success(iSvm.svmPredict(nodeData, modelFilePath));
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
