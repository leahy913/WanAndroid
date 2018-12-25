package com.leahy.utils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<?> mFragment;
    private List<String> mTitleList;
    private FragmentManager fm;


    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.mFragment = mFragment;
    }


    public BaseFragmentPagerAdapter(FragmentManager fm, List<?> mFragment) {
        super(fm);
        this.fm = fm;
        this.mFragment = mFragment;
    }

    /**
     * 接收首页传递的标题
     */
    public BaseFragmentPagerAdapter(FragmentManager fm, List<?> mFragment, List<String> mTitleList) {
        super(fm);
        this.fm = fm;
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }


    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        } else {
            return "";
        }
    }


    public void addFragmentList(List<?> fragment) {
        this.mFragment.clear();
        this.mFragment = null;
        this.mFragment = fragment;
        notifyDataSetChanged();
    }

    public void addFragmentList(List<?> fragment, List<String> mTitleList) {
        this.mFragment.clear();
        this.mFragment = null;
        this.mFragment = fragment;
        this.mTitleList = null;
        this.mTitleList = mTitleList;
        notifyDataSetChanged();
    }


    public void cleanFragments() {
        if (this.mFragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Object o : this.mFragment) {
                ft.remove((Fragment) o);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
    }


}
