package com.leahy.wanandroid.viewmodel.projrct;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModel;

import com.leahy.utils.network.JsonCallback;
import com.leahy.utils.network.JsonCallbackArray;
import com.leahy.wanandroid.bean.project.HotKeyBean;
import com.leahy.wanandroid.bean.project.ProjectBean;
import com.leahy.wanandroid.network.ProjectApi;
import com.lzy.okgo.OkGo;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/26
 * Description: SearchViewModel
 */
public class SearchViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Activity mActivity;
    private SearchNavigator navigator;
    public String hotKey = "搜索热词";

    private int page;
    private String key;


    public SearchViewModel(Activity mActivity, SearchNavigator navigator) {
        this.mActivity = mActivity;
        this.navigator = navigator;
        loadHotKey();
    }

    private void loadHotKey() {
        ProjectApi.getHotKey(mActivity, new JsonCallbackArray<HotKeyBean>() {
            @Override
            public void onSuccess(List<HotKeyBean> result) {
                if (result != null && result.size() > 0) {
                    HotKeyBean bean = new HotKeyBean();
                    bean.setName(hotKey);
                    result.add(0, bean);
                    navigator.showHotKey(result);
                } else {
                    navigator.loadHotKeyError();
                }
            }

            @Override
            public void onError(int code) {
                navigator.loadHotKeyError();
            }
        });

    }

    public void loadSearchData() {
        ProjectApi.postQuery(mActivity, key, page, new JsonCallback<ProjectBean>() {
            @Override
            public void onSuccess(ProjectBean result) {
                page = result.getCurPage();
                navigator.showSearchResult(result.getDatas(), result.isOver());
            }

            @Override
            public void onError(int code) {
                navigator.searchError();
            }
        });
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void destroy() {
        OkGo.getInstance().cancelTag(mActivity);
        navigator = null;
    }
}
