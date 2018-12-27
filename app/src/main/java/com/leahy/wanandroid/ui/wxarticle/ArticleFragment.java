package com.leahy.wanandroid.ui.wxarticle;

import android.os.Bundle;

import com.leahy.utils.base.BaseFragment;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/27
 * Description: ArticleFragment
 */
public class ArticleFragment extends BaseFragment {


    private static final String ID = "id";
    private String id;

    public static ArticleFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(ID, id);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setContent() {
        return 0;
    }
}
