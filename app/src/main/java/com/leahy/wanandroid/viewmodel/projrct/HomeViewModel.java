package com.leahy.wanandroid.viewmodel.projrct;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModel;

import com.leahy.utils.network.JsonCallbackArray;
import com.leahy.wanandroid.bean.project.BannerBean;
import com.leahy.wanandroid.network.ProjectApi;

import java.util.ArrayList;
import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/24
 * Description: HomeViewModel
 */
public class HomeViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Activity mActivity;
    private HomeNavigator navigator;
    public List<BannerBean> mBanners;


    public HomeViewModel(Activity mActivity, HomeNavigator navigator) {
        this.mActivity = mActivity;
        this.navigator = navigator;
    }

    public void loadBanner() {
        ProjectApi.getBanner(mActivity, new JsonCallbackArray<BannerBean>() {
            @Override
            public void onSuccess(List<BannerBean> result) {
                mBanners = result;
                List<String> paths = new ArrayList<>();
                for (BannerBean mBanner : mBanners) {
                    paths.add(mBanner.getImagePath());
                }
                navigator.initBanner(paths);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    public void destroy() {
        navigator = null;
    }


}
