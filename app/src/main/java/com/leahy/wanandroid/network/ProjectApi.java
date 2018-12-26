package com.leahy.wanandroid.network;

import android.content.Context;

import com.leahy.utils.network.JsonCallback;
import com.leahy.utils.network.JsonCallbackArray;
import com.leahy.wanandroid.bean.project.BannerBean;
import com.leahy.wanandroid.bean.project.HotKeyBean;
import com.leahy.wanandroid.bean.project.ProjectBean;
import com.leahy.wanandroid.network.http.Callback;
import com.leahy.wanandroid.network.http.YcxOkGo;
import com.lzy.okgo.OkGo;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/24
 * Description: ProjectApi
 */
public class ProjectApi {

    private static String Url(String url) {
        return YcxOkGo.getUrl() + url;
    }


    /**
     * 首页banner
     * http://www.wanandroid.com/banner/json
     * <p>
     * 方法：GET
     * 参数：无
     */
    public static void getBanner(Context context, JsonCallbackArray<BannerBean> callbackArray) {
        OkGo.<String>get(Url("/banner/json"))
                .tag(context)
                .execute(new Callback<>(BannerBean.class, callbackArray));
    }


    /**
     * 首页文章列表
     * http://www.wanandroid.com/article/list/0/json
     * <p>
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    public static void getNewBlog(Context context, int page, JsonCallback<ProjectBean> callback) {
        OkGo.<String>get(Url("/article/list/" + page + "/json"))
                .tag(context)
                .execute(new Callback<>(ProjectBean.class, callback));
    }


    /**
     * 最新项目tab (首页的第二个tab)
     * 按时间分页展示所有项目。
     * <p>
     * http://wanandroid.com/article/listproject/0/json
     * <p>
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    public static void getNewProject(Context context, int page, JsonCallback<ProjectBean> callback) {
        OkGo.<String>get(Url("/article/listproject/" + page + "/json"))
                .tag(context)
                .execute(new Callback<>(ProjectBean.class, callback));
    }


    /**
     * 搜索热词
     * 即目前搜索最多的关键词。
     * <p>
     * http://www.wanandroid.com/hotkey/json
     * <p>
     * 方法：GET
     * 参数：无
     */
    public static void getHotKey(Context context, JsonCallbackArray<HotKeyBean> callback) {
        OkGo.<String>get(Url("/hotkey/json"))
                .tag(context)
                .execute(new Callback<>(HotKeyBean.class, callback));

    }


    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * <p>
     * 方法：POST
     * 参数：
     * 页码：拼接在链接上，从0开始。
     *
     * @param key k ： 搜索关键词
     */
    public static void postQuery(Context context, String key, int page, JsonCallback<ProjectBean> callback) {
        OkGo.<String>post(Url("/article/query/" + page + "/json"))
                .tag(context)
                .params("k", key)
                .execute(new Callback<>(ProjectBean.class, callback));

    }
}
