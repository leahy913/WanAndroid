package com.leahy.wanandroid.viewmodel.wxarticle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.leahy.utils.network.JsonCallbackArray;
import com.leahy.wanandroid.bean.wxarticle.ChaptersBean;
import com.leahy.wanandroid.network.WxArticleApi;

import java.util.ArrayList;
import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/27
 * Description: WxHomeViewModel
 */
public class WxHomeViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Activity mActivity;
    private WxHomeNavigator navigator;

    private List<Fragment> mFragments;


    public WxHomeViewModel(Activity mActivity, WxHomeNavigator navigator) {
        this.mActivity = mActivity;
        this.navigator = navigator;
    }

    public void loadData() {
        WxArticleApi.getChapters(mActivity, new JsonCallbackArray<ChaptersBean>() {
            @Override
            public void onSuccess(List<ChaptersBean> result) {
                navigator.showChapters(result);
                mFragments = new ArrayList<>();
                for (ChaptersBean chaptersBean : result) {

                }
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
