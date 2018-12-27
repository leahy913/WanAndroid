package com.leahy.wanandroid.webview.config;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.leahy.utils.activity.ActivityPhotos;

/**
 * Created by jingbin on 2016/11/17.
 * js通信接口
 */
public class ImageClickInterface {
    private Activity activity;
    private boolean isShowPhoto = true;

    public ImageClickInterface(Activity activity) {
        this.activity = activity;
    }

    public ImageClickInterface(Activity activity, boolean isShowPhoto) {
        this.activity = activity;
        this.isShowPhoto = isShowPhoto;
    }


    @JavascriptInterface
    public void imageClick(String imgUrl, String hasLink) {
        if (!isShowPhoto) return;
        ActivityPhotos.start(activity, imgUrl);
    }

    @JavascriptInterface
    public void textClick(String type, String item_pk) {
//        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(item_pk)) {
//            Toast.makeText(activity, "----点击了文字", Toast.LENGTH_SHORT).show();
//        }
    }
}
