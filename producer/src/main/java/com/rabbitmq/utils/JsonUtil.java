package com.rabbitmq.utils;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.entity.Orders;

public class JsonUtil {

    public static String Object2JSON(Object obj) {
        try {
            String json = JSON.toJSONString(obj);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Object JSON2Object(String json) {

        try {
            Object obj = JSON.parseObject(json, Orders.class);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
