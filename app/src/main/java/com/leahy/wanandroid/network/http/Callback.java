package com.leahy.wanandroid.network.http;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.leahy.utils.network.JsonCallback;
import com.leahy.utils.network.JsonCallbackArray;
import com.leahy.utils.utils.Show;
import com.leahy.wanandroid.bean.JsonBean;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;


/**
 * Author: leahy
 * Time:   2018/5/18
 * Description: Callback
 */

public class Callback<T> extends StringCallback {


    private Class<T> classOfT;
    private JsonCallback<T> jsonCallback;
    private JsonCallbackArray<T> jsonCallbackArray;


    public Callback(Class<T> classOfT, JsonCallback<T> jsonCallback) {
        this.classOfT = classOfT;
        this.jsonCallback = jsonCallback;
    }

    public Callback(Class<T> classOfT, JsonCallbackArray<T> jsonCallbackArray) {
        this.classOfT = classOfT;
        this.jsonCallbackArray = jsonCallbackArray;
    }


    /**
     * 网络请求开始
     */
    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);

    }

    /**
     * 网络请求结束
     */
    @Override
    public void onFinish() {
        super.onFinish();
    }

    /**
     * 请求成功
     */
    @Override
    public void onSuccess(Response<String> response) {

        try {
            JsonBean jsonBean = JSON.parseObject(response.body(), JsonBean.class);

            switch (jsonBean.getErrorCode()) {
                case 0:   //请求成功
                    LogUtils.d(classOfT.getName() + "\n" + jsonBean.getData());

                    if (jsonCallback != null) {
                        jsonCallback.onSuccess(JSON.parseObject(jsonBean.getData(), classOfT));
                    }

                    if (jsonCallbackArray != null) {
                        jsonCallbackArray.onSuccess(JSON.parseArray(jsonBean.getData(), classOfT));
                    }
                    break;
                case -1001:// 登录失效，需要重新登录。
                    jsonCallback.onError(-1001);
                    Show.warning("登录已过期，请重新登录");
                default:
                    LogUtils.d(classOfT.getName() + "\n" + jsonBean.toString());
                    Show.error(jsonBean.getErrorMsg());
                    if (jsonCallback != null)
                        jsonCallback.onError(jsonBean.getErrorCode());
                    if (jsonCallbackArray != null)
                        jsonCallbackArray.onError(jsonBean.getErrorCode());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Show.error("请求失败");
            LogUtils.d(classOfT.getName() + "\n" + response.body());
            if (jsonCallback != null) jsonCallback.onError(-1);
            if (jsonCallbackArray != null) jsonCallbackArray.onError(-1);
        }
    }


    /**
     * 请求失败
     */
    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        LogUtils.d("Error");
        Show.error("请求失败");
        if (jsonCallback != null) jsonCallback.onError(-1);
        if (jsonCallbackArray != null) jsonCallbackArray.onError(0 - 1);
    }
}
