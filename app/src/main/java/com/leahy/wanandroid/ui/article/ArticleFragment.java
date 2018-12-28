package com.leahy.wanandroid.ui.article;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewTreeObserver;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.leahy.utils.base.BaseFragment;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.adapter.article.ArticleAdapter;
import com.leahy.wanandroid.app.App;
import com.leahy.wanandroid.bean.project.ProjectBean;
import com.leahy.wanandroid.databinding.FragmentArticleRecyclerBinding;
import com.leahy.wanandroid.viewmodel.article.ArticleNavigator;
import com.leahy.wanandroid.viewmodel.article.ArticleViewModel;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/27
 * Description: 公众号文章列表
 */
public class ArticleFragment extends BaseFragment<FragmentArticleRecyclerBinding> implements ArticleNavigator, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, ViewTreeObserver.OnGlobalLayoutListener {

    private static final String ID = "id";
    private ArticleViewModel mViewModel;
    private ArticleAdapter mAdapter;
    private ArticleHomeFragment mGroupFragment;

    public static ArticleFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(ID, id);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGroupFragment = (ArticleHomeFragment) getParentFragment();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_article_recycler;
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
        mViewModel = new ArticleViewModel(mActivity, getArguments().getString(ID), this);
        mBinding.refresh.setColorSchemeResources(R.color.colorAccent);
        mBinding.refresh.setOnRefreshListener(this);

        mBinding.recycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ArticleAdapter(mActivity, null);
        mAdapter.setOnLoadMoreListener(this);
        mBinding.recycler.setAdapter(mAdapter);
        mBinding.recycler.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    /**
     * 根据键盘显示状态来显示输入框光标
     */
    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        mBinding.recycler.getWindowVisibleDisplayFrame(r);
        int screenHeight = mBinding.recycler.getRootView().getHeight();
        int heightDifference = screenHeight - (r.bottom);
        if (heightDifference > 200) { //软键盘显示 显示输入框光标
            mGroupFragment.setCursorVisible(true);
        } else {
            mGroupFragment.setCursorVisible(false);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirst) return;
        if (!mGroupFragment.getSearchKey().equals(mViewModel.getKey())) {
            mBinding.refresh.setRefreshing(true);
            mViewModel.setKey(mGroupFragment.getSearchKey());
            mViewModel.setPage(App.START_PAGES);
            mViewModel.loadData();
        }
    }

    @Override
    protected void loadData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        mBinding.refresh.setRefreshing(true);
        mViewModel.setKey(mGroupFragment.getSearchKey());
        mViewModel.setPage(App.START_PAGES);
        mViewModel.loadData();
    }


    public void scrollToTop() {
        mBinding.recycler.smoothScrollToPosition(0);
    }


    public void search(String key) {
        scrollToTop();
        mViewModel.setKey(key);
        mBinding.refresh.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mViewModel.setPage(App.START_PAGES);
        mViewModel.loadData();
    }


    @Override
    public void onLoadMoreRequested() {
        mViewModel.loadData();
    }


    @Override
    public void showArticleList(List<ProjectBean.DatasBean> articles, boolean isOver) {
        isFirst = false;
        showContentView();
        mBinding.refresh.setRefreshing(false);
        if (mViewModel.getPage() == App.START_PAGES) {
            mAdapter.setKey(mViewModel.getKey());
            mAdapter.setNewData(articles);
        } else {
            mAdapter.addData(articles);
        }
        if (isOver) {
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadArticleError() {
        if (mViewModel.getPage() > App.START_PAGES) {
            mAdapter.loadMoreFail();
        } else {
            isFirst = true;
            showError();
            mBinding.refresh.setRefreshing(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.destroy();
    }


}
