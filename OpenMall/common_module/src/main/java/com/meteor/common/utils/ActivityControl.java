package com.meteor.common.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/**
 * Author：Meteor
 * date：2018/7/30 15:27
 * desc：管理所有Activity
 */

public class ActivityControl {
    private Set<Activity> allActivities = new HashSet<>();
    private WeakReference<Activity> currentActivity;

    /**
     * 设置当前运行的Activity
     *
     * @param currentActivity
     */
    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = new WeakReference<>(currentActivity);
    }

    /**
     * 获取当前运行的Activity,有可能返回null
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return currentActivity.get();
    }

    /**
     * 添加Activity到管理
     *
     * @param act
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 从管理器移除Activity，一般在OnDestroy移除，防止强引用内存泄漏
     *
     * @param act
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 关闭所有Activity
     */
    public void finishAll() {
        if (allActivities != null) {
            for (Activity act : allActivities) {
                act.finish();
            }
        }
    }

    /**
     * 关闭所有Activity 除了对应的activity
     *
     * @param activity
     */
    public void finishAllExcept(Activity activity) {
        if (allActivities != null) {
            for (Activity act : allActivities) {
                if (act != activity) {
                    act.finish();
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAll();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
