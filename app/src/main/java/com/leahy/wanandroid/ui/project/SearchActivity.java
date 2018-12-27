package com.leahy.wanandroid.ui.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.leahy.utils.base.BaseActivity;
import com.leahy.utils.utils.CommonUtils;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.adapter.BaseDataBindingAdapter;
import com.leahy.wanandroid.adapter.project.HotKeyAdapter;
import com.leahy.wanandroid.adapter.project.SearchResultAdapter;
import com.leahy.wanandroid.app.App;
import com.leahy.wanandroid.bean.project.HotKeyBean;
import com.leahy.wanandroid.bean.project.ProjectBean;
import com.leahy.wanandroid.databinding.ActivitySearchBinding;
import com.leahy.wanandroid.viewmodel.projrct.SearchNavigator;
import com.leahy.wanandroid.viewmodel.projrct.SearchViewModel;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/26
 * Description: 搜索
 */
public class SearchActivity extends BaseActivity<ActivitySearchBinding> implements SearchNavigator, BaseDataBindingAdapter.OnItemClickListener<HotKeyBean>, TextWatcher, TextView.OnEditorActionListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SearchViewModel mViewModel;
    private HotKeyAdapter mHotKeyAdapter;
    private SearchResultAdapter mSearchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        showContentView();
        hideToolbar();
        initView();
        initHistory();
    }

    private void initView() {
        mViewModel = new SearchViewModel(mActivity, this);
        mBinding.setView(this);
        mBinding.setIsShowHot(false);
        mBinding.setIsShowClose(false);
        mBinding.refresh.setColorSchemeResources(R.color.colorAccent);
        mBinding.refresh.setOnRefreshListener(this);
        mBinding.refresh.setEnabled(false);
        mBinding.search.addTextChangedListener(this);
        mBinding.search.setOnEditorActionListener(this);
        //初始化热门搜索
        mBinding.hotRecycler.setLayoutManager(ChipsLayoutManager.newBuilder(mActivity)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setScrollingEnabled(false)
                .build());
        mHotKeyAdapter = new HotKeyAdapter(null);
        mHotKeyAdapter.setOnItemClickListener(this);
        mBinding.hotRecycler.setAdapter(mHotKeyAdapter);
        //初始化搜索结果列表
        mBinding.resultRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mSearchResultAdapter = new SearchResultAdapter(mActivity, null);
        mSearchResultAdapter.setOnLoadMoreListener(this);
        mBinding.resultRecycler.setAdapter(mSearchResultAdapter);

    }


    private void initHistory() {
        //TODO 后续添加
    }


    @Override
    public void showHotKey(List<HotKeyBean> mHotKeys) {
        mBinding.setIsShowHot(true);
        mHotKeyAdapter.setNewData(mHotKeys);
    }

    @Override
    public void loadHotKeyError() {
        mBinding.setIsShowHot(false);
    }

    @Override
    public void onRefresh() {
        mViewModel.setPage(App.START_PAGE);
        mViewModel.loadSearchData();
    }

    @Override
    public void onLoadMoreRequested() {
        mViewModel.loadSearchData();
    }


    @Override
    public void searchError() {
        mBinding.refresh.setRefreshing(false);
    }


    public void back() {
        onBackPressed();
    }

    public void closeInput() {
        KeyboardUtils.showSoftInput(mActivity);
        mBinding.search.setText("");
        mBinding.refresh.setEnabled(false);
        mSearchResultAdapter.setNewData(null);
    }


    /**
     * 点击热词搜索
     */
    @Override
    public void onItemClick(View view, HotKeyBean bean, int position) {
        if (bean.getName().equals(mViewModel.hotKey)) return;
        mBinding.search.setText(bean.getName());
        //将光标移至文字末尾
        mBinding.search.setSelection(bean.getName().length());
        search();
    }

    /**
     * 回车键监听，开始搜索
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (TextUtils.isEmpty(mBinding.search.getText())) return true;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search();
        }
        return false;
    }


    private void search() {
        if (TextUtils.isEmpty(mBinding.search.getText())) return;
        if (!mBinding.refresh.isRefreshing()) mBinding.refresh.setRefreshing(true);
        mViewModel.setPage(App.START_PAGE);
        mViewModel.setKey(mBinding.search.getText().toString());
        mSearchResultAdapter.setKey(mViewModel.getKey());
        mViewModel.loadSearchData();
    }

    /**
     * 搜索结果
     */
    @Override
    public void showSearchResult(List<ProjectBean.DatasBean> beans, boolean isOver) {
        KeyboardUtils.hideSoftInput(mActivity);
        mBinding.refresh.setEnabled(true);
        mBinding.refresh.setRefreshing(false);
        if (mViewModel.getPage() == App.START_PAGE) {
            mSearchResultAdapter.setNewData(beans);
        } else {
            mSearchResultAdapter.addData(beans);
        }
        if (isOver) {
            mSearchResultAdapter.loadMoreEnd(true);
        } else {
            mSearchResultAdapter.loadMoreComplete();
        }

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mBinding.setIsShowClose(s.length() > 0);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.destroy();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }


}
