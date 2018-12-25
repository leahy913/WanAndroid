package com.leahy.wanandroid.network.http;

import android.app.Activity;

import com.leahy.utils.network.JsonCallback;
import com.leahy.utils.widget.Loading;
import com.lzy.okgo.request.base.Request;

/**
 * Author: leahy
 * Time:   2018/5/21
 * Description: LoadingCallback
 */

public class LoadingNullCallback extends NullCallback {


    private Activity mActivity;


    public LoadingNullCallback(Activity mActivity, Class classOfT, JsonCallback jsonCallback) {
        super(classOfT, jsonCallback);
        this.mActivity=mActivity;
    }


    @Override
    public void onStart(Request request) {
        super.onStart(request);
        Loading.show(mActivity);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Loading.dismiss();
    }
}
