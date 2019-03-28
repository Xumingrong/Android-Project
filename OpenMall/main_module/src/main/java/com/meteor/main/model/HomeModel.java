package com.meteor.main.model;

import android.content.Context;

import com.meteor.common.base.BaseResponseBean;
import com.meteor.common.http.RxObserver;
import com.meteor.common.http.RxRetrofitManager;
import com.meteor.common.http.cancle.ApiCancelManager;
import com.meteor.main.api.Api;
import com.meteor.main.bean.ArticleListBean;
import com.meteor.main.contract.HomeContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Meteor
 * date：2018/8/6 10:44
 * desc：
 */
public class HomeModel implements HomeContract.ArticleModel {
    @Override
    public void getArticleData(Context context, int page, final HomeContract.IArticleListModelCallback callback) {
        RxRetrofitManager.getInstance()
                .setTag("articleList")
                .getApiService(Api.class)
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<BaseResponseBean<ArticleListBean>>(context, true) {
                    @Override
                    public void onSuccess(BaseResponseBean<ArticleListBean> response) {
                        if (callback != null) {
                            callback.onSuccess(response.getData().getDatas());
                        }
                    }

                    @Override
                    public void onFailed(BaseResponseBean<ArticleListBean> response) {
                        if (callback != null) {
                            callback.onFail(response.getMsg());
                        }
                    }

                    @Override
                    public void onErrored() {
                        if (callback != null) {
                            callback.onError();
                        }
                    }
                });
    }

    @Override
    public void cancelHttpRequest() {
        ApiCancelManager.getInstance().cancel("articleList");
    }
}
