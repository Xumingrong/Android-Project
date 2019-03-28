package com.meteor.common.http.cancle;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.Disposable;

/**
 * Author：Meteor
 * date：2018/9/04 14:39
 * desc：请求管理类，根据Tag标签来取消相应的请求，或者所有的请求
 */
public class ApiCancelManager {
    private static ApiCancelManager instance;
    private ConcurrentHashMap<Object, Disposable> maps;  //管理所有请求的集合
    private String tagValue;//接收设置的tag标签字段

    private ApiCancelManager() {
        maps = new ConcurrentHashMap<>();
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static ApiCancelManager getInstance() {
        if (instance == null) {
            synchronized (ApiCancelManager.class) {
                if (instance == null) {
                    instance = new ApiCancelManager();
                }
            }
        }
        return instance;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getTagValue() {
        return tagValue;
    }

    /**
     * 添加
     */
    public void add(Object tag, Disposable disposable) {
        maps.put(tag, disposable);
    }

    /**
     * 根据tag标签将此请求移除出集合
     */
    public void remove(Object tag) {
        if (!maps.isEmpty()) {
            maps.remove(tag);
        }
    }

    /**
     * 移除集合中所有的请求
     */
    public void removeAll() {
        if (!maps.isEmpty()) {
            maps.clear();
        }
    }

    /**
     * 取消指定Tag标签的请求
     */
    public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        if (!maps.get(tag).isDisposed()) {
            maps.get(tag).dispose();
            maps.remove(tag);
        }
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        for (Object tag : maps.keySet()) {
            cancel(tag);
        }
    }
}
