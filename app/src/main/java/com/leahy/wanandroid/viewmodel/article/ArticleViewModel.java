package com.leahy.wanandroid.viewmodel.article;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.leahy.utils.network.JsonCallback;
import com.leahy.wanandroid.bean.project.ProjectBean;
import com.leahy.wanandroid.network.ArticleApi;
import com.lzy.okgo.OkGo;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/28
 * Description: ArticleViewModel
 */
public class ArticleViewModel extends ViewModel implements JsonCallback<ProjectBean> {

    @SuppressLint("StaticFieldLeak")
    private Activity mActivity;
    private ArticleNavigator navigator;
    private int page;
    private String id;
    private String key;


    public ArticleViewModel(Activity mActivity, String id, ArticleNavigator navigator) {
        this.mActivity = mActivity;
        this.navigator = navigator;
        this.id = id;
    }


    /**
     * 加载公众号历史数据
     */
    public void loadData() {
        if (TextUtils.isEmpty(key)) {
            ArticleApi.getArticle(mActivity, id, page, this);
        } else {
            ArticleApi.search(mActivity, id, page, key, this);
        }
    }

    @Override
    public void onSuccess(ProjectBean result) {
        navigator.showArticleList(result.getDatas(), result.isOver());
        page = page + 1;
    }

    @Override
    public void onError(int code) {
        navigator.loadArticleError();
    }


    public void destroy() {
        navigator = null;
        OkGo.getInstance().cancelTag(mActivity);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKey() {
        return TextUtils.isEmpty(key) ? "" : key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
