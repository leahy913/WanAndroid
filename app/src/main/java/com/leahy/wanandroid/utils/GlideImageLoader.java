package com.leahy.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.leahy.utils.utils.GlideUtils;
import com.youth.banner.loader.ImageLoader;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/24
 * Description: GlideImageLoader
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideUtils.load(imageView, path);
    }
}
