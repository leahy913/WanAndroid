package com.leahy.wanandroid.viewmodel.article;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/27
 * Description: ArticleHomeNavigator
 */
public interface ArticleHomeNavigator {

    void showChapters(List<String> tabs);

    void loadError();
}
