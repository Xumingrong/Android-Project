package com.meteor.common.base;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.meteor.common.R;
import com.meteor.common.utils.PermissionUtil;
import com.meteor.common.widget.MallProgressBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：Meteor
 * date：2018/7/30 15:27
 * desc：mvp设计风格的基类activity
 */
public abstract class BaseMvpActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {
    private P presenter;
    protected ImmersionBar immersionBar;//修改状态栏的背景
    public final String ACTIVITY_SIMPLE_NAME = this.getClass().getSimpleName();//获取当前activity名称
    private Unbinder unBinder;
    protected ACTIVITY_STATUS status;//activity当前状态
    protected LayoutInflater layoutInflater;
    private MallProgressBar loadingView;
    protected boolean isRegEvent;//是否注册EventBus

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        unBinder = ButterKnife.bind(this);//初始化ButterKnife
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar(R.color.color_dominant_hue, R.color.color_black, false);
        }
        BaseApplication.getApplication().getActivityControl().addActivity(this);//加入activity管理
        layoutInflater = getLayoutInflater();
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

        initView(savedInstanceState);
        initProgressBar();
        initEvent();
        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        status = ACTIVITY_STATUS.START;
    }

    @Override
    protected void onPause() {
        super.onPause();
        status = ACTIVITY_STATUS.PAUSE;
    }

    @Override
    protected void onResume() {
        super.onResume();
        status = ACTIVITY_STATUS.RESUME;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        //在BaseActivity里销毁mImmersionBar
        if (immersionBar != null) {
            immersionBar.destroy();
        }
        BaseApplication.getApplication().getActivityControl().removeActivity(this); //移除类
    }

    /**
     * 初始化进度条
     */
    private void initProgressBar() {
        View view = layoutInflater.inflate(R.layout.loading_view, null);
        this.loadingView = ((MallProgressBar) view.findViewById(R.id.loading_view));
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 显示Progress
     */
    public void showProgress() {
        if (getCurrentStatus().equals(ACTIVITY_STATUS.DESTROY)) {
            return;
        }
        if (this.loadingView != null) {
            this.loadingView.showProgress();
        }
    }

    /**
     * 隐藏Progress
     */
    public void hideProgress() {
        if (null != this.loadingView) {
            this.loadingView.hideProgress();
        }
    }

    /**
     * @return: 获取当前activity的状态
     */
    public ACTIVITY_STATUS getCurrentStatus() {
        return this.status;
    }

    /**
     * 修改状态栏的背景
     *
     * @param highStatusBarColor
     * @param lowStatusBarColor
     * @param isChange
     */
    protected void initImmersionBar(@ColorRes int highStatusBarColor, @ColorRes int lowStatusBarColor, boolean isChange) {
        //在BaseActivity里初始化
        immersionBar = ImmersionBar.with(this);
        //如果设备支持状态栏变色（背景色默认为白色(可更改,在子类重写initImmersionBar方法，传入第一个参数颜色即可),字体颜色可更改：系统默认为白色；statusBarDarkFont为true时：为黑色；statusBarDarkFont为false时：为白色，本项目大部分地方状态栏字体为黑色，所以默认为true,即为黑色。
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            immersionBar.statusBarDarkFont(isChange)
                    .fitsSystemWindows(true)
                    .statusBarColor(highStatusBarColor)
                    .keyboardEnable(true)
                    .init();
//            mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
            //如果设备不支持状态栏变色(背景色现在默认为黑色(可更改,在子类重写initImmersionBar方法，传入第二个参数颜色即可)，字体颜色不可更改，所以一直为黑色)
        } else {
            immersionBar.fitsSystemWindows(true)
                    .statusBarColor(lowStatusBarColor)
                    .keyboardEnable(true)
                    .init();
        }
    }

    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * Android M 全局权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化点击事件
     */
    protected void initEvent() {
    }

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
     * 保存activity每个生命周期的状态。
     */
    public enum ACTIVITY_STATUS {
        START, RESUME, PAUSE, STOP, DESTROY
    }
}
