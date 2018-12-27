package com.leahy.wanandroid.ui.wxarticle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.leahy.utils.base.BaseFragment;
import com.leahy.utils.statusbar.StatusBarUtil;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.bean.wxarticle.ChaptersBean;
import com.leahy.wanandroid.databinding.FragmentWxArticleHomeBinding;
import com.leahy.wanandroid.viewmodel.wxarticle.WxHomeNavigator;
import com.leahy.wanandroid.viewmodel.wxarticle.WxHomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/24
 * Description: 公众号主页
 */
public class WxHomeFragment extends BaseFragment<FragmentWxArticleHomeBinding> implements WxHomeNavigator, TextWatcher, TextView.OnEditorActionListener {


    private WxHomeViewModel mViewModel;
    private List<Fragment> mFragments;

    public static WxHomeFragment newInstance() {
        return new WxHomeFragment();
    }


    @Override
    public int setContent() {
        return R.layout.fragment_wx_article_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isPrepared = true;
        loadData();
    }

    private void initView() {
        mViewModel = new WxHomeViewModel(mActivity, this);
        mBinding.setView(this);
        mBinding.setIsShowClose(false);

        //状态栏
        StatusBarUtil.setPaddingSmart(mActivity, mBinding.toolBar);
        StatusBarUtil.setPaddingSmart(mActivity, mBinding.blurView);
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
    public void showChapters(List<ChaptersBean> beans) {
        showContentView();
        isFirst = false;
        mFragments = new ArrayList<>();


    }

    @Override
    public void loadError() {
        isFirst = true;
        showError();
    }

    public void closeInput() {
        KeyboardUtils.showSoftInput(mActivity);
        mBinding.search.setText("");
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    /**
     * 回车开始搜索
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (TextUtils.isEmpty(mBinding.search.getText())) return true;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //TODO 搜索
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.destroy();
    }
}
