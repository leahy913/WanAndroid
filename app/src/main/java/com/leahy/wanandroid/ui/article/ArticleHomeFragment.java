package com.leahy.wanandroid.ui.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.leahy.utils.adapter.BaseTabAdapter;
import com.leahy.utils.base.BaseFragment;
import com.leahy.utils.statusbar.StatusBarUtil;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.databinding.FragmentArticleHomeBinding;
import com.leahy.wanandroid.viewmodel.article.ArticleHomeNavigator;
import com.leahy.wanandroid.viewmodel.article.ArticleHomeViewModel;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/24
 * Description: 公众号主页
 */
public class ArticleHomeFragment extends BaseFragment<FragmentArticleHomeBinding> implements ArticleHomeNavigator, TextWatcher, TextView.OnEditorActionListener {


    private ArticleHomeViewModel mViewModel;
    private boolean isVisibleToUser = true;

    public static ArticleHomeFragment newInstance() {
        return new ArticleHomeFragment();
    }


    @Override
    public int setContent() {
        return R.layout.fragment_article_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isPrepared = true;
        loadData();
    }

    private void initView() {
        mViewModel = new ArticleHomeViewModel(mActivity, this);
        mBinding.setView(this);
        mBinding.setIsShowClose(false);
        //状态栏
        StatusBarUtil.setPaddingSmart(mActivity, mBinding.layoutGroup);
        mBinding.search.addTextChangedListener(this);
        mBinding.search.setOnEditorActionListener(this);
    }


    @Override
    protected void loadData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        mViewModel.loadData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isFirst) this.isVisibleToUser = isVisibleToUser;
    }

    @Override
    public void showChapters(List<String> tabs) {
        showContentView();
        isFirst = false;
        mBinding.mViewPager.setAdapter(new BaseTabAdapter(getChildFragmentManager(), mViewModel.mFragments, tabs));
        mBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mBinding.tabLayout.setupWithViewPager(mBinding.mViewPager);
    }

    @Override
    public void loadError() {
        isFirst = true;
        showError();
    }

    public void closeInput() {
        mBinding.search.setText("");
        ((ArticleFragment) mViewModel.mFragments.get(mBinding.mViewPager.getCurrentItem())).search(null);
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


    /**
     * 回车开始搜索
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (TextUtils.isEmpty(mBinding.search.getText())) return true;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            KeyboardUtils.hideSoftInput(mActivity);
            ((ArticleFragment) mViewModel.mFragments.get(mBinding.mViewPager.getCurrentItem())).search(mBinding.search.getText().toString());
        }
        return false;
    }

    public void scrollToTop() {
        if (isVisibleToUser) {
            isVisibleToUser = false;
            return;
        }
        ((ArticleFragment) mViewModel.mFragments.get(mBinding.mViewPager.getCurrentItem())).scrollToTop();
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) mBinding.appbar.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            final AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != 0) {
                mBinding.appbar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        appBarLayoutBehavior.setTopAndBottomOffset(0);
                    }
                }, 100);
            }
        }
    }

    /**
     * 获取搜索关键字（子Fragment调用）
     */
    public String getSearchKey() {
        return TextUtils.isEmpty(mBinding.search.getText()) ? "" : mBinding.search.getText().toString();
    }

    /**
     * 设置输入框光标时候显示（子Fragment调用）
     */
    public void setCursorVisible(boolean visible) {
        mBinding.search.setCursorVisible(visible);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.destroy();
    }


}
