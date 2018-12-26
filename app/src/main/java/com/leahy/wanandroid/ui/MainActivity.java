package com.leahy.wanandroid.ui;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.leahy.utils.adapter.BaseTabAdapter;
import com.leahy.utils.statusbar.StatusBarUtil;
import com.leahy.utils.tablayout.hometab.TabGroupView;
import com.leahy.utils.tablayout.hometab.TabView;
import com.leahy.utils.utils.Show;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.databinding.ActivityMainBinding;
import com.leahy.wanandroid.ui.project.HomeFragment;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/24
 * Description: MainActivity
 */

public class MainActivity extends AppCompatActivity implements TabGroupView.OnItemClickListener {

    private ActivityMainBinding mBinding;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        StatusBarUtil.darkMode(this);
        initContentFragment();
    }

    private void initContentFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(NullFragment.newInstance());
        mBinding.viewPager.setAdapter(new BaseTabAdapter(getSupportFragmentManager(), mFragments));
        mBinding.tabGroup.setViewPager(mBinding.viewPager);
        mBinding.tabGroup.setOnItemClickListener(this);

    }

    @Override
    public void onClick(TabView tabLayout, int position) {
        mBinding.viewPager.setCurrentItem(position, false);
        if (position == 0) {
            ((HomeFragment) mFragments.get(0)).scrollToTop();
        }
    }


    // 返回键监听
    private long exitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Show.defaults("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                OkGo.getInstance().cancelAll();
                onBackPressed();
//                System.exit(0);
                // 不退出程序，进入后台
                // moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
