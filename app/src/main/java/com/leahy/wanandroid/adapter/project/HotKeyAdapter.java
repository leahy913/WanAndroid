package com.leahy.wanandroid.adapter.project;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.adapter.BaseDataBindingAdapter;
import com.leahy.wanandroid.bean.project.HotKeyBean;
import com.leahy.wanandroid.databinding.ItemSearchHotKeyBinding;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/26
 * Description: HotKeyAdapter
 */
public class HotKeyAdapter extends BaseDataBindingAdapter<HotKeyBean, ItemSearchHotKeyBinding> {

    public HotKeyAdapter(@Nullable List<HotKeyBean> data) {
        super(R.layout.item_search_hot_key, data);
    }

    @Override
    protected void converts(BaseViewHolder helper, ItemSearchHotKeyBinding binding, HotKeyBean item, int position) {
        binding.setHotKey(item.getName());
        binding.executePendingBindings();

        addListener(binding.hotKey, item, position);
    }
}
