package com.easy.trip.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Steven
 * @date 2022年09月25日 21:49
 */
public class Parameter {


    public static Map<String, String> parseMap(Map<String, String[]> requestParams) {
        Map<String, String> params = new HashMap<>();
        // 将前台的参数转换为"xxx"->"aaa,bbb"的格式存入params中,实际上回调传来的参数每个key都只对应一个value
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String key = entry.getKey();
            String values[] = entry.getValue();
            StringBuilder valStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                if (i != values.length - 1) {
                    valStr.append(values[i]).append(",");
                } else {
                    valStr.append(values[i]);
                }
            }
            params.put(key, valStr.toString());
        }
        return params;
    }
}
