package com.meteor.main.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meteor.common.arouter.InterruptCallback;

/**
 * Author：Meteor
 * date：2018/7/31 10:57
 * desc：router工具类
 */
public class RouterJumpUtil {
    /**
     * 跳转到主页面
     *
     * @param context
     */
    public static void startJumpMainActivity(final Context context) {
        ARouter.getInstance().build(RouterUrl.BASE_MAIN).withString("", "").navigation(context, new InterruptCallback() {
            @Override
            public void onInterrupt(Postcard postcard) {
                super.onInterrupt(postcard);
            }
        });
    }

}
