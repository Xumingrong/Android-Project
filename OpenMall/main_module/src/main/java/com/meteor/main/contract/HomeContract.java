package com.meteor.main.contract;

import android.content.Context;

import com.meteor.main.bean.ArticleListBean;

import java.util.List;

/**
 * Author：Meteor
 * date：2018/8/3 15:44
 * desc：首页接口类：home
 */
public class HomeContract {
    public interface ArticleView {
        //获取首页数据成功
        void onArticleListSuccess(List<ArticleListBean.DatasBean> list);

        //获取首页数据失败
        void onArticleListFail(String msg);

        //获取首页数据错误
        void onArticleListError();
    }

    public interface ArticleModel {
        //请求数据，回调
        void getArticleData(Context context, int page, IArticleListModelCallback callback);

        //取消请求
        void cancelHttpRequest();
    }

    public interface IArticleListModelCallback {
        void onSuccess(List<ArticleListBean.DatasBean> response);

        void onFail(String msg);

        void onError();
    }
}
