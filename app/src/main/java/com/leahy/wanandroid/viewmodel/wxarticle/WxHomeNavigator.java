package com.leahy.wanandroid.viewmodel.wxarticle;

import com.leahy.wanandroid.bean.wxarticle.ChaptersBean;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/27
 * Description: WxHomeNavigator
 */
public interface WxHomeNavigator {

    void showChapters(List<ChaptersBean> beans);

    void loadError();
}
