package com.leahy.wanandroid.adapter.project;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leahy.utils.listener.PerfectClickListener;
import com.leahy.utils.utils.StringUtils;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.adapter.BaseDataBindingAdapter;
import com.leahy.wanandroid.bean.project.ProjectBean;
import com.leahy.wanandroid.databinding.ItemProjectBinding;
import com.leahy.wanandroid.ui.webview.ActivityWeb;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/25
 * Description: ProjectAdapter
 */
public class ProjectAdapter extends BaseDataBindingAdapter<ProjectBean.DatasBean, ItemProjectBinding> {

    private Activity mActivity;

    public ProjectAdapter(@Nullable List<ProjectBean.DatasBean> data, Activity mActivity) {
        super(R.layout.item_project, data);
        this.mActivity = mActivity;
    }

    @Override
    protected void converts(BaseViewHolder helper, ItemProjectBinding binding, final ProjectBean.DatasBean item, int position) {
//        binding.title.setText(item.getTitle());
        binding.setBean(item);
//        binding.time.setText(StringUtils.getTimeSpanByNow(item.getPublishTime()));
//        binding.author.setText(item.getAuthor());

        helper.itemView.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ActivityWeb.loadUrl(mActivity, item.getLink(), item.getTitle());
            }
        });
    }
}
