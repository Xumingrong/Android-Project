package com.meteor.common.http;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.meteor.common.base.BaseResponseBean;
import com.meteor.common.http.cancle.ApiCancelManager;
import com.meteor.common.utils.DeviceUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * Author：Meteor
 * date：2018/7/25 17:59
 * desc：网络请求管理
 */

public abstract class RxObserver<T extends BaseResponseBean> implements Observer<T> {
    private Context context;
    //添加tag标签，统一管理请求(比如取消请求等操作)
    private String tag;
    //private CustomProgressDialog progressDialog;

    public RxObserver(Context context, boolean canCancle) {
        this.context = context;
        //弹窗进度条
        //createProgressDialog(context, canCancle);
    }

    public RxObserver(Context context, boolean canCancle, boolean isShowProgress) {
        this.context = context;
        //弹窗进度条
        if (isShowProgress) {
            //createProgressDialog(context, canCancle);
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        //显示进度条
        //showProgressDialog();
        //是否有网络
        if (DeviceUtils.isNetworkConnected(context)) {
            tag = ApiCancelManager.getInstance().getTagValue();
            if (!TextUtils.isEmpty(tag)) {
                ApiCancelManager.getInstance().add(tag, d);
            }
        } else {
            //没有网络的时候，调用error方法，并且切断与上游的联系
            if (!d.isDisposed()) {
                //stopProgressDialog();
                onErrored();
                solveException(ExceptionType.BAD_NETWORK);
                d.dispose();
                return;
            }
        }
    }

    @Override
    public void onNext(@NonNull T response) {
        //关闭弹窗进度条
        //stopProgressDialog();
        if (response.getCode() >= 0) {
            onSuccess(response);
        } else {
            onFailed(response);
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        //关闭弹窗进度条
        //stopProgressDialog();
        onErrored();
        onErrors(e);
    }


//    /**
//     * 创建进度条实例
//     */
//    public void createProgressDialog(Context cxt, boolean canCancle) {
//        try {
//            if (progressDialog != null) {
//                progressDialog.dismiss();
//                progressDialog = null;
//            }
//            if (progressDialog == null) {
//                progressDialog = CustomProgressDialog.createDialog(cxt, canCancle);
//                progressDialog.setCanceledOnTouchOutside(false);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 启动加载进度条
//     */
//    public void showProgressDialog(){
//        try {
//            if (progressDialog != null) {
//                progressDialog.show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 关闭加载进度条
//     */
//    public void stopProgressDialog() {
//        try {
//            if (progressDialog != null) {
//                progressDialog.dismiss();
//                progressDialog = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 请求成功回调
     *
     * @param response 服务端返回的数据
     */
    public abstract void onSuccess(T response);

    /**
     * 请求失败的回调 (非 200的情况)，开发者手动去触发
     *
     * @param response 数据
     */
    public abstract void onFailed(T response);

    /**
     * 请求错误的回调
     *
     * @param
     */
    public abstract void onErrored();


    /**
     * 连接异常时回调，手动触发
     */
    public void onErrors(@NonNull Throwable e) {
        if (e instanceof UnknownHostException || e instanceof ConnectException) {//无网络
            solveException(ExceptionType.BAD_NETWORK);
        } else if (e instanceof JsonParseException || e instanceof JSONException ||
                e instanceof ParseException) {//解析异常
            solveException(ExceptionType.PARSE_DATA_ERROR);
        } else if (e instanceof HttpException) {//http异常，比如 404 500
            solveException(ExceptionType.UNFOUND_ERROR);
        } else if (e instanceof SocketTimeoutException) {//连接超时
            solveException(ExceptionType.TIMEOUT_ERROR);
        } else {//未知错误
            solveException(ExceptionType.UNKNOWN_ERROR);
        }
    }

    /**
     * 对于异常情况的统一处理
     *
     * @param type 异常的类型
     */
    public void solveException(ExceptionType type) {
        switch (type) {
            case BAD_NETWORK:
                Toast.makeText(context, "无网络", Toast.LENGTH_SHORT).show();
                break;
            case PARSE_DATA_ERROR:
                Toast.makeText(context, "数据解析异常", Toast.LENGTH_SHORT).show();
                break;
            case UNFOUND_ERROR:
                Toast.makeText(context, "地址链接错误", Toast.LENGTH_SHORT).show();
                break;
            case TIMEOUT_ERROR:
                Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
                break;
            case UNKNOWN_ERROR:
                Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public enum ExceptionType {
        /**
         * 无网络
         */
        BAD_NETWORK,
        /**
         * 数据解析异常
         */
        PARSE_DATA_ERROR,
        /**
         * 找不到相关连接
         */
        UNFOUND_ERROR,
        /**
         * 连接超时
         */
        TIMEOUT_ERROR,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR
    }
}
