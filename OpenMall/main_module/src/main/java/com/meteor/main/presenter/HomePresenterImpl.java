package com.meteor.main.presenter;

import android.content.Context;

import com.meteor.common.base.BasePresenter;
import com.meteor.main.bean.ArticleListBean;
import com.meteor.main.contract.HomeContract;
import com.meteor.main.model.HomeModel;

import java.util.List;

/**
 * Author：Meteor
 * date：2018/8/3 16:19
 * desc：
 */
public class HomePresenterImpl extends BasePresenter<HomeContract.ArticleView> {
    private HomeModel model;

    public HomePresenterImpl() {
        model = new HomeModel();
    }

    public void requestHomeDatas(Context context, int page) {

        model.getArticleData(context, page, new HomeContract.IArticleListModelCallback() {
            @Override
            public void onSuccess(List<ArticleListBean.DatasBean> response) {
                if (getMvpView() != null) {
                    getMvpView().onArticleListSuccess(response);
                }
            }

            @Override
            public void onFail(String msg) {
                getMvpView().onArticleListFail(msg);
            }

            @Override
            public void onError() {
                getMvpView().onArticleListError();
            }
        });
    }
}
