package com.leahy.wanandroid.ui.project;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.leahy.utils.base.BaseFragment;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.adapter.project.ProjectAdapter;
import com.leahy.wanandroid.app.App;
import com.leahy.wanandroid.bean.project.ProjectBean;
import com.leahy.wanandroid.databinding.FragmentHomeRecyclerViewBinding;
import com.leahy.wanandroid.viewmodel.projrct.BlogNavigator;
import com.leahy.wanandroid.viewmodel.projrct.BlogViewModel;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/25
 * Description: 最新博客
 */
public class BlogFragment extends BaseFragment<FragmentHomeRecyclerViewBinding> implements BlogNavigator, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TYPE = "type";
    public static final int TYPE_BLOG = 0;
    public static final int TYPE_PROJECT = 1;

    private int type;
    private ProjectAdapter mAdapter;
    private BlogViewModel mViewModel;
    private HomeFragment mHomeFragment;

    public static BlogFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        BlogFragment fragment = new BlogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHomeFragment = (HomeFragment) getParentFragment();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_home_recycler_view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isPrepared = true;
        loadData();
    }


    private void initView() {
        type = getArguments() != null ? getArguments().getInt(TYPE) : TYPE_BLOG;
        mViewModel = new BlogViewModel(mActivity, type, this);
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ProjectAdapter(null, mActivity);
        mAdapter.setOnLoadMoreListener(this);
        mBinding.recycler.setAdapter(mAdapter);
    }


    @Override
    protected void loadData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        onRefresh();
    }


    public void scrollToTop() {
        if (isFirst) return;
        mBinding.recycler.smoothScrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        mViewModel.setPage(App.START_PAGE);
        mViewModel.loadData();
    }

    @Override
    public void onLoadMoreRequested() {
        mViewModel.loadData();
    }

    @Override
    public void showBlogList(List<ProjectBean.DatasBean> blogs, boolean isOver) {
        showContentView();
        mHomeFragment.finishRefresh();
        isFirst = false;
        if (mViewModel.getPage() == App.START_PAGE) {
            mAdapter.setNewData(blogs);
        } else {
            mAdapter.addData(blogs);
        }
        if (isOver) {
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadBlogError() {
        if (mViewModel.getPage() > App.START_PAGES) {
            mAdapter.loadMoreFail();
        } else {
            isFirst = true;
            showError();
            mHomeFragment.finishRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.destroy();
    }


}
