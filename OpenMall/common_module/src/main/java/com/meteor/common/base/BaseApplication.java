package com.meteor.common.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.meteor.common.utils.ActivityControl;
import com.meteor.common.utils.Utils;

/**
 * Author：Meteor
 * date：2018/7/30 11:05
 * desc：Application基类
 */
public class BaseApplication extends Application {
    private static BaseApplication application;
    private ActivityControl activityControl;//Activity管理

    public ActivityControl getActivityControl() {
        return activityControl;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
    }

    /**
     * 获取当前Application
     *
     * @return
     */
    public static BaseApplication getApplication() {
        return application;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        activityControl = new ActivityControl();
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        activityControl.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 程序在内存清理的时候执行
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
