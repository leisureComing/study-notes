package com.lmy.adapter;

import java.util.HashMap;
import java.util.Map;

public class DevMapInfo implements IMapInfo {
    @Override
    public Map getMapInfo() {
        Map<String, String> devInfo = new HashMap<>();
        devInfo.put("name", "设备仪");
        devInfo.put("height", "100");
        devInfo.put("width", "100");
        return devInfo;
    }
}
