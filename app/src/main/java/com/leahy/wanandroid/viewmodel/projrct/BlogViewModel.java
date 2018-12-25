package com.leahy.wanandroid.viewmodel.projrct;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModel;

import com.leahy.utils.network.JsonCallback;
import com.leahy.wanandroid.bean.project.ProjectBean;
import com.leahy.wanandroid.network.ProjectApi;
import com.leahy.wanandroid.ui.project.BlogFragment;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/25
 * Description: BlogViewModel
 */
public class BlogViewModel extends ViewModel implements JsonCallback<ProjectBean> {

    @SuppressLint("StaticFieldLeak")
    private Activity mActivity;
    private int type;
    private int page;
    private BlogNavigator navigator;


    public BlogViewModel(Activity mActivity, int type, BlogNavigator navigator) {
        this.mActivity = mActivity;
        this.type = type;
        this.navigator = navigator;
    }

    public void loadData() {
        if (type == BlogFragment.TYPE_BLOG) {
            ProjectApi.getNewBlog(mActivity, page, this);
        } else {
            ProjectApi.getNewProject(mActivity, page, this);
        }
    }


    public void destroy() {
        navigator = null;
    }

    @Override
    public void onSuccess(ProjectBean result) {
        page = result.getCurPage();
        navigator.showBlogList(result.getDatas(), result.isOver());
    }

    @Override
    public void onError(int code) {
        navigator.loadBlogError();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
