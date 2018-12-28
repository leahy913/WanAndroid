package com.leahy.wanandroid.network;

import android.content.Context;

import com.leahy.utils.network.JsonCallback;
import com.leahy.utils.network.JsonCallbackArray;
import com.leahy.wanandroid.bean.project.ProjectBean;
import com.leahy.wanandroid.bean.article.ChaptersBean;
import com.leahy.wanandroid.network.http.Callback;
import com.leahy.wanandroid.network.http.YcxOkGo;
import com.lzy.okgo.OkGo;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/27
 * Description: ArticleApi
 */
public class ArticleApi {

    private static String Url(String url) {
        return YcxOkGo.getUrl() + url;
    }


    /**
     * 获取公众号列表
     * http://wanandroid.com/wxarticle/chapters/json
     * 方法： GET
     */
    public static void getChapters(Context context, JsonCallbackArray<ChaptersBean> callbackArray) {
        OkGo.<String>get(Url("/wxarticle/chapters/json"))
                .tag(context)
                .execute(new Callback<>(ChaptersBean.class, callbackArray));

    }


    /**
     * 查看某个公众号历史数据
     * http://wanandroid.com/wxarticle/list/405/1/json
     * 方法：GET
     * 参数：
     * 公众号 ID：拼接在 url 中，eg:405
     * 公众号页码：拼接在url 中，eg:1
     */
    public static void getArticle(Context context, String id, int page, JsonCallback<ProjectBean> callback) {
        OkGo.<String>get(Url("/wxarticle/list/" + id + "/" + page + "/json"))
                .tag(context)
                .execute(new Callback<>(ProjectBean.class, callback));

    }


    /**
     * 在某个公众号中搜索历史文章
     * http://wanandroid.com/wxarticle/list/405/1/json?k=Java
     * 方法：GET
     * 参数 ：
     * k : 字符串，eg:Java
     * 公众号 ID：拼接在 url 中，eg:405
     * 公众号页码：拼接在url 中，eg:1
     */
    public static void search(Context context, String id, int page, String key, JsonCallback<ProjectBean> callback) {
        OkGo.<String>get(Url("/wxarticle/list/" + id + "/" + page + "/json"))
                .tag(context)
                .params("k", key)
                .execute(new Callback<>(ProjectBean.class, callback));

    }

}
