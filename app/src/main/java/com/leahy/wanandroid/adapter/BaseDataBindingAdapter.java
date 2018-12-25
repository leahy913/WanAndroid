package com.leahy.wanandroid.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leahy.utils.listener.PerfectClickListener;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/11/23
 * Description: BaseDataBindingAdapter
 */
public abstract class BaseDataBindingAdapter<T, D extends ViewDataBinding> extends BaseQuickAdapter<T, BaseViewHolder> {

    protected OnItemClickListener<T> listener;

    public BaseDataBindingAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (item == null) return;
        D binding = DataBindingUtil.getBinding(helper.itemView);
        int position = 0;
        if (helper.getLayoutPosition() >= getHeaderLayoutCount()) {
            position = helper.getLayoutPosition() - getHeaderLayoutCount();
        }
        converts(helper, binding, item, position);
    }

    protected abstract void converts(BaseViewHolder helper, D binding, T item, int position);


    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        return binding.getRoot();
    }


    public interface OnItemClickListener<T> {
        void onItemClick(View view, T bean, int position);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    protected void addListener(final View view, final T bean, final int position) {
        view.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                listener.onItemClick(view, bean, position);
            }
        });

    }
}