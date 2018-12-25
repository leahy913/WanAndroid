package com.leahy.wanandroid.network.http;

import android.content.Context;
import android.view.View;

import com.leahy.utils.network.JsonCallback;
import com.leahy.utils.network.JsonCallbackArray;
import com.leahy.utils.widget.Loading;
import com.lzy.okgo.request.base.Request;

/**
 * Author: leahy
 * Time:   2018/5/21
 * Description: LoadingCallback
 */

public class LoadingCallback extends Callback {


    private Context mActivity;
    private View view = null;


    public LoadingCallback(Context mActivity, Class classOfT, JsonCallback jsonCallback) {
        super(classOfT, jsonCallback);
        this.mActivity = mActivity;
    }


    public LoadingCallback(Context mActivity, Class classOfT, JsonCallbackArray jsonCallbackArray) {
        super(classOfT, jsonCallbackArray);
        this.mActivity = mActivity;
    }

    public LoadingCallback(Context mActivity, View view, Class classOfT, JsonCallback jsonCallback) {
        super(classOfT, jsonCallback);
        this.mActivity = mActivity;
        this.view = view;
    }

    public LoadingCallback(Context mActivity, View view, Class classOfT, JsonCallbackArray jsonCallbackArray) {
        super(classOfT, jsonCallbackArray);
        this.mActivity = mActivity;
        this.view = view;
    }


    @Override
    public void onStart(Request request) {
        super.onStart(request);
        Loading.show(mActivity, view);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Loading.dismiss();
    }
}
