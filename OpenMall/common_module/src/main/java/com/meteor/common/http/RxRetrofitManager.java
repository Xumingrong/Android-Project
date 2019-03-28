package com.meteor.common.http;

import android.util.Log;

import com.meteor.common.http.cancle.ApiCancelManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：Meteor
 * date：2018/7/25 17:59
 * desc：网络管理
 */

public class RxRetrofitManager {
    private static RxRetrofitManager manager;
    private Retrofit retrofit;
    /*private RetrofitServer server;*/

    private RxRetrofitManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String msg = URLDecoder.decode(message, "utf-8");
                    Log.i("OkHttpInterceptor----->", msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.i("OkHttpInterceptor----->", message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /** OKHttp默认三个超时时间是10s，有些请求时间比较长，需要重新设置下 **/
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        /*server = retrofit.create(RetrofitServer.class);*/

    }

    /**
     * 单例模式
     */
    public static RxRetrofitManager getInstance() {
        if (manager == null) {
            synchronized (RxRetrofitManager.class) {
                if (manager == null) {
                    manager = new RxRetrofitManager();
                }
            }
        }
        return manager;
    }

    /*public RetrofitServer getApiService(){
        return manager.server;
    }*/

    /**
     * 获取Api
     *
     * @param apiServer
     * @param <T>
     * @return
     */
    public <T> T getApiService(Class<T> apiServer) {
        return retrofit.create(apiServer);
    }

    /**
     * 设置tag取消请求标签
     *
     * @param tag
     * @return
     */
    public RxRetrofitManager setTag(String tag) {
        ApiCancelManager.getInstance().setTagValue(tag);
        return manager;
    }
}
