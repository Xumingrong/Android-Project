package com.meteor.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：Meteor
 * date：2018/8/2 16:40
 * desc：mvp设计风格的基类fragment
 */
public abstract class BaseMvpFragment<V, P extends BasePresenter<V>> extends Fragment {
    private P presenter;
    protected Context context;
    public View rootView;
    private Unbinder unBinder;
    protected boolean isRegEvent;//是否注册EventBus
    protected ImmersionBar immersionBar;//修改状态栏的背景
    protected FRAGMENT_STATUS status;//Fragment当前状态

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(getContentLayout(), container, false);
            unBinder = ButterKnife.bind(this, rootView);//初始化ButterKnife
            //初始化沉浸式
            if (isImmersionBarEnabled()) {
                initImmersionBar();
            }
            presenter = createPresenter(); //创建presenter
            if (null != presenter) {
                presenter.attachView((V) this);
            } else {
                throw new NullPointerException("presenter can not be empty");
            }
            //初始化EventBus
            if (isRegEvent) {
                EventBus.getDefault().register(this);
            }
            //初始化控件
            initView();
            initEvent();
            getData();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        status = FRAGMENT_STATUS.START;
    }

    @Override
    public void onResume() {
        super.onResume();
        status = FRAGMENT_STATUS.RESUME;
    }

    @Override
    public void onStop() {
        super.onStop();
        status = FRAGMENT_STATUS.STOP;
    }

    @Override
    public void onPause() {
        super.onPause();
        status = FRAGMENT_STATUS.PAUSE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context = null;
        status = FRAGMENT_STATUS.DESTROY;

        //解绑，并且取消网络请求
        if (presenter != null) {
            presenter.detachView();
        }
        //取消绑定ButterKnife
        if (unBinder != null) {
            unBinder.unbind();
        }
        //取消注册EventBus
        if (isRegEvent) {
            EventBus.getDefault().unregister(this);
        }

        if (immersionBar != null) {
            immersionBar.destroy();
        }
    }
    /**
     * 显示Progress
     */
    public void showProgress() {
        BaseMvpActivity baseMvpActivity = (BaseMvpActivity) getActivity();
        if (baseMvpActivity == null)
            return;
        baseMvpActivity.showProgress();
    }
    /**
     * 隐藏Progress
     */
    public void hideProgress() {
        BaseMvpActivity baseMvpActivity = (BaseMvpActivity) getActivity();
        if (baseMvpActivity == null)
            return;
        baseMvpActivity.hideProgress();
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        immersionBar = ImmersionBar.with(this);
        immersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getContentLayout();

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化点击事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据
     */
    protected abstract void getData();

    /**
     * 获取presenter
     */
    protected P getPresenter() {
        return presenter;
    }

    /**
     * 保存fragment每个生命周期的状态。
     */
    protected enum FRAGMENT_STATUS {
        START, RESUME, PAUSE, STOP, DESTROY
    }

    protected void init(Bundle savedInstanceState) {
    }
}
