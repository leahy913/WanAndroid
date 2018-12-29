package com.leahy.wanandroid.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leahy.utils.base.BaseFragment;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.databinding.FragmentProjectHomeBinding;
import com.leahy.wanandroid.viewmodel.projrct.HomeNavigator;
import com.leahy.wanandroid.viewmodel.projrct.HomeViewModel;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/24
 * Description: NullFragment
 */
public class NullFragment extends BaseFragment<FragmentProjectHomeBinding>  {




    public static NullFragment newInstance() {
        return new NullFragment();
    }


    @Override
    public int setContent() {
        return R.layout.layout_empty;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       showContentView();
    }

}
