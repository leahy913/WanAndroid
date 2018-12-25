package com.leahy.utils.listener;

import android.support.v4.widget.NestedScrollView;


/**
 * Author: leahy
 * Time:   2018/5/24
 * Description: NestedScrollView 滑动方向
 */


public abstract class ScrollDirectionListener implements NestedScrollView.OnScrollChangeListener {

    /**
     * 最小的滑动距离
     */
    private static final int SCROLLLIMIT = 10;


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (oldScrollY > scrollY && oldScrollY - scrollY > SCROLLLIMIT) {
            // 向下
            onScrollDirection(true);
        } else if (oldScrollY < scrollY && scrollY - oldScrollY > SCROLLLIMIT) {
            // 向上
            onScrollDirection(false);
        }
    }


    /**
     * direction true 向下滑动
     * <p>
     * direction false 向上滑动
     */
    protected abstract void onScrollDirection(boolean direction);
}
