package com.leahy.wanandroid.network.http;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.leahy.utils.network.JsonCallback;
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

public class NullCallback<T> extends StringCallback {


    private Class<T> classOfT;
    private JsonCallback<T> jsonCallback;

    public NullCallback(Class<T> classOfT, JsonCallback<T> jsonCallback) {
        this.classOfT = classOfT;
        this.jsonCallback = jsonCallback;
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

        LogUtils.d(response.body());
        try {
            JsonBean jsonBean = JSON.parseObject(response.body(), JsonBean.class);

            switch (jsonBean.getErrorCode()) {
                case 0:   //请求成功
                    jsonCallback.onSuccess(JSON.parseObject(response.body(), classOfT));
                    break;
                case -1001:// 登录失效，需要重新登录。
                    jsonCallback.onError(-1001);
                    break;
                default:
                    Show.error(jsonBean.getErrorMsg());
                    jsonCallback.onError(jsonBean.getErrorCode());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d(response.body());
            Show.error("请求失败");
            jsonCallback.onError(-1);
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
        jsonCallback.onError(0);
    }
}
