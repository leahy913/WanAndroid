package com.leahy.wanandroid.viewmodel.article;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.leahy.utils.network.JsonCallbackArray;
import com.leahy.wanandroid.bean.article.ChaptersBean;
import com.leahy.wanandroid.network.ArticleApi;
import com.leahy.wanandroid.ui.article.ArticleFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/27
 * Description: ArticleHomeViewModel
 */
public class ArticleHomeViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Activity mActivity;
    private ArticleHomeNavigator navigator;

    public List<Fragment> mFragments;


    public ArticleHomeViewModel(Activity mActivity, ArticleHomeNavigator navigator) {
        this.mActivity = mActivity;
        this.navigator = navigator;
    }

    public void loadData() {
        ArticleApi.getChapters(mActivity, new JsonCallbackArray<ChaptersBean>() {
            @Override
            public void onSuccess(List<ChaptersBean> result) {
                mFragments = new ArrayList<>();
                List<String> mTabs= new ArrayList<>();
                for (ChaptersBean beans : result) {
                    mTabs.add(beans.getName());
                    mFragments.add(ArticleFragment.newInstance(beans.getId()));
                }
                navigator.showChapters(mTabs);
            }

            @Override
            public void onError(int code) {
                navigator.loadError();
            }
        });

    }

    public void destroy() {
        navigator = null;
    }
}
