package com.leahy.wanandroid.viewmodel.article;

import com.leahy.wanandroid.bean.project.ProjectBean;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/28
 * Description: ArticleNavigator
 */
public interface ArticleNavigator {


    void showArticleList(List<ProjectBean.DatasBean> articles, boolean isOver);

    void loadArticleError();
}
