package com.meteor.common.arouter;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * Author：Meteor
 * date：2018/7/31 10:54
 * desc：监听路由的过程
 */
public class InterruptCallback implements NavigationCallback {

    /**
     * 找到了
     *
     * @param postcard
     */
    @Override
    public void onFound(Postcard postcard) {

    }

    /**
     * 找不到
     *
     * @param postcard
     */
    @Override
    public void onLost(Postcard postcard) {

    }

    /**
     * 跳转完了
     *
     * @param postcard
     */
    @Override
    public void onArrival(Postcard postcard) {

    }

    /**
     * 被拦截了
     *
     * @param postcard
     */
    @Override
    public void onInterrupt(Postcard postcard) {

    }
}
