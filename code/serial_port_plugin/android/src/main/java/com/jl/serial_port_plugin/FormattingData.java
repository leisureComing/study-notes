package com.jl.serial_port_plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2021-10-20
 * @Description: 格式化数据
 */
public class FormattingData {

    /**
     * [String[]] conversion to [List<String>]
     * */
    public static List<String> strArrToStrList(String[] source){
        List<String> target = new ArrayList<>();
        for (String temp: source) {
            target.add(temp);
        }
        return target;
    }
}
