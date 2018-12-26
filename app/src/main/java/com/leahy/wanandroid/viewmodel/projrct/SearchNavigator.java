package com.leahy.wanandroid.viewmodel.projrct;

import com.leahy.wanandroid.bean.project.HotKeyBean;
import com.leahy.wanandroid.bean.project.ProjectBean;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/26
 * Description: SearchNavigator
 */
public interface SearchNavigator {

    void showHotKey(List<HotKeyBean> mHotKeys);

    void loadHotKeyError();

    void showSearchResult(List<ProjectBean.DatasBean>  beans, boolean isOver);

    void searchError();
}
