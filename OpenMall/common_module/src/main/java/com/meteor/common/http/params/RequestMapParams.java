package com.meteor.common.http.params;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Map;
import java.util.TreeMap;

/**
 * Author：Meteor
 * date：2018/7/25 17:59
 * desc：通用参数工厂
 */

public class RequestMapParams implements RequestMapBuild<Map<String, String>> {
    private Map<String, String> map;//存放请求参数
    private Gson gson;

    public RequestMapParams() {
        map = new TreeMap<>();
    }

    @Override
    public RequestMapParams put(String key, String value) {
        if (value != null) {
            map.put(key, value);
        }
        return this;
    }

    @Override
    public RequestMapBuild<Map<String, String>> put(String key, Object value) {
        if (value != null) {
            map.put(key, getGson().toJson(value));
        }
        return this;
    }

    @Override
    public RequestMapBuild<Map<String, String>> put(String key, int value) {
        map.put(key, String.valueOf(value));
        return this;
    }

    @Override
    public RequestMapBuild<Map<String, String>> put(Map<String, String> map) {
        this.map.putAll(map);
        return this;
    }

    @Override
    public RequestMapBuild<Map<String, String>> put(@NonNull String s, @NonNull String[] arrayOf) {
        map.put(s, getGson().toJson(arrayOf));
        return this;
    }

    private Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    @Override
    public Map<String, String> build() {
        //ReqConstrants instance = ReqConstrants.getInstance();
        // if (instance == null) {
        //     return map;
        // }、
        //必传参数
        map.put("apiVersion", "1.0");
        map.put("appPlatform", "1");
        map.put("channel", "360");
        map.put("appPackageName", "com.allyoubank.xinhuagolden.deposit");
        map.put("appVersion", "1.1.2");
        map.put("appCode", "6");
        return map;
    }
}
