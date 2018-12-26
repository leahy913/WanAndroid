package com.leahy.wanandroid.ui.project;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.leahy.utils.adapter.BaseFragmentPagerAdapter;
import com.leahy.utils.base.BaseFragment;
import com.leahy.utils.statusbar.StatusBarUtil;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.databinding.FragmentProjectHomeBinding;
import com.leahy.wanandroid.ui.webview.ActivityWeb;
import com.leahy.wanandroid.utils.GlideImageLoader;
import com.leahy.wanandroid.viewmodel.projrct.HomeNavigator;
import com.leahy.wanandroid.viewmodel.projrct.HomeViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/24
 * Description: 项目主页
 */
public class HomeFragment extends BaseFragment<FragmentProjectHomeBinding> implements HomeNavigator, OnBannerListener, OnRefreshListener {


    private HomeViewModel mViewModel;
    private List<Fragment> mFragments;
    private boolean isVisibleToUser = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public int setContent() {
        return R.layout.fragment_project_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initView();
        isPrepared = true;
        loadData();
    }


    private void initView() {
        mViewModel = new HomeViewModel(mActivity, this);
        mBinding.setView(this);
        mBinding.refresh.setOnRefreshListener(this);
        //状态栏
        StatusBarUtil.setMargin(mActivity, mBinding.include.header);
        StatusBarUtil.setPaddingSmart(mActivity, mBinding.mCoordinatorLayout);
        StatusBarUtil.setPaddingSmart(mActivity, mBinding.toolBar);
        StatusBarUtil.setPaddingSmart(mActivity, mBinding.blurView);
        //轮播图
        mBinding.banner.setImageLoader(new GlideImageLoader());
        mBinding.banner.setIndicatorGravity(BannerConfig.RIGHT);
        mBinding.banner.setOnBannerListener(this);
        //列表
        mFragments = new ArrayList<>();
        mFragments.add(BlogFragment.newInstance(BlogFragment.TYPE_BLOG));
        mFragments.add(BlogFragment.newInstance(BlogFragment.TYPE_PROJECT));
        mBinding.mViewPager.setAdapter(new BaseFragmentPagerAdapter(getChildFragmentManager(), mFragments, Arrays.asList("最新博文", "最新项目")));
        mBinding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mBinding.tabLayout.setupWithViewPager(mBinding.mViewPager);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isFirst) {
            this.isVisibleToUser = isVisibleToUser;
            if (isVisibleToUser) {
                mBinding.banner.startAutoPlay();
            } else {
                mBinding.banner.stopAutoPlay();
            }
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        mViewModel.loadBanner();
        isFirst = false;
    }

    @Override
    public void initBanner(List<String> path) {
        mBinding.banner.setImages(path).start();
    }

    @Override
    public void OnBannerClick(int position) {
        ActivityWeb.loadUrl(mActivity, mViewModel.mBanners.get(position).getUrl(), mViewModel.mBanners.get(position).getTitle());
    }

    public void startSearchActivity() {
        SearchActivity.start(mActivity);
    }

    public void scrollToTop() {
        if (isVisibleToUser) {
            isVisibleToUser = false;
            return;
        }
        mBinding.refresh.autoRefresh();
        ((BlogFragment) mFragments.get(mBinding.mViewPager.getCurrentItem())).scrollToTop();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        ((BlogFragment) mFragments.get(mBinding.mViewPager.getCurrentItem())).onRefresh();
    }

    public void finishRefresh() {
        mBinding.refresh.finishRefresh();
    }


    @Override
    public void onStart() {
        super.onStart();
        mBinding.banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBinding.banner.stopAutoPlay();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.destroy();
    }


}
