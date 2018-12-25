package com.leahy.wanandroid.viewmodel.projrct;

import com.leahy.wanandroid.bean.project.ProjectBean;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/25
 * Description: BlogNavigator
 */
public interface BlogNavigator {


    void showBlogList(List<ProjectBean.DatasBean> blogs,boolean isOver);

    void loadBlogError();

}
