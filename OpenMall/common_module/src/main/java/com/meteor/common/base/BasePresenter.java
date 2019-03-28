package com.meteor.common.base;

/**
 * Author：Meteor
 * date：2018/7/30 15:28
 * desc：Presenter基类
 */
public abstract class BasePresenter<V> {
    private V mvpView;

    /**
     * 绑定view
     */
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    /**
     * 解绑view
     */
    public void detachView() {
        mvpView = null;
    }

    /**
     * 获取view
     */
    public V getMvpView() {
        return mvpView;
    }
}
