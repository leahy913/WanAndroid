package com.leahy.utils.utils;

import android.app.Application;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.leahy.utils.R;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/8/28
 * Description: YcxGlide
 */

public class GlideUtils {

    private static Application app;


    public static void init(Application app) {
        GlideUtils.app = app;
    }


    /**
     * 显示图片
     */
    public static void load(ImageView imageView, Object url) {
        Glide.with(app)
                .load(url)
                .apply(new RequestOptions()
                        .error(R.drawable.default_image)
                        .placeholder(R.drawable.default_image))
                .into(imageView);
    }


    /**
     * 显示图片
     * res 加载异常展位图
     */
    public static void load(ImageView imageView, Object url, int res) {
        Glide.with(app)
                .load(url)
                .apply(new RequestOptions()
                        .error(res)
                        .placeholder(R.drawable.default_image))
                .into(imageView);
    }

}
