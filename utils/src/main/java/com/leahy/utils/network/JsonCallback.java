package com.leahy.utils.network;

/**
 * Author: leahy
 * Time:   2018/5/18
 * Description: JsonCallback
 */

public interface JsonCallback<T> {


    void onSuccess(T result);


    void onError(int code);

}
