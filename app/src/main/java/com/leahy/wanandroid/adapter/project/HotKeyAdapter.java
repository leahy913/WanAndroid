package com.leahy.wanandroid.adapter.project;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leahy.utils.utils.CommonUtils;
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

        if (item.getName().equals("搜索热词")) {
            binding.hotKey.getDelegate().setBackgroundColor(CommonUtils.getColor(R.color.itemBackground));
            binding.hotKey.getDelegate().setTextColor(CommonUtils.getColor(R.color.text_color_black));
            binding.hotKey.getDelegate().setStrokeColor(CommonUtils.getColor(R.color.text_color_black));
        } else {
            int color = CommonUtils.getRandomColor();
            binding.hotKey.getDelegate().setTextColor(color);
            binding.hotKey.getDelegate().setStrokeColor(color);
        }
        addListener(binding.hotKey, item, position);
    }
}
