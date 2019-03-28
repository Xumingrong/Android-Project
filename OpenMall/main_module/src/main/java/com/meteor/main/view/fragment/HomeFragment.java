package com.meteor.main.view.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.meteor.common.base.BaseMvpFragment;
import com.meteor.main.R;

import com.meteor.main.bean.ArticleListBean;
import com.meteor.main.contract.HomeContract;
import com.meteor.main.presenter.HomePresenterImpl;

import java.util.List;

/**
 * Author：Meteor
 * date：2018/8/1 10:34
 * desc：首页Fragment页面
 */
public class HomeFragment extends BaseMvpFragment<HomeContract.ArticleView, HomePresenterImpl> implements HomeContract.ArticleView, BaseQuickAdapter.OnItemClickListener {
    private List<ArticleListBean.DatasBean> mDatas;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenterImpl createPresenter() {
        return new HomePresenterImpl();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {
        getPresenter().requestHomeDatas(context, 2);
    }

    @Override
    public void onArticleListSuccess(List<ArticleListBean.DatasBean> lists) {
        if (lists != null && lists.size() > 0) {
            mDatas.clear();

        }
    }

    @Override
    public void onArticleListFail(String msg) {

    }

    @Override
    public void onArticleListError() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
