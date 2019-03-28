package com.meteor.main.api;

import com.meteor.common.base.BaseResponseBean;
import com.meteor.main.bean.ArticleListBean;
import com.meteor.main.bean.BannerBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Author：Meteor
 * date：2018/8/6 11:37
 * desc：Api
 */
public interface Api {
    /**
     * 例子
     * 首页banner图
     */
    @GET("banner/json")
    Observable<BaseResponseBean<List<BannerBean>>> getBannerImgs();

    /**
     * 例子
     * 文章列表数据
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponseBean<ArticleListBean>> getArticleList(@Path("page") int page);

//    @FormUrlEncoded
//    @POST("Product/loadProductByType")
//    Observable<BaseResponseBean<ItemProduct>> getInvestData(@FieldMap Map<String, String> map);

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    @Streaming
    @GET()
    Observable<ResponseBody> downFile(@Url() String url);


}
